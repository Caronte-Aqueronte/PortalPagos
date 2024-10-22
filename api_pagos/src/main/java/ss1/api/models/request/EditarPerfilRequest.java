/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models.request;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.stereotype.Component;

/**
 *
 * @author luid
 */
@Component
public class EditarPerfilRequest {

    @NotNull(message = "El id del cliente no puede ser nulo")
    private Long id;

    @NotBlank(message = "El nit del cliente no puede estar vacío.")
    @NotNull(message = "El nit del cliente no puede ser nulo")
    @Size(min = 6, max = 12, message = "El nit del cliente debe tener entre 6 y 12 caracteres.")
    private String nit;

    @NotBlank(message = "Los nombres del cliente no puede estar vacío.")
    @Size(min = 1, max = 250, message = "Los nombres del cliente debe tener entre 1 y 250 caracteres.")
    @Column(length = 250)
    private String nombres;

    @NotBlank(message = "Los apellidos del cliente no puede estar vacío.")
    @Size(min = 1, max = 250, message = "Los apellidos del cliente debe tener entre 1 y 250 caracteres.")
    private String apellidos;

    public EditarPerfilRequest(Long id, String nit, String nombres, String apellidos) {
        this.id = id;
        this.nit = nit;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public EditarPerfilRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

}
