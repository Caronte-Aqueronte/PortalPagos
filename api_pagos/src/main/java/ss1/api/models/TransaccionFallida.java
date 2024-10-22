package ss1.api.models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * La clase TransaccionFallida registra las transacciones fallidas que no han
 * podido completarse en el sistema. Cada transacción fallida contiene
 * información sobre el usuario, la fecha y hora de la transacción, el motivo
 * del fallo, el monto que se intentó movilizar, el tipo de transacción y la
 * entidad financiera relacionada.
 *
 * Extiende de la clase Auditor, lo que implica que contiene información de
 * auditoría como la fecha de creación y modificación.
 *
 * Validaciones: - Se asegura que todos los campos requeridos estén presentes y
 * que cumplan con las reglas de negocio.
 *
 * @author luid
 */
@Entity
@Table(name = "transaccion_fallida")
public class TransaccionFallida extends Auditor {

    // Validación para asegurar que el usuario no sea nulo
    @NotNull(message = "El usuario no puede ser nulo.")
    @ManyToOne // Relación con Usuario (muchos a uno)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Validación para asegurar que la fecha y hora no sea nula
    @NotNull(message = "La fecha y hora no pueden ser nulas.")
    @Column(nullable = false)
    private LocalDateTime fechaHora;

    // Validación para asegurar que el motivo del error no sea nulo ni vacío
    @NotNull(message = "El motivo del error no puede ser nulo.")
    @NotBlank(message = "El motivo del error no puede estar vacío.")
    @Size(min = 5, max = 255, message = "El motivo del error debe tener entre 5 y 255 caracteres.")
    @Column(nullable = false)
    private String motivoError;

    // Validación para asegurar que el monto sea positivo y no nulo
    @NotNull(message = "El monto intentado no puede ser nulo.")
    @DecimalMin(value = "0.01", message = "El monto intentado debe ser mayor que 0.")
    @Column(nullable = false)
    private Double montoIntentado;

    // Validación para asegurar que el tipo de transacción no sea nulo ni vacío
    @NotNull(message = "El tipo de transacción no puede ser nulo.")
    @NotBlank(message = "El tipo de transacción no puede estar vacío.")
    @Size(min = 5, max = 50, message = "El tipo de transacción debe tener entre 5 y 50 caracteres.")
    @Column(nullable = false)
    private String tipoTransaccion;

    // Validación para asegurar que la entidad financiera no sea nula ni vacía
    @NotNull(message = "La entidad financiera no puede ser nula.")
    @NotBlank(message = "La entidad financiera no puede estar vacía.")
    @Size(min = 2, max = 100, message = "El nombre de la entidad financiera debe tener entre 2 y 100 caracteres.")
    @Column(nullable = false)
    private String entidadFinanciera;

    // Validación opcional para la cuenta destino, ya que puede no aplicarse en ciertos casos
    @Size(max = 20, message = "El número de cuenta destino no debe exceder los 20 caracteres.")
    private String cuentaDestino;

    // Constructor vacío requerido por JPA
    public TransaccionFallida() {
    }

    /**
     * Constructor con parámetros para inicializar los campos de la transacción
     * fallida.
     *
     * @param usuario Nombre o identificación del usuario.
     * @param fechaHora Fecha y hora del error en la transacción.
     * @param motivoError Descripción del error ocurrido.
     * @param montoIntentado Monto que el usuario intentó movilizar.
     * @param tipoTransaccion Tipo de transacción (retiro, ingreso, etc.).
     * @param entidadFinanciera Entidad financiera relacionada.
     * @param cuentaDestino Cuenta destino involucrada (opcional).
     */
    public TransaccionFallida(Usuario usuario, LocalDateTime fechaHora, String motivoError, Double montoIntentado,
            String tipoTransaccion, String entidadFinanciera, String cuentaDestino) {
        this.usuario = usuario;
        this.fechaHora = fechaHora;
        this.motivoError = motivoError;
        this.montoIntentado = montoIntentado;
        this.tipoTransaccion = tipoTransaccion;
        this.entidadFinanciera = entidadFinanciera;
        this.cuentaDestino = cuentaDestino;
    }

    // Métodos Getters y Setters
    /**
     * Obtiene el usuario relacionado con la transacción fallida.
     *
     * @return El objeto Usuario relacionado.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Asigna el usuario relacionado con la transacción fallida.
     *
     * @param usuario El objeto Usuario a asignar.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la fecha y hora en que ocurrió el error.
     *
     * @return Un objeto LocalDateTime con la fecha y hora del error.
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Asigna la fecha y hora en que ocurrió el error.
     *
     * @param fechaHora El objeto LocalDateTime con la nueva fecha y hora.
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * Obtiene el motivo del error que causó la transacción fallida.
     *
     * @return Una cadena que describe el motivo del error.
     */
    public String getMotivoError() {
        return motivoError;
    }

    /**
     * Asigna el motivo del error que causó la transacción fallida.
     *
     * @param motivoError Una cadena con la descripción del error.
     */
    public void setMotivoError(String motivoError) {
        this.motivoError = motivoError;
    }

    /**
     * Obtiene el monto que el usuario intentó movilizar.
     *
     * @return Un valor Double con el monto intentado.
     */
    public Double getMontoIntentado() {
        return montoIntentado;
    }

    /**
     * Asigna el monto que el usuario intentó movilizar.
     *
     * @param montoIntentado Un valor Double con el nuevo monto.
     */
    public void setMontoIntentado(Double montoIntentado) {
        this.montoIntentado = montoIntentado;
    }

    /**
     * Obtiene el tipo de transacción que falló.
     *
     * @return Una cadena con el tipo de transacción (por ejemplo, retiro,
     * ingreso).
     */
    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * Asigna el tipo de transacción que falló.
     *
     * @param tipoTransaccion Una cadena con el nuevo tipo de transacción.
     */
    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * Obtiene el nombre de la entidad financiera relacionada con la transacción
     * fallida.
     *
     * @return Una cadena con el nombre de la entidad financiera.
     */
    public String getEntidadFinanciera() {
        return entidadFinanciera;
    }

    /**
     * Asigna el nombre de la entidad financiera relacionada con la transacción
     * fallida.
     *
     * @param entidadFinanciera Una cadena con el nuevo nombre de la entidad
     * financiera.
     */
    public void setEntidadFinanciera(String entidadFinanciera) {
        this.entidadFinanciera = entidadFinanciera;
    }

    /**
     * Obtiene el número de cuenta destino relacionado con la transacción
     * fallida.
     *
     * @return Una cadena con el número de cuenta destino (si aplica).
     */
    public String getCuentaDestino() {
        return cuentaDestino;
    }

    /**
     * Asigna el número de cuenta destino relacionado con la transacción fallida
     * (si aplica).
     *
     * @param cuentaDestino Una cadena con el nuevo número de cuenta destino.
     */
    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }
}
