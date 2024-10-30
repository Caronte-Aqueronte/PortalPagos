/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services;

import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ss1.api.excepciones.NotFoundException;
import ss1.api.excepciones.UnauthorizedException;
import ss1.api.models.Recarga;
import ss1.api.models.Retiro;
import ss1.api.models.Saldo;
import ss1.api.models.TransaccionFallida;
import ss1.api.models.Usuario;
import ss1.api.models.dto.SaldoDTO;
import ss1.api.models.request.MovimientoExternoRequest;
import ss1.api.repositories.SaldoRepository;
import ss1.api.services.bancos.BancoService;
import ss1.api.services.bancos.BancoServiceFactory;

/**
 *
 * @author luid
 */
@org.springframework.stereotype.Service
public class SaldoService extends Service {

    @Autowired
    private SaldoRepository saldoRepository;
    @Autowired
    private RecargaService recargaService;
    @Autowired
    private RetiroService retiroService;
    @Autowired
    @Lazy
    private UsuarioService usuarioService;
    @Autowired
    private TransaccionFallidaService transaccionFallidaService;
    @Autowired
    private BancoServiceFactory bancoServiceFactory;

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

    @Transactional(rollbackOn = Exception.class)
    public String recargarSaldo(MovimientoExternoRequest recargaRequest) {
        //validar
        validarModelo(recargaRequest);
        try {
            recargar(recargaRequest.getMonto(), recargaRequest.getBanco());
            BancoService bancoService = bancoServiceFactory.getBancoService(recargaRequest.getBanco());
            String token = bancoService.login(recargaRequest.getEmail(),
                    recargaRequest.getPin());
            bancoService.recargarDesdeBanco(token, recargaRequest.getMonto());
            return "Se recargo el monto con exito.";
        } catch (Exception ex) {
            TransaccionFallida transaccionFallida = new TransaccionFallida(
                    "N/A",
                    LocalDateTime.now(),
                    ex.getMessage(),
                    recargaRequest.getMonto(),
                    "Recarga desde banco",
                    recargaRequest.getBanco(),
                    recargaRequest.getEmail()
            );
            this.transaccionFallidaService.guardarTransaccionFallida(transaccionFallida);
            throw ex;
        }

    }

    @Transactional(rollbackOn = Exception.class)
    public String retirarSaldo(MovimientoExternoRequest recargaRequest) {
        //validar
        validarModelo(recargaRequest);
        try {
            //retiramos el saldo
            String retirar = retirar(recargaRequest.getMonto(), recargaRequest.getBanco(),
                    recargaRequest.getEmail());
            //si no hay ningun problema entonces mandamos el dinero al banco
            BancoService bancoService = bancoServiceFactory.getBancoService(recargaRequest.getBanco());
            String token = bancoService.login(recargaRequest.getEmail(),
                    recargaRequest.getPin());
            bancoService.retirarABanco(token, recargaRequest.getMonto());
            return "Se retiro el monto con exito.";
        } catch (Exception ex) {

            TransaccionFallida transaccionFallida = new TransaccionFallida(
                    "N/A",
                    LocalDateTime.now(),
                    ex.getMessage(),
                    recargaRequest.getMonto(),
                    "Retiro a banco",
                    recargaRequest.getBanco(),
                    recargaRequest.getEmail()
            );
            this.transaccionFallidaService.guardarTransaccionFallida(transaccionFallida);

            throw ex;
        }
    }

    @Transactional(rollbackOn = Exception.class)
    private String recargar(Double montoRecarga, String entidadFinanciera) throws NotFoundException {
        //traer el usuario
        Usuario usuario = usuarioService.getUsuarioUseJwt();
        //sumarle el saldo al usuario
        usuario.getSaldo().setSaldoDisponible(
                usuario.getSaldo().getSaldoDisponible() + montoRecarga);
        //crear la recarga
        recargaService.guardarRecarga(new Recarga(usuario,
                montoRecarga, entidadFinanciera));
        //guardar la actualizacion del usuario
        usuarioService.getUsuarioRepository().save(usuario);
        return "Se recargo con exito.";
    }

    @Transactional(rollbackOn = Exception.class)
    private String retirar(Double montoRetiro, String entidadFinanciera,
            String cuentaDestino) throws NotFoundException,
            UnauthorizedException {

        //traer el usuario
        Usuario usuario = usuarioService.getUsuarioUseJwt();
        //verificar si el usuario tiene suficiente dinero
        boolean tieneSuficienteSaldo
                = usuarioTieneSaldoSuficiente(usuario, montoRetiro);

        if (!tieneSuficienteSaldo) {
            throw new UnauthorizedException("Saldo insuficiente.");
        }

        //sumarle el saldo al usuario
        usuario.getSaldo().setSaldoDisponible(usuario.getSaldo().getSaldoDisponible() - montoRetiro);
        //crear el retiro
        retiroService.guardarRetiro(new Retiro(usuario,
                montoRetiro, cuentaDestino, entidadFinanciera
        ));
        //guardar la actualizacion del usuario
        usuarioService.getUsuarioRepository().save(usuario);
        return "Se retiro con exito.";
    }
}
