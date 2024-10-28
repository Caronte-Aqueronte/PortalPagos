/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import ss1.api.models.Recarga;
import ss1.api.repositories.RecargaRepository;

/**
 *
 * @author Luis Monterroso
 */
@org.springframework.stereotype.Service
public class RecargaService {

    @Autowired
    private RecargaRepository recargaRepository;

    @Transactional(rollbackOn = Exception.class)
    public Recarga guardarRecarga(Recarga recarga) {

        return recargaRepository.save(recarga);
    }
}
