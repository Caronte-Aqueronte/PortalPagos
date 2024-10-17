/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services;

import ss1.api.excepciones.UnauthorizedException;
import ss1.api.models.Usuario;
import ss1.api.services.tools.Validador;

/**
 *
 * @author luid
 */
@org.springframework.stereotype.Service
public class Service extends Validador {

    protected boolean verificarUsuarioJwt(Usuario usuarioTratar, String emailUsuarioAutenticado) throws UnauthorizedException {
        // validar si el usuario tiene permiso de eliminar
        if (!emailUsuarioAutenticado.equals(usuarioTratar.getEmail())
                && !isUserAdmin(emailUsuarioAutenticado)) {
            throw new UnauthorizedException("No tienes permiso para realizar acciones a este usuario.");
        }
        return true;
    }
}
