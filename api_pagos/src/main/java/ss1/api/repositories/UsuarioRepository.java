/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ss1.api.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ss1.api.models.Usuario;

/**
 *
 * @author luid
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    public Optional<Usuario> findOneByEmail(String email);

    public Optional<Usuario> findOneByNit(String nit);
}
