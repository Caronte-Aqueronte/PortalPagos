/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models.dto.reporte;

import java.util.List;
import ss1.api.models.Recarga;
import ss1.api.models.Retiro;
import ss1.api.models.Transaccion;

/**
 *
 * @author Luis Monterroso
 */
public class MovimientosReporteDTO {

    private String totalRetiros;
    private String totalTransacciones;
    private String totalRecargas;

    private List<Retiro> retiros;
    private List<Transaccion> transacciones;
    private List<Recarga> recargas;

    public MovimientosReporteDTO(String totalRetiros, String totalTransacciones, String totalRecargas, List<Retiro> retiros, List<Transaccion> transacciones, List<Recarga> recargas) {
        this.totalRetiros = totalRetiros;
        this.totalTransacciones = totalTransacciones;
        this.totalRecargas = totalRecargas;
        this.retiros = retiros;
        this.transacciones = transacciones;
        this.recargas = recargas;
    }

    public String getTotalTransacciones() {
        return totalTransacciones;
    }

    public void setTotalTransacciones(String totalTransacciones) {
        this.totalTransacciones = totalTransacciones;
    }

    public MovimientosReporteDTO() {
    }

    public String getTotalRetiros() {
        return totalRetiros;
    }

    public void setTotalRetiros(String totalRetiros) {
        this.totalRetiros = totalRetiros;
    }

    public String getTotalRecargas() {
        return totalRecargas;
    }

    public void setTotalRecargas(String totalRecargas) {
        this.totalRecargas = totalRecargas;
    }

    public List<Retiro> getRetiros() {
        return retiros;
    }

    public void setRetiros(List<Retiro> retiros) {
        this.retiros = retiros;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    public List<Recarga> getRecargas() {
        return recargas;
    }

    public void setRecargas(List<Recarga> recargas) {
        this.recargas = recargas;
    }

}
