/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models.dto;

/**
 *
 * @author Luis Monterroso
 */
public class TransaccionDTO {

    private Long id;  // ID de la transacción
    private Double monto;  // Monto transferido
    private String concepto;  // Concepto de la transacción
    private String emisor;  // Correo del emisor
    private String receptor;  // Correo del receptor
    private String fechaTransaccion;  // Fecha en que se realizó la transacción

    /**
     * Constructor para inicializar el DTO de una transacción con todos los
     * atributos relevantes.
     *
     * @param id El identificador único de la transacción.
     * @param monto El monto transferido en la transacción.
     * @param concepto El concepto o descripción de la transacción.
     * @param emisor El correo del usuario que realiza la transacción.
     * @param receptor El correo del usuario que recibe los fondos.
     * @param fechaTransaccion La fecha y hora en que se realizó la transacción.
     */
    public TransaccionDTO(Long id, Double monto, String concepto, String emisor,
            String receptor, String fechaTransaccion) {
        this.id = id;
        this.monto = monto;
        this.concepto = concepto;
        this.emisor = emisor;
        this.receptor = receptor;
        this.fechaTransaccion = fechaTransaccion;
    }

    /**
     * Obtiene el identificador único de la transacción.
     *
     * @return El ID de la transacción.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la transacción.
     *
     * @param id El ID a establecer para la transacción.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el monto transferido en la transacción.
     *
     * @return El monto de la transacción.
     */
    public Double getMonto() {
        return monto;
    }

    /**
     * Establece el monto transferido en la transacción.
     *
     * @param monto El monto a establecer para la transacción.
     */
    public void setMonto(Double monto) {
        this.monto = monto;
    }

    /**
     * Obtiene el concepto o descripción de la transacción.
     *
     * @return El concepto de la transacción.
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * Establece el concepto o descripción de la transacción.
     *
     * @param concepto El concepto a establecer para la transacción.
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * Obtiene el correo del usuario que realiza la transacción (emisor).
     *
     * @return El correo del emisor.
     */
    public String getEmisor() {
        return emisor;
    }

    /**
     * Establece el correo del usuario que realiza la transacción (emisor).
     *
     * @param emisor El correo a establecer para el emisor.
     */
    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    /**
     * Obtiene el correo del usuario que recibe los fondos de la transacción
     * (receptor).
     *
     * @return El correo del receptor.
     */
    public String getReceptor() {
        return receptor;
    }

    /**
     * Establece el correo del usuario que recibe los fondos de la transacción
     * (receptor).
     *
     * @param receptor El correo a establecer para el receptor.
     */
    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    /**
     * Obtiene la fecha y hora en que se realizó la transacción.
     *
     * @return La fecha de la transacción.
     */
    public String getFechaTransaccion() {
        return fechaTransaccion;
    }

    /**
     * Establece la fecha y hora en que se realizó la transacción.
     *
     * @param fechaTransaccion La fecha a establecer para la transacción.
     */
    public void setFechaTransaccion(String fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }
}
