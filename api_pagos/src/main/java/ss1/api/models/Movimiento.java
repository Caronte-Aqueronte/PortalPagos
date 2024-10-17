package ss1.api.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * La clase Movimiento extiende de Auditor, lo que significa que hereda
 * atributos y comportamientos comunes de auditoría. Un movimiento representa
 * una transacción financiera, ya sea un ingreso o egreso.
 *
 * Anotaciones como @Entity y @Table indican que esta clase será una entidad JPA
 * mapeada a la tabla "movimiento" en la base de datos.
 *
 * @author luid
 */
@Entity
@Table(name = "movimiento")
@DynamicUpdate // Optimiza las consultas de actualización, actualizando solo los campos modificados
public class Movimiento extends Auditor {

    /**
     * El tipo de movimiento (INGRESO o EGRESO) es obligatorio y debe seguir el
     * patrón definido. El mensaje de error especifica que el valor solo puede
     * ser "EGRESO" o "INGRESO".
     */
    @NotNull(message = "El tipo del movimiento no puede ser nulo.")
    @NotBlank(message = "El tipo del movimiento no puede estar vacio.")
    @Pattern(regexp = "^(EGRESO|INGRESO)$",
            message = "El tipo de movimiento debe ser "
            + "uno de los valores permitidos: EGRESO,"
            + " INGRESO.")
    private String tipoMovimiento;

    /**
     * El monto del movimiento es un valor requerido y no puede ser negativo. El
     * valor mínimo permitido es 0.
     */
    @NotNull(message = "El concepto no puede ser nulo.")
    @Min(value = 0, message = "El valor minimo del movimiento debe ser 0.")
    private Double monto;

    /**
     * El concepto es una descripción del movimiento, es un campo requerido que
     * no puede estar vacío.
     */
    @NotNull(message = "El concepto no puede ser nulo.")
    @NotBlank(message = "El concepto no puede estar vacio.")
    private String concepto;

    /**
     * El número de cuenta es obligatorio, y representa la cuenta a la cual se
     * relaciona el movimiento. No puede ser nulo ni estar vacío.
     */
    @NotNull(message = "El numero de cuenta a depositar no puede ser nulo.")
    @NotBlank(message = "El numero de cuenta a depositar no puede estar vacio.")
    private String numeroCuenta;

    /**
     * El nombre de la entidad financiera es obligatorio y describe la
     * institución bancaria relacionada con el movimiento. No puede ser nulo ni
     * estar vacío.
     */
    @NotNull(message = "El nombre de la entidad financiera no puede ser nulo.")
    @NotBlank(message = "El nombre de la entidad financiera  no puede estar vacio.")
    private String nombreEntidadFinanciera;

    /**
     * Relación muchos-a-uno con la entidad Usuario. Un usuario puede estar
     * asociado a múltiples movimientos. La columna de unión "usuario" almacena
     * la clave foránea. Si un usuario es eliminado, los movimientos asociados a
     * este también serán eliminados (OnDeleteAction.CASCADE).
     */
    @ManyToOne
    @JoinColumn(name = "usuario", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    /**
     * Constructor de la clase Movimiento. Permite inicializar un movimiento con
     * los valores proporcionados para tipoMovimiento, monto, concepto,
     * numeroCuenta y nombreEntidadFinanciera.
     *
     * @param tipoMovimiento El tipo de movimiento, puede ser EGRESO o INGRESO.
     * @param monto El monto del movimiento.
     * @param concepto El concepto o descripción del movimiento.
     * @param numeroCuenta El número de cuenta relacionado con el movimiento.
     * @param nombreEntidadFinanciera La entidad financiera relacionada.
     */
    public Movimiento(String tipoMovimiento, Double monto, String concepto, String numeroCuenta, String nombreEntidadFinanciera) {
        this.tipoMovimiento = tipoMovimiento;
        this.monto = monto;
        this.concepto = concepto;
        this.numeroCuenta = numeroCuenta;
        this.nombreEntidadFinanciera = nombreEntidadFinanciera;
    }

    // Métodos Getters y Setters
    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNombreEntidadFinanciera() {
        return nombreEntidadFinanciera;
    }

    public void setNombreEntidadFinanciera(String nombreEntidadFinanciera) {
        this.nombreEntidadFinanciera = nombreEntidadFinanciera;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
