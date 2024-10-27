/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ss1.api.excepciones.ConflictException;
import ss1.api.excepciones.NotFoundException;
import ss1.api.excepciones.UnauthorizedException;
import ss1.api.models.Transaccion;
import ss1.api.models.TransaccionFallida;
import ss1.api.models.Usuario;
import ss1.api.models.dto.TransaccionDTO;
import ss1.api.models.request.PagoExternoRequest;
import ss1.api.models.request.PagoRequest;
import ss1.api.reportes.printers.ComprobantePrinter;
import ss1.api.repositories.TransaccionRepository;
import ss1.api.tools.ManejadorTiempo;

/**
 *
 * @author luid
 */
@org.springframework.stereotype.Service
public class TransaccionService extends Service {

    @Value("${externo.servicio.urlTiendaA}")
    private String urlTiendaA;
    @Value("${externo.servicio.urlTiendaB}")
    private String urlTiendaB;

    //temporal
    String[] domains = {
        "amazon.com",
        "ebay.com",
        "alibaba.com",
        "etsy.com",
        "walmart.com",
        "mercadolibre.com",
        "shopify.com",
        "rakuten.com",
        "target.com",
        "bestbuy.com"
    };

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
    @Autowired
    private ComprobantePrinter comprobantePrinter;

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
    private Transaccion procesarPago(PagoRequest pago) throws NotFoundException,
            ConflictException {
        validarModelo(pago);
        //debemos traer el usuario que desea realizar el pago
        Usuario emisor = usuarioService.getUsuarioUseJwt();
        //traer el usuario destinatario
        Usuario receptor = usuarioService.getByEmail(pago.getCorreoReceptor());

        //verificar que el receptor no sea le mismo que el emisor
        if (Objects.equals(emisor.getId(), receptor.getId())) {
            throw new ConflictException("No puedes transferirte a ti mismo.");
        }

        //verificamos que el usuario tenga salgo
        boolean usuarioTieneSaldo = saldoService.usuarioTieneSaldo(emisor);

        boolean usuarioTieneSaldoSuficiente = saldoService.usuarioTieneSaldoSuficiente(emisor,
                pago.getCantidad());

        if (!usuarioTieneSaldo || !usuarioTieneSaldoSuficiente) {

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
        return transaccionRepository.save(transaccion);
    }

    @Transactional(rollbackOn = Exception.class)
    public TransaccionDTO pagar(PagoRequest pago) throws NotFoundException,
            ConflictException {
        validarModelo(pago);
        Transaccion guardarTransaccion = procesarPago(pago);
        //ahora debemos ver si el usuario autenticado tiene los fondos suficientes para hacer el debito
        return constuirTransaccionDTO(guardarTransaccion);
    }

    @Transactional(rollbackOn = Exception.class)
    public byte[] pagarGetComprobante(PagoExternoRequest pago) throws NotFoundException,
            ConflictException, Exception {
        validarModelo(pago);

        // Inicializar el RestTemplate para realizar la solicitud GET
        RestTemplate restTemplate = new RestTemplate();
        String url;

        //TEMPORAL OBTENCION DE UN LOGO EN SERVICIO EXTERNO
        Random random = new Random();
        int randomIndex = random.nextInt(domains.length);

        //mandar a traer la informacion de la tienda segun su identificadorF
        switch (pago.getIdentificadorTienda()) {
            case "a":
                url = urlTiendaA + "/" + domains[randomIndex];
                break;
            case "b":
                url = urlTiendaB;
                break;
            default:
                throw new NotFoundException("Tienda no reconocida.");

        }

        //ahora debemos hacer la peticion get para conseguir la imagen de la tienda
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);

        byte[] imagenTienda;

        // Verificar si la respuesta es exitosa (200 OK)
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            imagenTienda = response.getBody(); // Devolver el byte array de la imagen
        } else {
            throw new NotFoundException("No se pudo obtener la informacion de la tienda.");
        }

        //procesar el pago
        Transaccion guardarTransaccion = procesarPago(pago);

        //mandamos a imprimir el comprobante de pago y retornamos
        return this.comprobantePrinter.init(
                constuirTransaccionDTO(guardarTransaccion),
                pago.getNombreTienda(),
                imagenTienda);
    }

    /**
     * Obtiene las últimas 20 transacciones de un usuario específico donde el
     * usuario actúa como emisor o receptor. Este método primero verifica la
     * identidad del usuario y luego recupera las transacciones más recientes,
     * devolviéndolas como una lista de {@link TransaccionDTO}.
     *
     * @param idUsuario ID del usuario cuyas transacciones se desean consultar.
     * @return Lista de objetos {@link TransaccionDTO} que representa las
     * últimas 20 transacciones del usuario.
     * @throws UnauthorizedException si el usuario no tiene autorización para
     * realizar esta consulta.
     * @throws NotFoundException si el usuario especificado no se encuentra en
     * el sistema.
     */
    public List<TransaccionDTO> getMisUltimasTransacciones(Long idUsuario) throws UnauthorizedException,
            NotFoundException {
        //verificar la identidad del usuario
        Usuario usuario = usuarioService.getUsuarioById(new Usuario(idUsuario));
        this.verificarUsuarioJwt(usuario);
        //mandar a traer el top 20 de transacciones del usuario
        List<Transaccion> _20Transacciones
                = transaccionRepository.findTop20ByUsuarioIdOrderByCreatedAtDesc(idUsuario);
        return constuirTransaccionesDTOS(_20Transacciones);
    }

    public List<TransaccionDTO> getMisPagosEnDosFechas(Long idUsuario,
            LocalDate fecha1, LocalDate fecha2) throws UnauthorizedException,
            NotFoundException {
        //verificar la identidad del usuario
        Usuario usuario = usuarioService.getUsuarioById(new Usuario(idUsuario));
        this.verificarUsuarioJwt(usuario);

        Date fecha1Date = null;
        Date fecha2Date = null;

        if (fecha1 != null) {
            fecha1Date = Date.valueOf(fecha1);
        }

        if (fecha2 != null) {
            fecha2Date = Date.valueOf(fecha2);
        }
        //mandar a traer el top 20 de transacciones del usuario
        List<Transaccion> transacciones
                = transaccionRepository.findByUsuarioEmisorAndFechaBetween(idUsuario,
                        fecha1Date, fecha2Date);
        return constuirTransaccionesDTOS(transacciones);
    }

    public List<TransaccionDTO> getMisIngresosEnDosFechas(Long idUsuario,
            LocalDate fecha1, LocalDate fecha2) throws UnauthorizedException,
            NotFoundException {
        //verificar la identidad del usuario
        Usuario usuario = usuarioService.getUsuarioById(new Usuario(idUsuario));
        this.verificarUsuarioJwt(usuario);

        Date fecha1Date = null;
        Date fecha2Date = null;

        if (fecha1 != null) {
            fecha1Date = Date.valueOf(fecha1);
        }

        if (fecha2 != null) {
            fecha2Date = Date.valueOf(fecha2);
        }
        //mandar a traer el top 20 de transacciones del usuario
        List<Transaccion> transacciones
                = transaccionRepository.findByUsuarioReceptorAndFechaBetween(idUsuario,
                        fecha1Date, fecha2Date);
        return constuirTransaccionesDTOS(transacciones);
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
                manejadorMoneda.cantidadAFormatoRegional(transaccion.getMonto()),
                transaccion.getConcepto(),
                transaccion.getEmisor().getEmail(),
                transaccion.getReceptor().getEmail(),
                this.manejadorTiempo.parsearFechaYHoraAFormatoRegional(
                        transaccion.getCreatedAt().toLocalDate())
        );
    }

    /**
     * Convierte una lista de entidades de tipo {@link Transaccion} a una lista
     * de objetos de tipo {@link TransaccionDTO}. Este método utiliza el método
     * auxiliar {@code constuirTransaccionDTO} para construir cada
     * {@link TransaccionDTO}.
     *
     * @param transacciones Lista de objetos {@link Transaccion} que se desea
     * convertir a DTOs.
     * @return Lista de objetos {@link TransaccionDTO} correspondiente a las
     * transacciones proporcionadas.
     */
    private List<TransaccionDTO> constuirTransaccionesDTOS(List<Transaccion> transacciones) {
        ArrayList<TransaccionDTO> transaccionesDTOS = new ArrayList<>();
        //por cada transaccion en la lista crear un DTO
        for (Transaccion item : transacciones) {
            transaccionesDTOS.add(constuirTransaccionDTO(item));
        }
        return transaccionesDTOS;
    }
}
