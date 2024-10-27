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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ss1.api.models.dto.SaldoDTO;
import ss1.api.services.SaldoService;

/**
 *
 * @author Luis Monterroso
 */
@RestController
@RequestMapping("/api/saldo")
public class SaldoController {

    @Autowired
    private SaldoService saldoService;

    /**
     * Endpoint para obtener el saldo disponible de un usuario específico.
     *
     * @param idUsuario ID del usuario cuyo saldo se desea consultar.
     * @return ResponseEntity con el saldo disponible del usuario en formato
     * regional, envuelto en un objeto {@link SaldoDTO} y con el estado HTTP OK
     * (200).
     */
    @Operation(summary = "Obtener el saldo de un usuario",
            description = "Devuelve el saldo disponible del usuario en formato regional.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Saldo encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaldoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Saldo no encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Error inesperado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/protected/getMiSaldo/{idUsuario}")
    public ResponseEntity<?> getMiSaldo(@PathVariable Long idUsuario) {
        SaldoDTO data = saldoService.getMiSaldo(idUsuario);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
