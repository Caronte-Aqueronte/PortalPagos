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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ss1.api.models.Usuario;
import ss1.api.models.dto.LoginDTO;
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

}
