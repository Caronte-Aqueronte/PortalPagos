/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services;

import ss1.api.models.Usuario;

/**
 *
 * @author luid
 */
@org.springframework.stereotype.Service
public class SaldoService extends Service {

    public boolean usuarioTieneSaldo(Usuario usuario) {
        return usuario.getSaldo().getSaldoDisponible() != 0;
    }
}
