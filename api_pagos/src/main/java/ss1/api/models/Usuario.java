/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 * La clase {@code Usuario} representa la entidad de un usuario en el sistema.
 * Un usuario contiene atributos básicos como NIT, email, nombres, apellidos, y
 * contraseña, así como una relación con un rol.
 *
 * La clase incluye validaciones para los campos y permite la actualización
 * dinámica de atributos usando la anotación {@link DynamicUpdate}.
 *
 * Extiende la clase {@link Auditor}, lo que agrega campos de auditoría como
 * "created_at" y "updated_at".
 *
 * @author luid
 */
@Entity
@Table(name = "usuario")
@DynamicUpdate
public class Usuario extends Auditor {

    /**
     * El NIT del usuario. Este campo es obligatorio, debe ser único y tiene
     * entre 6 y 12 caracteres. El valor máximo de almacenamiento es de 13
     * caracteres.
     */
    @NotBlank(message = "El nit del cliente no puede estar vacío.")
    @NotNull(message = "El nit del cliente no puede ser nulo")
    @Size(min = 6, max = 12, message = "El nit del cliente debe tener entre 6 y 12 caracteres.")
    @Column(length = 13, unique = true, nullable = false)
    private String nit;

    /**
     * El email del usuario. Es obligatorio y debe tener entre 1 y 250
     * caracteres.
     */
    @NotBlank(message = "El email del cliente no puede estar vacío.")
    @NotNull(message = "El email del cliente no puede ser nulo")
    @Size(min = 1, max = 250, message = "El email del cliente debe tener entre 1 y 250 caracteres.")
    @Column(length = 250)
    private String email;

    /**
     * Los nombres del usuario. Son obligatorios y deben tener entre 1 y 250
     * caracteres.
     */
    @NotBlank(message = "Los nombres del cliente no puede estar vacío.")
    @Size(min = 1, max = 250, message = "Los nombres del cliente debe tener entre 1 y 250 caracteres.")
    @Column(length = 250)
    private String nombres;

    /**
     * Los apellidos del usuario. Son obligatorios y deben tener entre 1 y 250
     * caracteres.
     */
    @NotBlank(message = "Los apellidos del cliente no puede estar vacío.")
    @Size(min = 1, max = 250, message = "Los apellidos del cliente debe tener entre 1 y 250 caracteres.")
    @Column(length = 250)
    private String apellidos;

    /**
     * La contraseña del usuario. Es obligatoria y debe tener entre 1 y 250
     * caracteres.
     */
    @NotBlank(message = "La password del cliente no puede estar vacía.")
    @NotNull(message = "La password del cliente no puede ser nula.")
    @Size(min = 1, max = 250, message = "La password del cliente debe tener entre 1 y 250 caracteres.")
    @Column(length = 250)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * El rol asociado al usuario. La relación es muchos a uno, donde múltiples
     * usuarios pueden compartir un mismo rol. El campo {@code rol} se almacena
     * usando la columna "categoria" en la tabla.
     */
    @ManyToOne
    @JoinColumn(name = "categoria") // Indicamos que el id del rol se guarda en la columna "categoria"
    private Rol rol;

    /**
     * Constructor para inicializar un objeto Usuario con todos los atributos.
     *
     * @param nit el NIT del usuario
     * @param email el email del usuario
     * @param nombres los nombres del usuario
     * @param apellidos los apellidos del usuario
     * @param password la contraseña del usuario
     * @param rol el rol asociado al usuario
     */
    public Usuario(String nit, String email, String nombres, String apellidos, String password, Rol rol) {
        this.nit = nit;
        this.email = email;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.password = password;
        this.rol = rol;
    }

    /**
     * Constructor por defecto para la clase Usuario.
     */
    public Usuario() {
    }

    /**
     * Obtiene el NIT del usuario.
     *
     * @return el NIT del usuario
     */
    public String getNit() {
        return nit;
    }

    /**
     * Establece el NIT del usuario.
     *
     * @param nit el NIT a establecer
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * Obtiene el email del usuario.
     *
     * @return el email del usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario.
     *
     * @param email el email a establecer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene los nombres del usuario.
     *
     * @return los nombres del usuario
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Establece los nombres del usuario.
     *
     * @param nombres los nombres a establecer
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Obtiene los apellidos del usuario.
     *
     * @return los apellidos del usuario
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del usuario.
     *
     * @param apellidos los apellidos a establecer
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return la contraseña del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password la contraseña a establecer
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el rol asociado al usuario.
     *
     * @return el rol del usuario
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Establece el rol asociado al usuario.
     *
     * @param rol el rol a establecer
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
