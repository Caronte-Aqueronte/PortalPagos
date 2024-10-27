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
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ss1.api.models.dto.TransaccionDTO;
import ss1.api.models.request.PagoExternoRequest;
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
    private TransaccionService transaccionService;

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
        TransaccionDTO resultado = transaccionService.pagar(solicitudPago);
        return ResponseEntity.ok(resultado); // Retornar la respuesta según el resultado
    }

    @PostMapping("/protected/pagarGetComprobante")
    public ResponseEntity<?> pagarGetComprobante(@RequestBody PagoExternoRequest solicitudPago) {
        // Llamar al servicio de pagos para procesar la transacción
        byte[] resultado = transaccionService.pagarGetComprobante(solicitudPago);
        return ResponseEntity.ok(resultado); // Retornar la respuesta según el resultado
    }

    /**
     * Endpoint para obtener las últimas 20 transacciones de un usuario donde el
     * usuario actúa como emisor o receptor.
     *
     * @param idUsuario ID del usuario cuyas transacciones se desean consultar.
     * @return ResponseEntity con una lista de objetos {@link TransaccionDTO}
     * que representan las últimas 20 transacciones del usuario y un estado HTTP
     * OK (200).
     */
    @Operation(summary = "Obtener las últimas 20 transacciones de un usuario",
            description = "Devuelve las 20 transacciones más recientes del usuario como emisor o receptor.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transacciones encontradas",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransaccionDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Error inesperado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/protected/getMisUltimas20Transacciones/{idUsuario}")
    public ResponseEntity<?> getMisUltimas20Transacciones(@PathVariable Long idUsuario) {
        List<TransaccionDTO> data = transaccionService.getMisUltimasTransacciones(idUsuario);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/protected/getMisPagosEnDosFechas/{idUsuario}")
    public ResponseEntity<?> getMisPagosEnDosFechas(
            @PathVariable Long idUsuario,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha1,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha2) {

        List<TransaccionDTO> data = transaccionService.getMisPagosEnDosFechas(idUsuario, fecha1, fecha2);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/protected/getMisIngresosEnDosFechas/{idUsuario}")
    public ResponseEntity<?> getMisIngresosEnDosFechas(
            @PathVariable Long idUsuario,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha1,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha2) {

        List<TransaccionDTO> data = transaccionService.getMisIngresosEnDosFechas(idUsuario, fecha1, fecha2);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
