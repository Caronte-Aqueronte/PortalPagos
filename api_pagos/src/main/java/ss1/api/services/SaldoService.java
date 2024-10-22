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

    /**
     * Verifica si el usuario tiene saldo disponible.
     *
     * @param usuario El usuario cuyo saldo se está verificando.
     * @return {@code true} si el usuario tiene saldo disponible distinto de 0,
     * {@code false} de lo contrario.
     */
    public boolean usuarioTieneSaldo(Usuario usuario) {
        return usuario.getSaldo().getSaldoDisponible() != 0;
    }

    /**
     * Transfiere una cantidad de dinero entre dos usuarios. Resta la cantidad
     * del remitente y la suma al destinatario.
     *
     * @param remitente El usuario que envía los fondos.
     * @param destinatario El usuario que recibe los fondos.
     * @param transferencia La cantidad de dinero a transferir.
     */
    public void transferirFondos(Usuario remitente, Usuario destinatario, Double transferencia) {
        //restamos al saldo del remitente la transferencia
        remitente.getSaldo().setSaldoDisponible(
                remitente.getSaldo().getSaldoDisponible() - transferencia
        );
        //sumamos al saldo del destinatario la transferencia
        destinatario.getSaldo().setSaldoDisponible(
                destinatario.getSaldo().getSaldoDisponible() + transferencia
        );
    }
}
