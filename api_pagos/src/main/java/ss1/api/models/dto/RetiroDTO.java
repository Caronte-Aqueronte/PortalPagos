/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models.dto;

/**
 *
 * @author Luis Monterroso
 */
public class RetiroDTO {

    private Long retiroId;
    private String usuarioEmail;
    private String montoRetirado;
    private String comision;
    private String montoFinalTransferido;
    private String cuentaDestino;
    private String entidadFinanciera;
    private String fechaRetiro;

    public RetiroDTO(Long retiroId, String usuarioEmail, String montoRetirado, String comision, String montoFinalTransferido, String cuentaDestino, String entidadFinanciera, String fechaRetiro) {
        this.retiroId = retiroId;
        this.usuarioEmail = usuarioEmail;
        this.montoRetirado = montoRetirado;
        this.comision = comision;
        this.montoFinalTransferido = montoFinalTransferido;
        this.cuentaDestino = cuentaDestino;
        this.entidadFinanciera = entidadFinanciera;
        this.fechaRetiro = fechaRetiro;
    }

    public RetiroDTO() {
    }

    public Long getRetiroId() {
        return retiroId;
    }

    public void setRetiroId(Long retiroId) {
        this.retiroId = retiroId;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(String fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public String getMontoRetirado() {
        return montoRetirado;
    }

    public void setMontoRetirado(String montoRetirado) {
        this.montoRetirado = montoRetirado;
    }

    public String getComision() {
        return comision;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }

    public String getMontoFinalTransferido() {
        return montoFinalTransferido;
    }

    public void setMontoFinalTransferido(String montoFinalTransferido) {
        this.montoFinalTransferido = montoFinalTransferido;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public String getEntidadFinanciera() {
        return entidadFinanciera;
    }

    public void setEntidadFinanciera(String entidadFinanciera) {
        this.entidadFinanciera = entidadFinanciera;
    }

}
