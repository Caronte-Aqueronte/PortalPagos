/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models.dto;

/**
 *
 * @author Luis Monterroso
 */
public class SaldoDTO {

    String saldoDisponible;

    public SaldoDTO(String saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public SaldoDTO() {
    }

    public String getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(String saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

}
