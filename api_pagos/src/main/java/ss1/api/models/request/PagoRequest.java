/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Luis Monterroso
 */
public class PagoRequest {

    @Min(value = 1, message = "La cantidad debe ser mayor a 0.")
    private Double cantidad;

    @NotNull(message = "El correo del receptor no puede ser nulo.")
    @NotBlank(message = "El correo del receptor no puede estar vacío.")
    @Email(message = "El correo del receptor debe ser un correo electrónico válido.")
    @Length(max = 254, message = "El correo del receptor debe tener entre 1 y 254 caracteres.")
    private String correoReceptor;

    // Getters y setters
    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getCorreoReceptor() {
        return correoReceptor;
    }

    public void setCorreoReceptor(String correoReceptor) {
        this.correoReceptor = correoReceptor;
    }

}
