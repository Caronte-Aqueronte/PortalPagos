/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services.bancos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ss1.api.excepciones.NotFoundException;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class BancoServiceFactory {

    @Autowired
    private BancoCreditoService bancoCreditoService;

    @Autowired
    private BancoDebitoService bancoDebitoService;

    public BancoService getBancoService(String tipoBanco) throws NotFoundException {
        if ("credito".equalsIgnoreCase(tipoBanco)) {
            return bancoCreditoService;
        } else if ("debito".equalsIgnoreCase(tipoBanco)) {
            return bancoDebitoService;
        }
        throw new NotFoundException("Tipo de banco no soportado: " + tipoBanco);
    }

}
