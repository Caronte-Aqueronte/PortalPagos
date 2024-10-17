package ss1.api.models.dto;

import org.springframework.stereotype.Component;
import ss1.api.models.Usuario;

/**
 * La clase LoginDTO es un objeto de transferencia de datos (DTO) que encapsula
 * la información del usuario autenticado y el token JWT generado tras el login.
 * Esta clase facilita el envío de estos datos desde el servidor hacia el
 * cliente después de un proceso de autenticación.
 *
 * Anotada con @Component, la clase está disponible como un componente de
 * Spring, lo que permite su inyección en otros servicios o controladores cuando
 * sea necesario.
 *
 * Atributos: - Usuario: Objeto que contiene los detalles del usuario
 * autenticado. - jwt: Cadena que representa el token JWT asociado con el
 * usuario autenticado.
 *
 * Esta clase incluye un constructor completo, un constructor vacío y métodos
 * getters y setters para acceder y modificar los atributos.
 *
 * @author luid
 */
@Component
public class LoginDTO {

    // Objeto Usuario que contiene los detalles del usuario autenticado
    private Usuario usuario;

    // Token JWT generado después de que el usuario se autentica
    private String jwt;

    /**
     * Constructor completo que inicializa los campos usuario y jwt.
     *
     * @param usuario Objeto Usuario que contiene la información del usuario
     * autenticado.
     * @param jwt Token JWT generado para la autenticación del usuario.
     */
    public LoginDTO(Usuario usuario, String jwt) {
        this.usuario = usuario;
        this.jwt = jwt;
    }

    /**
     * Constructor vacío para permitir la creación de instancias sin inicializar
     * los atributos de inmediato.
     */
    public LoginDTO() {
    }

    // Métodos Getters y Setters
    /**
     * Devuelve el objeto Usuario asociado a este DTO.
     *
     * @return El objeto Usuario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Asigna un objeto Usuario a este DTO.
     *
     * @param usuario El objeto Usuario que se va a asociar.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Devuelve el token JWT asociado a este DTO.
     *
     * @return El token JWT como una cadena.
     */
    public String getJwt() {
        return jwt;
    }

    /**
     * Asigna un token JWT a este DTO.
     *
     * @param jwt El token JWT que se va a asociar.
     */
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}
