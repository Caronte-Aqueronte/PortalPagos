/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Luis Monterroso
 */
public class MovimientoExternoRequest {

    @NotNull(message = "El correo no puede ser nulo.")
    @NotBlank(message = "El correo no puede estar vacío.")
    @Email(message = "El correo debe ser un correo electrónico válido.")
    private String email;

    @NotNull(message = "La password no puede ser nula.")
    private String pin;

    @Pattern(regexp = "^(credito|debito)$",
            message = "El identificador de baco debe ser uno "
            + "de los valores permitidos: 'credito' o 'debito'.")
    private String banco;

    @NotNull(message = "La password no puede ser nula.")
    @Min(value = 1, message = "El monto debe ser mayor a 0.")
    private Double monto;

    public MovimientoExternoRequest() {
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

}
