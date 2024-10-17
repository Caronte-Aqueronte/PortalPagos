package ss1.api.models.request;

import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

/**
 * La clase LoginRequest representa un objeto de solicitud utilizado para
 * manejar los datos necesarios para el inicio de sesión. Contiene dos campos:
 * email y password, los cuales son validados para asegurar que no sean nulos.
 *
 * Esta clase se utiliza en el contexto de autenticación para recibir los datos
 * enviados por el cliente cuando este intenta iniciar sesión.
 *
 * Anotada con @Component, la clase está disponible como un componente de
 * Spring, lo que permite su inyección en otros servicios o controladores cuando
 * sea necesario.
 *
 * Atributos: - email: Correo electrónico del usuario que intenta autenticarse.
 * - password: Contraseña del usuario.
 *
 * Incluye un constructor completo, un constructor vacío, y métodos getters y
 * setters para acceder y modificar los atributos.
 *
 * @author luid
 */
@Component
public class LoginRequest {

    /**
     * Correo electrónico del usuario. Es un campo obligatorio, por lo que debe
     * ser validado con @NotNull.
     */
    @NotNull(message = "El email no puede ser nulo.")
    private String email;

    /**
     * Contraseña del usuario. Es un campo obligatorio, por lo que debe ser
     * validado con @NotNull.
     */
    @NotNull(message = "La password no puede ser nula.")
    private String password;

    /**
     * Constructor que inicializa ambos campos: email y password.
     *
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     */
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor vacío que permite crear una instancia de LoginRequest sin
     * inicializar los campos inmediatamente.
     */
    public LoginRequest() {
    }

    // Métodos Getters y Setters
    /**
     * Devuelve el correo electrónico del usuario.
     *
     * @return El correo electrónico como una cadena.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email El correo electrónico a establecer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve la contraseña del usuario.
     *
     * @return La contraseña como una cadena.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password La contraseña a establecer.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
