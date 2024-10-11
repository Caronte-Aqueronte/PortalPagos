/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ss1.api.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ss1.api.models.Rol;

/**
 *
 * @author luid
 */
public interface RolRepository extends CrudRepository<Rol, Long> {

    Optional<Rol> findOneByNombre(String nombre);
}
