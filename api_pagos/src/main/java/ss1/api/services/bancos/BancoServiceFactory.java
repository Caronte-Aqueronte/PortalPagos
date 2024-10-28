/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services.bancos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class BancoServiceFactory {

    @Autowired
    private BancoCreditoService bancoCreditoService;

    public BancoService getBancoService(String tipoBanco) {
        if ("credito".equalsIgnoreCase(tipoBanco)) {
            return bancoCreditoService;
        } else if ("debito".equalsIgnoreCase(tipoBanco)) {
            return null;
        }
        throw new IllegalArgumentException("Tipo de banco no soportado: " + tipoBanco);
    }

}
