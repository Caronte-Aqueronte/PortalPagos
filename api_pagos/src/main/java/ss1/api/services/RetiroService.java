/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import ss1.api.models.Retiro;
import ss1.api.repositories.RetiroRepository;

/**
 *
 * @author Luis Monterroso
 */
@org.springframework.stereotype.Service
public class RetiroService {

    @Autowired
    private RetiroRepository retiroRepository;

    @Transactional(rollbackOn = Exception.class)
    public Retiro guardarRetiro(Retiro retiro) {
        return retiroRepository.save(retiro);
    }
}
