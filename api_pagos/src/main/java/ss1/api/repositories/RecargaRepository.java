/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ss1.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ss1.api.models.Recarga;

/**
 *
 * @author Luis Monterroso
 */
@Repository
public interface RecargaRepository extends CrudRepository<Recarga, Long> {

}
