/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services;

import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import ss1.api.excepciones.ConflictException;
import ss1.api.excepciones.NotFoundException;
import ss1.api.models.Transaccion;
import ss1.api.models.TransaccionFallida;
import ss1.api.models.Usuario;
import ss1.api.models.dto.TransaccionDTO;
import ss1.api.models.request.PagoRequest;
import ss1.api.repositories.TransaccionRepository;
import ss1.api.tools.ManejadorTiempo;

/**
 *
 * @author luid
 */
@org.springframework.stereotype.Service
public class TransaccionService extends Service {

    @Autowired
    private TransaccionRepository transaccionRepository;
    @Autowired
    private ManejadorTiempo manejadorTiempo;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private SaldoService saldoService;
    @Autowired
    private TransaccionFallidaService transaccionFallidaService;

    /**
     * Procesa un pago entre un emisor y un receptor. Verifica si el emisor
     * tiene saldo suficiente, transfiere los fondos, registra la transacción y,
     * en caso de error, guarda una transacción fallida.
     *
     * @param pago Objeto {@link PagoRequest} con los detalles del pago,
     * incluyendo el monto y el receptor.
     * @return Un {@link TransaccionDTO} con los detalles de la transacción
     * completada.
     * @throws NotFoundException Si el usuario receptor no es encontrado.
     * @throws ConflictException Si el emisor no tiene saldo suficiente o hay un
     * conflicto en el proceso.
     */
    @Transactional(rollbackOn = Exception.class)
    public TransaccionDTO procesarPago(PagoRequest pago) throws NotFoundException,
            ConflictException {
        //debemos traer el usuario que desea realizar el pago
        Usuario emisor = usuarioService.getUsuarioUseJwt();
        //traer el usuario destinatario
        Usuario receptor = usuarioService.getByEmail(pago.getCorreoReceptor());
        //verificamos que el usuario tenga salgo
        boolean usuarioTieneSaldo = saldoService.usuarioTieneSaldo(emisor);

        if (!usuarioTieneSaldo) {

            TransaccionFallida transaccionFallida = new TransaccionFallida(
                    emisor,
                    LocalDateTime.now(),
                    "Usuario sin fondos",
                    pago.getCantidad(),
                    "TRANSFERENCIA",
                    "N/A",
                    receptor.getEmail()
            );
            this.transaccionFallidaService.guardarTransaccionFallida(transaccionFallida);
            throw new ConflictException("Usuario sin fondos.");
        }
        //si el usuario tiene fondos entonces debemos transeferir los fondos al destinatario
        saldoService.transferirFondos(emisor, receptor, pago.getCantidad());

        //debemos persistir a los dos usuarios ahora
        usuarioService.getUsuarioRepository().save(emisor);
        usuarioService.getUsuarioRepository().save(receptor);

        //debemos crear la transaccion
        Transaccion transaccion = new Transaccion(
                pago.getCantidad(),
                pago.getConcepto(), receptor,
                emisor);

        //persistir la transaccion
        Transaccion save = transaccionRepository.save(transaccion);
        //ahora debemos ver si el usuario autenticado tiene los fondos suficientes para hacer el debito
        return constuirTransaccionDTO(transaccion);
    }

    /**
     * Construye un objeto {@link TransaccionDTO} a partir de una entidad
     * {@link Transaccion}.
     *
     * @param transaccion La entidad {@link Transaccion} desde la cual se
     * construirá el DTO.
     * @return Un {@link TransaccionDTO} que contiene la información relevante
     * de la transacción.
     */
    private TransaccionDTO constuirTransaccionDTO(Transaccion transaccion) {
        return new TransaccionDTO(
                transaccion.getId(),
                transaccion.getMonto(),
                transaccion.getConcepto(),
                transaccion.getEmisor().getEmail(),
                transaccion.getReceptor().getEmail(),
                this.manejadorTiempo.localDateANombreDia(
                        transaccion.getCreatedAt().toLocalDate())
        );
    }
}
