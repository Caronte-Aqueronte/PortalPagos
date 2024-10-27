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
import ss1.api.models.Transaccion;

/**
 *
 * @author Luis Monterroso
 */
@Repository
public interface TransaccionRepository extends CrudRepository<Transaccion, Long> {

    /**
     * Encuentra las 20 transacciones más recientes en las que el usuario es el
     * emisor o receptor, ordenadas por la fecha de creación en orden
     * descendente.
     *
     * @param usuarioId ID del usuario que actúa como emisor o receptor.
     * @return Lista de las 20 transacciones más recientes.
     */
    @Query("SELECT t FROM Transaccion t WHERE t.emisor.id = :usuarioId OR t.receptor.id = :usuarioId ORDER BY t.createdAt DESC")
    public List<Transaccion> findTop20ByUsuarioIdOrderByCreatedAtDesc(@Param("usuarioId") Long usuarioId);

    /**
     * Encuentra todas las transacciones en las que el usuario es el emisor,
     * filtrando por la fecha de creación (createdAt) entre dos fechas
     * especificadas. Si ambas fechas son nulas, devuelve todas las
     * transacciones del usuario. Si solo una fecha es nula, se ajusta para
     * seleccionar desde o hasta el límite disponible.
     *
     * @param usuarioId ID del usuario que actúa como emisor.
     * @param fechaInicio Fecha de inicio del filtro (puede ser nula).
     * @param fechaFin Fecha de fin del filtro (puede ser nula).
     * @return Lista de transacciones en las que el usuario es el emisor y
     * cumplen con el filtro de fecha.
     */
    @Query("SELECT t FROM Transaccion t WHERE t.emisor.id = :usuarioId "
            + "AND (:fechaInicio IS NULL OR DATE(t.createdAt) >= :fechaInicio) "
            + "AND (:fechaFin IS NULL OR DATE(t.createdAt)  <= :fechaFin) "
            + "ORDER BY t.createdAt DESC")
    public List<Transaccion> findByUsuarioEmisorAndFechaBetween(
            @Param("usuarioId") Long usuarioId,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin);

    /**
     * Encuentra todas las transacciones en las que el usuario es el receptor,
     * filtrando por la fecha de creación (createdAt) entre dos fechas
     * especificadas. Si ambas fechas son nulas, devuelve todas las
     * transacciones del usuario. Si solo una fecha es nula, se ajusta para
     * seleccionar desde o hasta el límite disponible.
     *
     * @param usuarioId ID del usuario que actúa como receptor.
     * @param fechaInicio Fecha de inicio del filtro (puede ser nula).
     * @param fechaFin Fecha de fin del filtro (puede ser nula).
     * @return Lista de transacciones en las que el usuario es el receptor y
     * cumplen con el filtro de fecha.
     */
    @Query("SELECT t FROM Transaccion t WHERE t.receptor.id = :usuarioId "
            + "AND (:fechaInicio IS NULL OR DATE(t.createdAt)  >= :fechaInicio) "
            + "AND (:fechaFin IS NULL OR DATE(t.createdAt)  <= :fechaFin) "
            + "ORDER BY t.createdAt DESC")
    public List<Transaccion> findByUsuarioReceptorAndFechaBetween(
            @Param("usuarioId") Long usuarioId,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin);
}
