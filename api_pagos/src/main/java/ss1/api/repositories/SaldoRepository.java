/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ss1.api.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ss1.api.models.Saldo;

/**
 *
 * @author Luis Monterroso
 */
public interface SaldoRepository extends CrudRepository<Saldo, Long> {

    public Optional<Saldo> findSaldoByUsuario_id(Long idUsuario);
}
