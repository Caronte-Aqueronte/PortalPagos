/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ss1.api.excepciones.NotFoundException;
import ss1.api.excepciones.UnauthorizedException;
import ss1.api.models.Saldo;
import ss1.api.models.Usuario;
import ss1.api.models.dto.SaldoDTO;
import ss1.api.repositories.SaldoRepository;

/**
 *
 * @author luid
 */
@org.springframework.stereotype.Service
public class SaldoService extends Service {

    @Autowired
    private SaldoRepository saldoRepository;
    @Autowired
    @Lazy
    private UsuarioService usuarioService;

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
     * Verifica si un usuario tiene saldo suficiente para una operación
     * específica.
     *
     * @param usuario Objeto de tipo {@link Usuario} cuyo saldo se desea
     * verificar.
     * @param cantidadNecesaria Cantidad requerida para realizar la operación.
     * @return {@code true} si el saldo disponible del usuario es mayor o igual
     * a la cantidad necesaria, {@code false} en caso contrario.
     */
    public boolean usuarioTieneSaldoSuficiente(Usuario usuario, Double cantidadNecesaria) {
        return usuario.getSaldo().getSaldoDisponible() >= cantidadNecesaria;
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

    /**
     * Obtiene el saldo de un usuario específico.
     *
     * @param indUsuario Identificador único del usuario cuyo saldo se desea
     * obtener.
     * @return Objeto de tipo {@link Saldo} correspondiente al saldo del
     * usuario.
     * @throws NotFoundException si el saldo del usuario no se encuentra en la
     * base de datos.
     */
    public SaldoDTO getMiSaldo(Long indUsuario) throws NotFoundException, UnauthorizedException {
        Usuario usuario = usuarioService.getUsuarioById(new Usuario(indUsuario));
        this.verificarUsuarioJwt(usuario);
        Saldo saldo = saldoRepository.findSaldoByUsuario_id(indUsuario)
                .orElseThrow(() -> new NotFoundException("Saldo no encontrado."));
        return new SaldoDTO(
                this.manejadorMoneda.cantidadAFormatoRegional(saldo.getSaldoDisponible()));
    }
}
