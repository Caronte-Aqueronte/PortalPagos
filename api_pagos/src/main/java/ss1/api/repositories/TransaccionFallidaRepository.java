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
import ss1.api.models.TransaccionFallida;

/**
 *
 * @author Luis Monterroso
 */
@Repository
public interface TransaccionFallidaRepository extends CrudRepository<TransaccionFallida, Long> {

    @Query("SELECT t FROM TransaccionFallida t "
            + "WHERE (:fechaInicio IS NULL OR DATE(t.createdAt) >= :fechaInicio) "
            + "AND (:fechaFin IS NULL OR DATE(t.createdAt)  <= :fechaFin) "
            + "ORDER BY t.createdAt DESC")
    public List<TransaccionFallida> findByFechaBetween(
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin);
}
