/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ss1.api.models.Usuario;
import ss1.api.models.request.MovimientoRequest;

/**
 *
 * @author luid
 */
@org.springframework.stereotype.Service
public class MovimientoService extends Service {
    
    @Autowired
    private UsuarioService usuarioService;

    @Transactional(rollbackOn = Exception.class)

    public String generarMovimiento(MovimientoRequest movimiento) {
        //debemos traer el usuario remitente con el jwt
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       
        String emailUsuarioAutenticado = authentication.getName();
        Usuario usuarioRemitente = this.usuarioService.ge
        
        
        //ahora debemos ver si el usuario autenticado tiene los fondos suficientes para hacer el debito
        

        return "";
    }
}
