/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ss1.api.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ss1.api.models.Usuario;

/**
 *
 * @author luid
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    public Optional<Usuario> findOneByEmail(String email);

    public boolean existsByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.id <> :idUsuario AND u.deletedAt IS NULL")
    List<Usuario> findAllExceptUser(@Param("idUsuario") Long idUsuario);

    // Consultar solo productos que no estén eliminados (soft deleted)
    @Query("SELECT p FROM Usuario p WHERE p.deleted = false")
    public List<Usuario> findAllActive();

    public List<Usuario> findAllByDeletedAtIsNotNull();

    public List<Usuario> findAllByDeletedAtIsNull();

    public List<Usuario> findAllByRol_Nombre(String nombre);

    public List<Usuario> findAllByRol_NombreAndDeletedAtIsNotNull(String rolNombre);

    public List<Usuario> findAllByRol_NombreAndDeletedAtIsNull(String rolNombre);

}
