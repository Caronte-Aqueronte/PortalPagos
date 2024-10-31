/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import ss1.api.models.TransaccionFallida;
import ss1.api.repositories.TransaccionFallidaRepository;

/**
 *
 * @author Luis Monterroso
 */
@org.springframework.stereotype.Service
public class TransaccionFallidaService extends Service {

    @Autowired
    private TransaccionFallidaRepository transaccionFallidaRepository;

    /**
     * Guarda una transacción fallida en la base de datos.
     *
     * @param transaccionFallida Objeto {@link TransaccionFallida} a guardar.
     * @return La transacción fallida guardada.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TransaccionFallida guardarTransaccionFallida(TransaccionFallida transaccionFallida) {
        return transaccionFallidaRepository.save(transaccionFallida);
    }

}
