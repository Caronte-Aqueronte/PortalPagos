/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author luid
 */
public class LoginRequest {

    @NotNull(message = "El correo no puede ser nulo.")
    @NotBlank(message = "El correo no puede estar vacío.")
    @Email(message = "El correo debe ser un correo electrónico válido.")
    @Length(max = 254, message = "El correo del receptor debe tener entre 1 y 254 caracteres.")
    private String email;
    @NotNull(message = "La password no puede ser nula.")
    @Length(max = 254, message = "La password debe tener entre 1 y 254 caracteres.")
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
