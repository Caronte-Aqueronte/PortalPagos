/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Luis Monterroso
 */
public class PagoExternoRequest extends PagoRequest {

    @NotNull(message = "El nombre de la tienda no puede ser nulo.")
    @NotBlank(message = "El nombre de la tienda  no puede estar vacío.")
    private String nombreTienda;

    @NotNull(message = "El identificador de la tienda no puede ser nulo.")
    @NotBlank(message = "El identificador de la tienda  no puede estar vacío.")
    @Pattern(regexp = "^(a|b)$",
            message = "El identificador de la tienda debe ser uno "
            + "de los valores permitidos: 'a' o 'b'.")
    private String identificadorTienda;

    public PagoExternoRequest() {
    }

    public PagoExternoRequest(String nombreTienda, String identificadorTienda, Double cantidad, String correoReceptor, String concepto) {
        super(cantidad, correoReceptor, concepto);
        this.nombreTienda = nombreTienda;
        this.identificadorTienda = identificadorTienda;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public String getIdentificadorTienda() {
        return identificadorTienda;
    }

    public void setIdentificadorTienda(String identificadorTienda) {
        this.identificadorTienda = identificadorTienda;
    }
}
