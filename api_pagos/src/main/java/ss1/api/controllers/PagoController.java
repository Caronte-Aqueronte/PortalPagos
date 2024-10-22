/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ss1.api.models.request.PaymentRequest;
import ss1.api.services.PagoService;

/**
 *
 * @author Luis Monterroso
 */
@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/protected/enviar")
    public ResponseEntity<?> enviarPago(@Valid @RequestBody PaymentRequest solicitudPago, Authentication authentication) {
        // Obtener el usuario autenticado del token JWT
        String correoPagador = authentication.getName();

        // Llamar al servicio de pagos para procesar la transacción
        String resultado = pagoService.procesarPago(correoPagador, solicitudPago.getCorreoReceptor(), solicitudPago.getCantidad());

        return ResponseEntity.ok(resultado); // Retornar la respuesta según el resultado
    }
}
