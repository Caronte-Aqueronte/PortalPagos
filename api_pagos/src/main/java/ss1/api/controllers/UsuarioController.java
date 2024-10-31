/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ss1.api.models.Usuario;
import ss1.api.models.dto.ExistEmailDTO;
import ss1.api.models.dto.LoginDTO;
import ss1.api.models.request.EditarPasswordRequest;
import ss1.api.models.request.EditarPerfilRequest;
import ss1.api.models.request.EliminarMiUsuarioRequest;
import ss1.api.models.request.LoginRequest;
import ss1.api.services.UsuarioService;

/**
 *
 * @author luid
 */
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(description = "Realiza la autenticación de un usuario basado en las credenciales proporcionadas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Credenciales correctas",
                content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LoginDTO.class))
                }
        ),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta. Algún campo es inválido o falta información.", content = @Content),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas. El usuario no está autorizado.", content = @Content),
        @ApiResponse(responseCode = "404", description = "El correo electrónico no fue encontrado.", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor.", content = @Content)
    })
    @PostMapping("/public/login")
    public ResponseEntity login(@RequestBody LoginRequest login) {
        // Llama al servicio para iniciar sesión y devuelve el resultado en un DTO
        LoginDTO respuesta = usuarioService.iniciarSesion(login);
        // Si la autenticación fue exitosa, devolver el DTO con HTTP status 200
        return ResponseEntity.ok(respuesta);  // Respuesta 200 con el DTO devuelto
    }

    @Operation(summary = "Crear un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LoginDTO.class))
                }
        ),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, validación fallida", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflicto, el NIT o email ya existe", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/public/crearUsuario")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        // Llamar al servicio para crear el usuario
        LoginDTO nuevoUsuario = usuarioService.crearUsuario(usuario);
        // Si todo está correcto, devolver la respuesta 201 Created con el usuario creado
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @Operation(summary = "Elimina un usuario en el sistema, si y solo si no tiene fondos en la cuenta.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, validación fallida", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflicto, el usuario tiene saldo en su cuenta y no puede ser eliminado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "401", description = "Inautorizado, el usuario no tiene los permisos suficientes",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/protected/eliminarMiUsuario")
    public ResponseEntity<?> eliminarMiUsuario(@RequestBody EliminarMiUsuarioRequest req) {
        // Llamar al servicio para crear el usuario
        String nuevoUsuario = usuarioService.eliminarMiUsuario(req);
        Map<String, String> response = new HashMap<>();
        response.put("message", nuevoUsuario);
        // Si todo está correcto, devolver la respuesta 201 Created con el usuario creado
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Obtener usuario por ID",
            description = "Obtiene la información del usuario basado en el ID proporcionado, el usuario debe estar autenticado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/protected/getMiUsuario/{id}")
    public ResponseEntity<?> getMiUsuario(@PathVariable Long id) {
        Usuario data = usuarioService.getMiUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/admin/getUsuariosExceptoElMio")
    public ResponseEntity<?> getUsuariosExceptoElMio() {
        List<Usuario> data = usuarioService.getUsuariosExceptoElMio();
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @DeleteMapping("/admin/eliminarUsuarioParaAdmins/{idUsuario}")
    public ResponseEntity<?> eliminarUsuarioParaAdmins(@PathVariable Long idUsuario) {
        // Llamar al servicio para crear el usuario
        String nuevoUsuario = usuarioService.eliminarUsuarioParaAdmins(idUsuario);
        Map<String, String> response = new HashMap<>();
        response.put("message", nuevoUsuario);
        // Si todo está correcto, devolver la respuesta 201 Created con el usuario creado
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/admin/crearAdmin")
    public ResponseEntity<?> crearAdmin(@RequestBody Usuario usuario) {
        // Llamar al servicio para crear el usuario
        LoginDTO nuevoUsuario = usuarioService.crearAdmin(usuario);
        // Si todo está correcto, devolver la respuesta 201 Created con el usuario creado
        return ResponseEntity.status(HttpStatus.OK).body(nuevoUsuario);
    }

    @Operation(summary = "Edita la informacion del perfil del cliente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario editado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, validación fallida", content = @Content),
        @ApiResponse(responseCode = "401", description = "Inautorizado, el usuario no tiene los permisos suficientes",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PatchMapping("/protected/editarPerfil")
    public ResponseEntity<?> editarPerfil(@RequestBody EditarPerfilRequest id) {
        Usuario data = usuarioService.editarPerfil(id);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @Operation(summary = "Edita la password si y solo si la passoword anterior esta bien.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario editado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, validación fallida", content = @Content),
        @ApiResponse(responseCode = "401", description = "Inautorizado, el usuario no tiene los permisos suficientes",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PatchMapping("/protected/editarPassword")
    public ResponseEntity<?> editarPassword(@RequestBody EditarPasswordRequest id) {
        Usuario data = usuarioService.editarPassword(id);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    /**
     * Endpoint público para verificar si un email ya existe en el sistema.
     *
     * @param email Dirección de correo electrónico que se desea verificar.
     * @return ResponseEntity con un objeto {@link ExistEmailDTO} que indica si
     * el email tiene cuenta asociada.
     */
    @Operation(summary = "Verificar si un email ya existe en el sistema",
            description = "Devuelve un objeto que indica si el email proporcionado ya está registrado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email verificado correctamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExistEmailDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - El email no puede ser nulo",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Error inesperado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/public/existeEmail/{email}")
    public ResponseEntity<?> getMiUsuario(@PathVariable String email) {
        ExistEmailDTO data = usuarioService.existeEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
