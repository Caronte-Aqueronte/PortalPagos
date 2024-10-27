/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models.dto;

/**
 *
 * @author Luis Monterroso
 */
public class ExistEmailDTO {

    private Boolean tieneCuenta;

    public ExistEmailDTO(Boolean tieneCuenta) {
        this.tieneCuenta = tieneCuenta;
    }

    public ExistEmailDTO() {
    }

    public Boolean getTieneCuenta() {
        return tieneCuenta;
    }

    public void setTieneCuenta(Boolean tieneCuenta) {
        this.tieneCuenta = tieneCuenta;
    }

}
