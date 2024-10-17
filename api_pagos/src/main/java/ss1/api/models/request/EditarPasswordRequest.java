/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.stereotype.Component;

/**
 *
 * @author luid
 */
@Component
public class EditarPasswordRequest {

    @NotNull(message = "El id del cliente no puede ser nulo")
    private Long id;
    @NotBlank(message = "La password anterior del cliente no puede estar vacía.")
    @NotNull(message = "La password del cliente no puede ser nula.")
    @Size(min = 1, max = 250, message = "La password del cliente debe tener entre 1 y 250 caracteres.")
    private String oldPassword;
    @NotBlank(message = "La password nueva del cliente no puede estar vacía.")
    @NotNull(message = "La password del cliente no puede ser nula.")
    @Size(min = 1, max = 250, message = "La password del cliente debe tener entre 1 y 250 caracteres.")
    private String newPassword;

    public EditarPasswordRequest(Long id, String oldPassword, String newPassword) {
        this.id = id;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public EditarPasswordRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
