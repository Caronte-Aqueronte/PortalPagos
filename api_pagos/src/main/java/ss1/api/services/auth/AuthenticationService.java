/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ss1.api.models.Usuario;
import ss1.api.repositories.UsuarioRepository;

/**
 *
 * @author luism
 */
@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findOneByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Usuario no encontrado")
        );
        User.UserBuilder userBuilder = User.withUsername(username);
        userBuilder.password(usuario.getPassword()).roles(
                usuario.getRol().getNombre());
        return userBuilder.build();
    }
}
