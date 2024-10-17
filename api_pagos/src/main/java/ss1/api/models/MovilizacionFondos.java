package ss1.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * La clase MovilizacionFondos representa una operación en la que un usuario
 * retira fondos de su cuenta en el portal de pagos hacia una cuenta bancaria o
 * tarjeta de crédito. Durante la movilización de fondos, se calcula una
 * comisión que se descuenta del monto retirado.
 *
 * La clase extiende de Auditor, lo que implica que incluye características de
 * auditoría como la fecha de creación y modificación.
 *
 * Validaciones: - Asegura que los datos necesarios para la movilización de
 * fondos sean válidos y cumplan con las reglas de negocio.
 *
 * Atributos principales: - usuario: El usuario que realiza la movilización. -
 * montoRetirado: El monto total que el usuario desea retirar. - comision:
 * Comisión calculada por la movilización (1.3% del monto). -
 * montoFinalTransferido: El monto que se transfiere después de descontar la
 * comisión. - cuentaDestino: La cuenta bancaria o tarjeta de crédito a la que
 * se transfiere el dinero. - entidadFinanciera: El nombre del banco o entidad
 * financiera relacionada con la movilización.
 *
 * Métodos: - calcularComisionYMontoFinal: Método para calcular la comisión y el
 * monto transferido.
 *
 * @author luid
 */
@Entity
@Table(name = "movilizacion_fondo")
public class MovilizacionFondos extends Auditor {

    // Validación para asegurar que el usuario no sea nulo
    @NotNull(message = "El usuario no puede ser nulo.")
    @ManyToOne // Relación con Usuario (muchos a uno)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Validación para asegurar que el monto retirado sea mayor que 0
    @NotNull(message = "El monto a retirar no puede ser nulo.")
    @Min(value = 1, message = "El monto a retirar debe ser mayor que 0.")
    @Column(nullable = false)
    private Double montoRetirado;

    // Este campo será calculado, por lo que no necesita validación específica
    @Column(nullable = false)
    private Double comision;

    // Este campo también será calculado, no necesita validación adicional
    @Column(nullable = false)
    private Double montoFinalTransferido;

    // Validación para la cuenta destino, que no debe ser nula ni vacía
    @NotNull(message = "La cuenta destino no puede ser nula.")
    @NotBlank(message = "La cuenta destino no puede estar vacía.")
    @Size(min = 1, max = 120, message = "La cuenta destino debe tener entre 1 y 120 caracteres.")
    @Column(nullable = false)
    private String cuentaDestino;

    // Validación para la entidad financiera, que no debe ser nula ni vacía
    @NotNull(message = "La entidad financiera no puede ser nula.")
    @NotBlank(message = "La entidad financiera no puede estar vacía.")
    @Size(min = 1, max = 120, message = "El nombre de la entidad financiera debe tener entre 1 y 120 caracteres.")
    @Column(nullable = false)
    private String entidadFinanciera;

    // Constante para el porcentaje de la comisión
    private static final double PORCENTAJE_COMISION = 1.3 / 100;

    // Constructor vacío para JPA
    public MovilizacionFondos() {
    }

    /**
     * Constructor con parámetros para inicializar los campos de la
     * movilización.
     *
     * @param usuario El usuario que realiza la movilización.
     * @param montoRetirado El monto que se va a retirar.
     * @param cuentaDestino La cuenta destino donde se transferirá el dinero.
     * @param entidadFinanciera El nombre de la entidad financiera donde se
     * realizará la transferencia.
     */
    public MovilizacionFondos(Usuario usuario, Double montoRetirado, String cuentaDestino, String entidadFinanciera) {
        this.usuario = usuario;
        this.montoRetirado = montoRetirado;
        this.cuentaDestino = cuentaDestino;
        this.entidadFinanciera = entidadFinanciera;
        calcularComisionYMontoFinal();
    }

    /**
     * Método privado para calcular la comisión y el monto final transferido. La
     * comisión es el 1.3% del monto retirado.
     */
    private void calcularComisionYMontoFinal() {
        this.comision = this.montoRetirado * PORCENTAJE_COMISION;
        this.montoFinalTransferido = this.montoRetirado - this.comision;
    }

    // Getters y Setters
    /**
     * Obtiene el usuario que realizó la movilización.
     *
     * @return El usuario que realizó la movilización.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Asigna el usuario que realizó la movilización.
     *
     * @param usuario El usuario a asignar.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el monto que el usuario retiró.
     *
     * @return El monto retirado.
     */
    public Double getMontoRetirado() {
        return montoRetirado;
    }

    /**
     * Asigna el monto que el usuario retiró.
     *
     * @param montoRetirado El monto retirado a asignar.
     */
    public void setMontoRetirado(Double montoRetirado) {
        this.montoRetirado = montoRetirado;
        calcularComisionYMontoFinal();
    }

    /**
     * Obtiene la comisión calculada por la movilización de fondos.
     *
     * @return La comisión cobrada.
     */
    public Double getComision() {
        return comision;
    }

    /**
     * Obtiene el monto final transferido después de descontar la comisión.
     *
     * @return El monto final transferido.
     */
    public Double getMontoFinalTransferido() {
        return montoFinalTransferido;
    }

    /**
     * Obtiene la cuenta destino donde se transferirán los fondos.
     *
     * @return La cuenta destino.
     */
    public String getCuentaDestino() {
        return cuentaDestino;
    }

    /**
     * Asigna la cuenta destino donde se transferirán los fondos.
     *
     * @param cuentaDestino La cuenta destino a asignar.
     */
    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    /**
     * Obtiene el nombre de la entidad financiera relacionada con la
     * movilización.
     *
     * @return El nombre de la entidad financiera.
     */
    public String getEntidadFinanciera() {
        return entidadFinanciera;
    }

    /**
     * Asigna el nombre de la entidad financiera relacionada con la
     * movilización.
     *
     * @param entidadFinanciera El nombre de la entidad financiera a asignar.
     */
    public void setEntidadFinanciera(String entidadFinanciera) {
        this.entidadFinanciera = entidadFinanciera;
    }
}
