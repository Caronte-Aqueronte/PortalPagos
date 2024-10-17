package ss1.api.models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;

/**
 * La clase Saldo es una entidad JPA que representa la tabla "saldo" en la base
 * de datos. Cada instancia de Saldo tiene un saldo disponible y está asociada a
 * un único usuario en una relación uno a uno.
 *
 * La anotación @DynamicUpdate permite que las actualizaciones SQL generadas por
 * Hibernate solo incluyan las columnas que han sido modificadas, optimizando el
 * rendimiento.
 *
 * @author luid
 */
@Entity
@Table(name = "saldo")
@DynamicUpdate
public class Saldo extends Auditor {

    /**
     * Relación uno a uno con la entidad Usuario. La propiedad "mappedBy" indica
     * que esta es la parte inversa de la relación y que la columna que almacena
     * la clave foránea está definida en la clase Usuario.
     */
    @OneToOne(mappedBy = "saldo")
    private Usuario usuario;

    /**
     * Representa el saldo disponible asociado a un usuario. Este valor puede
     * ser modificado y consultado mediante los métodos getter y setter.
     */
    private Double saldoDisponible;

    /**
     * Constructor completo que permite crear una instancia de Saldo asociada a
     * un usuario y con un saldo disponible inicial.
     *
     * @param usuario El usuario asociado al saldo.
     * @param saldoDisponible El saldo disponible en la cuenta del usuario.
     */
    public Saldo(Usuario usuario, Double saldoDisponible) {
        this.usuario = usuario;
        this.saldoDisponible = saldoDisponible;
    }

    /**
     * Constructor que permite crear una instancia de Saldo solo con el saldo
     * disponible. Este constructor no asigna inicialmente un usuario.
     *
     * @param saldoDisponible El saldo disponible en la cuenta.
     */
    public Saldo(Double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    // Métodos Getters y Setters
    /**
     * Obtiene el usuario asociado a este saldo.
     *
     * @return El usuario asociado.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Asigna un usuario a este saldo.
     *
     * @param usuario El usuario a asociar con este saldo.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el saldo disponible actual.
     *
     * @return El saldo disponible.
     */
    public Double getSaldoDisponible() {
        return saldoDisponible;
    }

    /**
     * Establece el saldo disponible.
     *
     * @param saldoDisponible El nuevo saldo disponible a establecer.
     */
    public void setSaldoDisponible(Double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

}
