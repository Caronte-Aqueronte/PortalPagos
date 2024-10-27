/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ss1.api.excepciones.UnauthorizedException;
import ss1.api.models.Usuario;
import ss1.api.services.tools.Validador;
import ss1.api.tools.ManejadorMoneda;

/**
 *
 * @author luid
 */
@org.springframework.stereotype.Service
public class Service extends Validador {

    @Autowired
    protected ManejadorMoneda manejadorMoneda;

    /**
     * Verifica si el usuario autenticado tiene permiso para realizar acciones
     * sobre el usuario proporcionado. Lanza una excepción si el usuario
     * autenticado no es el mismo o no tiene privilegios de administrador.
     *
     * @param usuarioTratar El usuario sobre el que se intenta realizar una
     * acción.
     * @throws UnauthorizedException Si el usuario autenticado no tiene permisos
     * suficientes.
     */
    public void verificarUsuarioJwt(Usuario usuarioTratar) throws UnauthorizedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuarioAutenticado = authentication.getName();
        // validar si el usuario tiene permiso de eliminar
        if (!emailUsuarioAutenticado.equals(usuarioTratar.getEmail())
                && !isUserAdmin(emailUsuarioAutenticado)) {
            throw new UnauthorizedException("No tienes permiso para realizar acciones a este usuario.");
        }
    }

    /**
     * Obtiene el correo electrónico del usuario autenticado a partir del token
     * JWT.
     *
     * @return El correo electrónico del usuario autenticado.
     */
    public String getEmaiJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
