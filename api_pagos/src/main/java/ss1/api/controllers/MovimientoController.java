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
import ss1.api.models.request.MovimientoRequest;
import ss1.api.services.MovimientoService;

/**
 *
 * @author luid
 */
@RestController
@RequestMapping("/api/movimiento")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

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
    @PostMapping("/protected/generarMovimiento")
    public ResponseEntity<?> generarMovimiento(@RequestBody MovimientoRequestu movimiento) {
        // Llamar al servicio para crear el usuario
        String res = movimientoService.generarMovimiento(movimiento);
        // Si todo está correcto, devolver la respuesta 201 Created con el usuario creado
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

}
