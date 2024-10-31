/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ss1.api.excepciones.NotFoundException;
import ss1.api.models.Recarga;
import ss1.api.models.Usuario;
import ss1.api.models.dto.RecargaDTO;
import ss1.api.repositories.RecargaRepository;
import ss1.api.tools.ManejadorTiempo;

/**
 *
 * @author Luis Monterroso
 */
@org.springframework.stereotype.Service
public class RecargaService extends Service {

    @Autowired
    private RecargaRepository recargaRepository;
    @Autowired
    @Lazy
    private UsuarioService usuarioService;
    @Autowired
    @Lazy
    private ManejadorTiempo manejadorTiempo;

    @Transactional(rollbackOn = Exception.class)
    public Recarga guardarRecarga(Recarga recarga) {

        return recargaRepository.save(recarga);
    }

    public List<RecargaDTO> getMisUltimos20Recargas() throws NotFoundException {
        //obtenemos el usurio
        Usuario usuario = this.usuarioService.getUsuarioUseJwt();

        //ahora mandamos a buscar las 20 recargas del cliente
        List<Recarga> recargas = recargaRepository.findTop20ByUsuarioIdOrderByCreatedAtDesc(usuario.getId());
        return constuirRecargasDTOS(recargas);
    }

    public List<RecargaDTO> getMisRecargasEnDosFechas(LocalDate fecha1, LocalDate fecha2)
            throws NotFoundException {
        //obtenemos el usurio
        Usuario usuario = this.usuarioService.getUsuarioUseJwt();

        Date fecha1Date = null;
        Date fecha2Date = null;

        if (fecha1 != null) {
            fecha1Date = Date.valueOf(fecha1);
        }

        if (fecha2 != null) {
            fecha2Date = Date.valueOf(fecha2);
        }

        //ahora mandamos a buscar las 20 recargas del cliente
        List<Recarga> recargas = recargaRepository.findByUsuarioAndFechaBetween(usuario.getId(),
                fecha1Date, fecha2Date);
        return constuirRecargasDTOS(recargas);
    }

    private RecargaDTO constuirRecargaDTO(Recarga recarga) {
        return new RecargaDTO(
                recarga.getId(),
                recarga.getUsuario().getEmail(),
                manejadorMoneda.cantidadAFormatoRegional(recarga.getMontoRecargado()),
                manejadorTiempo.parsearFechaYHoraAFormatoRegional(
                        recarga.getCreatedAt().toLocalDate()
                ),
                recarga.getEntidadFinanciera()
        );
    }

    private List<RecargaDTO> constuirRecargasDTOS(List<Recarga> recargas) {
        ArrayList<RecargaDTO> transaccionesDTOS = new ArrayList<>();
        //por cada transaccion en la lista crear un DTO
        for (Recarga item : recargas) {
            transaccionesDTOS.add(constuirRecargaDTO(item));
        }
        return transaccionesDTOS;
    }
}
