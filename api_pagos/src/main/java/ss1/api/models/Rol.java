/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * La clase {@code Rol} representa la entidad de un rol en el sistema. Un rol
 * define permisos y accesos específicos que los usuarios pueden tener. Extiende
 * la clase {@link Auditor}, lo que proporciona campos adicionales de auditoría
 * como "created_at" y "updated_at".
 *
 * La clase incluye validaciones para el campo de nombre, define una relación de
 * uno a muchos con la entidad {@link Usuario} y permite el borrado suave (soft
 * delete) usando las anotaciones de Hibernate.
 *
 * @author luid
 */
@Entity
@Table(name = "rol")
@SQLDelete(sql = "UPDATE rol SET deleted_at = NULL WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Rol extends Auditor {

    /**
     * El nombre del rol. Este campo es obligatorio y tiene una longitud máxima
     * de 250 caracteres.
     */
    @Column(length = 250, nullable = false)
    @NotBlank(message = "El nombre del rol no puede estar vacío.")
    @NotNull(message = "El nombre del rol no puede ser nulo")
    @Size(min = 1, max = 250, message = "El nombre del rol debe tener entre 1 y 250 caracteres.")
    private String nombre;

    /**
     * Lista de usuarios asociados a este rol. La relación es de uno a muchos,
     * donde un rol puede estar asociado a múltiples usuarios. Este campo es
     * gestionado por la entidad {@link Usuario}.
     *
     * La anotación {@code JsonProperty} con acceso WRITE_ONLY asegura que la
     * lista de usuarios no se serialice en las respuestas JSON.
     */
    @OneToMany(mappedBy = "rol")
    @Cascade(CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(hidden = true)
    private List<Usuario> ususarios;

    /**
     * Constructor que inicializa el objeto Rol con el nombre proporcionado.
     *
     * @param nombre el nombre del rol
     */
    public Rol(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Constructor por defecto para la clase Rol.
     */
    public Rol() {
    }

    /**
     * Obtiene el nombre del rol.
     *
     * @return el nombre del rol
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del rol.
     *
     * @param nombre el nombre del rol a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la lista de usuarios asociados a este rol.
     *
     * @return la lista de usuarios
     */
    public List<Usuario> getUsusarios() {
        return ususarios;
    }

    /**
     * Establece la lista de usuarios asociados a este rol.
     *
     * @param ususarios la lista de usuarios a establecer
     */
    public void setUsusarios(List<Usuario> ususarios) {
        this.ususarios = ususarios;
    }
}
