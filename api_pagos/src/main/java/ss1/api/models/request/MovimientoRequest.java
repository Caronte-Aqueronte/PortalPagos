/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models.request;

import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/**
 *
 * @author luid
 */
@Component
public class MovimientoRequest {

    @NotNull(message = "El email de destinatario no puede ser nulo.")
    private String destinatario;
    @NotNull(message = "La cantidad de la transaccion no puede ser nula.")
    private Double cantidad;

    public MovimientoRequest(String destinatario, Double cantidad) {
        this.destinatario = destinatario;
        this.cantidad = cantidad;
    }

    public MovimientoRequest() {
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

}
