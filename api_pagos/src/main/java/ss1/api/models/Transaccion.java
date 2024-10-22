package ss1.api.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * La clase Transaccion extiende de Auditor, lo que significa que hereda
 * atributos y comportamientos comunes de auditoría. Una transacción representa
 * un movimiento financiero entre un emisor y un receptor.
 *
 * Anotaciones como @Entity y @Table indican que esta clase será una entidad JPA
 * mapeada a la tabla "movimiento" en la base de datos.
 */
@Entity
@Table(name = "movimiento")
@DynamicUpdate // Optimiza las consultas de actualización, actualizando solo los campos modificados
public class Transaccion extends Auditor {

    /**
     * El monto de la transacción es un valor requerido y no puede ser negativo.
     * El valor mínimo permitido es 0.
     */
    @NotNull(message = "El monto no puede ser nulo.")
    @Min(value = 0, message = "El monto mínimo de la transacción debe ser 0.")
    private Double monto;

    /**
     * El concepto es una descripción de la transacción, es un campo requerido
     * que no puede estar vacío.
     */
    @NotNull(message = "El concepto no puede ser nulo.")
    @NotBlank(message = "El concepto no puede estar vacío.")
    private String concepto;

    /**
     * Relación muchos-a-uno con la entidad Usuario para el emisor. Un usuario
     * puede ser emisor de múltiples transacciones. Si el usuario es eliminado,
     * las transacciones asociadas a este también serán eliminadas
     * (OnDeleteAction.CASCADE).
     */
    @ManyToOne
    @JoinColumn(name = "emisor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario emisor;

    /**
     * Relación muchos-a-uno con la entidad Usuario para el receptor. Un usuario
     * puede ser receptor de múltiples transacciones. Si el usuario es
     * eliminado, las transacciones asociadas a este también serán eliminadas
     * (OnDeleteAction.CASCADE).
     */
    @ManyToOne
    @JoinColumn(name = "receptor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario receptor;

    /**
     * Constructor de la clase Transacción. Permite inicializar una transacción
     * con los valores proporcionados para tipoMovimiento, monto, concepto,
     * usuario emisor y usuario receptor.
     *
     * @param monto El monto de la transacción.
     * @param concepto El concepto o descripción de la transacción.
     * @param receptor El usuario receptor relacionado con la transacción.
     * @param emisor El usuario emisor relacionado con la transacción.
     */
    public Transaccion(Double monto, String concepto,
            Usuario receptor, Usuario emisor) {
        this.monto = monto;
        this.concepto = concepto;
        this.receptor = receptor;
        this.emisor = emisor;
    }

    public Transaccion() {
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

    public Usuario getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }

    public Usuario getReceptor() {
        return receptor;
    }

    public void setReceptor(Usuario receptor) {
        this.receptor = receptor;
    }
}
