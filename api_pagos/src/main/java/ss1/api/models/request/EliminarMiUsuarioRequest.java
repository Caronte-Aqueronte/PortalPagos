/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models.request;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Luis Monterroso
 */
public class EliminarMiUsuarioRequest {

    @NotNull(message = "La password no puede ser nula.")
    @Length(max = 254, message = "La password debe tener entre 1 y 254 caracteres.")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
