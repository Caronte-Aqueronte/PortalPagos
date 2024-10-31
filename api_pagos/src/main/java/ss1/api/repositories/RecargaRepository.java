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
import ss1.api.models.Recarga;

/**
 *
 * @author Luis Monterroso
 */
@Repository
public interface RecargaRepository extends CrudRepository<Recarga, Long> {

    @Query("SELECT r FROM Recarga r WHERE r.usuario.id = :usuarioId  ORDER BY r.createdAt DESC")
    public List<Recarga> findTop20ByUsuarioIdOrderByCreatedAtDesc(@Param("usuarioId") Long usuarioId);

    @Query("SELECT r FROM Recarga r WHERE r.usuario.id = :usuarioId "
            + "AND (:fechaInicio IS NULL OR DATE(r.createdAt) >= :fechaInicio) "
            + "AND (:fechaFin IS NULL OR DATE(r.createdAt)  <= :fechaFin) "
            + "ORDER BY r.createdAt DESC")
    public List<Recarga> findByUsuarioAndFechaBetween(
            @Param("usuarioId") Long usuarioId,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin);

    @Query("SELECT r FROM Recarga r WHERE "
            + "(:fechaInicio IS NULL OR DATE(r.createdAt) >= :fechaInicio) "
            + "AND (:fechaFin IS NULL OR DATE(r.createdAt)  <= :fechaFin) "
            + "ORDER BY r.createdAt DESC")
    public List<Recarga> findByFechaBetween(
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin);

}
