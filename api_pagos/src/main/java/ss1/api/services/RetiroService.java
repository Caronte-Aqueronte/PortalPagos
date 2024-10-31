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
import ss1.api.models.Retiro;
import ss1.api.models.Usuario;
import ss1.api.models.dto.RetiroDTO;
import ss1.api.repositories.RetiroRepository;
import ss1.api.tools.ManejadorTiempo;

/**
 *
 * @author Luis Monterroso
 */
@org.springframework.stereotype.Service
public class RetiroService extends Service {

    @Autowired
    private RetiroRepository retiroRepository;
    @Autowired
    @Lazy
    private ManejadorTiempo manejadorTiempo;
    @Autowired
    @Lazy
    private UsuarioService usuarioService;

    @Transactional(rollbackOn = Exception.class)
    public Retiro guardarRetiro(Retiro retiro) {
        return retiroRepository.save(retiro);
    }

    public List<RetiroDTO> getMis20RetirosMasRecientes() throws NotFoundException {
        Usuario usuario = usuarioService.getUsuarioUseJwt();
        return constuirRetirosDTOS(
                retiroRepository.findTop20ByUsuarioIdOrderByCreatedAtDesc(
                        usuario.getId()));

    }

    public List<RetiroDTO> getMisRetirosEnDosFechas(LocalDate fecha1, LocalDate fecha2)
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
        List<Retiro> recargas = retiroRepository.findByUsuarioAndFechaBetween(usuario.getId(),
                fecha1Date, fecha2Date);
        return constuirRetirosDTOS(recargas);
    }

    private RetiroDTO constuirRetiroDTO(Retiro retiro) {
        return new RetiroDTO(
                retiro.getId(),
                retiro.getUsuario().getEmail(),
                manejadorMoneda.cantidadAFormatoRegional(retiro.getMontoRetirado()),
                retiro.getComision().toString(),
                manejadorMoneda.cantidadAFormatoRegional(retiro.getMontoFinalTransferido()),
                retiro.getCuentaDestino(),
                retiro.getEntidadFinanciera(),
                manejadorTiempo.parsearFechaYHoraAFormatoRegional(retiro.getCreatedAt().toLocalDate())
        );
    }

    private List<RetiroDTO> constuirRetirosDTOS(List<Retiro> retiros) {
        ArrayList<RetiroDTO> retirosDTOS = new ArrayList<>();
        //por cada transaccion en la lista crear un DTO
        for (Retiro item : retiros) {
            retirosDTOS.add(constuirRetiroDTO(item));
        }
        return retirosDTOS;
    }
}
