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
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ss1.api.models.dto.TransaccionDTO;
import ss1.api.models.request.PagoRequest;
import ss1.api.services.TransaccionService;

/**
 *
 * @author luid
 */
@RestController
@RequestMapping("/api/transaccion")
public class TransaccionController {

    @Autowired
    private TransaccionService movimientoService;

    /**
     * Procesa una solicitud de pago enviada por un usuario autenticado. El pago
     * se envía desde un emisor hacia un receptor especificado en la solicitud.
     *
     * @param solicitudPago Objeto {@link PagoRequest} que contiene los detalles
     * de la transacción como el monto y el receptor.
     * @return Un {@link ResponseEntity} con el objeto {@link TransaccionDTO}
     * que contiene los detalles de la transacción completada.
     */
    @Operation(summary = "Procesa un pago entre un emisor y un receptor",
            description = "Este endpoint permite que un usuario autenticado realice un pago a otro usuario. "
            + "El emisor debe tener fondos suficientes, de lo contrario, la transacción fallará.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago procesado con éxito",
                content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TransaccionDTO.class))}),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta o inválida", content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuario receptor no encontrado", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflicto, como saldo insuficiente", content = @Content)
    })
    @PostMapping("/protected/pagar")
    public ResponseEntity<?> enviarPago(@Valid @RequestBody PagoRequest solicitudPago) {
        // Llamar al servicio de pagos para procesar la transacción
        TransaccionDTO resultado = movimientoService.procesarPago(solicitudPago);
        return ResponseEntity.ok(resultado); // Retornar la respuesta según el resultado
    }

}
