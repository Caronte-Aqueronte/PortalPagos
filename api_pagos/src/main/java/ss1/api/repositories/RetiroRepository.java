/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ss1.api.repositories;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ss1.api.models.Retiro;

/**
 *
 * @author Luis Monterroso
 */
@Repository
public interface RetiroRepository extends CrudRepository<Retiro, Long> {

    @Query("SELECT r FROM Retiro r WHERE r.usuario.id = :usuarioId  ORDER BY r.createdAt DESC")
    public List<Retiro> findTop20ByUsuarioIdOrderByCreatedAtDesc(@Param("usuarioId") Long usuarioId);

    @Query("SELECT r FROM Retiro r WHERE r.usuario.id = :usuarioId "
            + "AND (:fechaInicio IS NULL OR DATE(r.createdAt) >= :fechaInicio) "
            + "AND (:fechaFin IS NULL OR DATE(r.createdAt)  <= :fechaFin) "
            + "ORDER BY r.createdAt DESC")
    public List<Retiro> findByUsuarioAndFechaBetween(
            @Param("usuarioId") Long usuarioId,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin);

}
