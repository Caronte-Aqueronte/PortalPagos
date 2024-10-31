/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models.dto;

/**
 *
 * @author Luis Monterroso
 */
public class RecargaDTO {

    private Long id;
    private String usuario;
    private String montoRecargado;
    private String fecha;
    private String entidadFinanciera;

    public RecargaDTO(Long id, String usuario, String montoRecargado, String fecha, String entidadFinanciera) {
        this.id = id;
        this.usuario = usuario;
        this.montoRecargado = montoRecargado;
        this.fecha = fecha;
        this.entidadFinanciera = entidadFinanciera;
    }

    public RecargaDTO() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMontoRecargado() {
        return montoRecargado;
    }

    public void setMontoRecargado(String montoRecargado) {
        this.montoRecargado = montoRecargado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEntidadFinanciera() {
        return entidadFinanciera;
    }

    public void setEntidadFinanciera(String entidadFinanciera) {
        this.entidadFinanciera = entidadFinanciera;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
