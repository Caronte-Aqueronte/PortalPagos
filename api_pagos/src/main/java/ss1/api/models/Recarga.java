/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Luis Monterroso
 */
@Entity
@Table(name = "recarga")
public class Recarga extends Auditor {

    // Validación para asegurar que el usuario no sea nulo
    @NotNull(message = "El usuario no puede ser nulo.")
    @ManyToOne // Relación con Usuario (muchos a uno)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private Double montoRecargado;

    @Column(nullable = false)
    private String entidadFinanciera;

    public Recarga(Usuario usuario, Double montoRecargado, String entidadFinanciera) {
        this.usuario = usuario;
        this.montoRecargado = montoRecargado;
        this.entidadFinanciera = entidadFinanciera;
    }

    public Recarga(Double montoRecargado, String entidadFinanciera) {
        this.montoRecargado = montoRecargado;
        this.entidadFinanciera = entidadFinanciera;
    }

    public Recarga() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getMontoRecargado() {
        return montoRecargado;
    }

    public void setMontoRecargado(Double montoRecargado) {
        this.montoRecargado = montoRecargado;
    }

    public String getEntidadFinanciera() {
        return entidadFinanciera;
    }

    public void setEntidadFinanciera(String entidadFinanciera) {
        this.entidadFinanciera = entidadFinanciera;
    }

}
