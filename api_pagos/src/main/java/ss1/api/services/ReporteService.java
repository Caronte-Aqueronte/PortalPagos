/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ss1.api.models.Recarga;
import ss1.api.models.Retiro;
import ss1.api.models.Transaccion;
import ss1.api.models.TransaccionFallida;
import ss1.api.models.Usuario;
import ss1.api.models.dto.reporte.MovimientosReporteDTO;
import ss1.api.models.dto.reporte.ReporteGananciasDTO;
import ss1.api.repositories.RecargaRepository;
import ss1.api.repositories.RetiroRepository;
import ss1.api.repositories.TransaccionFallidaRepository;
import ss1.api.repositories.TransaccionRepository;
import ss1.api.repositories.UsuarioRepository;

/**
 *
 * @author Luis Monterroso
 */
@org.springframework.stereotype.Service
public class ReporteService extends Service {

    @Autowired
    @Lazy
    private UsuarioRepository usuarioRepository;
    @Autowired
    @Lazy
    private TransaccionFallidaRepository transaccionFallidaRepository;

    @Autowired
    @Lazy
    private RetiroRepository retiroRepository;

    @Autowired
    @Lazy
    private TransaccionRepository transaccionRepository;

    @Autowired
    @Lazy
    private RecargaRepository recargaRepository;

    public List<Usuario> getUsuariosSegunSuEstado(String rol, String usuarioEstado) {
        // Caso 1: rol y usuarioEstado son nulos o vacíos -> mostrar todos los usuarios
        if (isNullOrEmpty(rol) && isNullOrEmpty(usuarioEstado)) {
            return (List<Usuario>) usuarioRepository.findAll();
        }

        // Caso 2: rol es nulo o vacío y usuarioEstado NO es nulo o vacío
        // -> mostrar usuarios eliminados según usuarioEstado
        if (isNullOrEmpty(rol)) {
            if ("Eliminados".equalsIgnoreCase(usuarioEstado)) {
                return usuarioRepository.findAllByDeletedAtIsNotNull();
            } else {
                return usuarioRepository.findAllByDeletedAtIsNull();
            }
        }

        // Caso 3: rol NO es nulo o vacío y usuarioEstado es nulo o vacío
        // -> mostrar todos los usuarios, eliminados o no, según el rol
        if (isNullOrEmpty(usuarioEstado)) {
            return usuarioRepository.findAllByRol_Nombre(rol);
        }

        // Caso 4: rol y usuarioEstado NO son nulos o vacíos
        // -> mostrar usuarios según rol y estado (eliminados o no)
        if ("Eliminados".equalsIgnoreCase(usuarioEstado)) {
            return usuarioRepository.findAllByRol_NombreAndDeletedAtIsNotNull(rol);
        } else {
            return usuarioRepository.findAllByRol_NombreAndDeletedAtIsNull(rol);
        }
    }

    public List<TransaccionFallida> reporteTransaccionFallida(
            LocalDate fecha1, LocalDate fecha2) {
        Date fecha1Date = null;
        Date fecha2Date = null;

        if (fecha1 != null) {
            fecha1Date = Date.valueOf(fecha1);
        }

        if (fecha2 != null) {
            fecha2Date = Date.valueOf(fecha2);
        }
        return transaccionFallidaRepository.findByFechaBetween(fecha1Date, fecha2Date);
    }

    public ReporteGananciasDTO reporteGanancias(LocalDate fecha1, LocalDate fecha2) {
        Date fecha1Date = null;
        Date fecha2Date = null;

        if (fecha1 != null) {
            fecha1Date = Date.valueOf(fecha1);
        }

        if (fecha2 != null) {
            fecha2Date = Date.valueOf(fecha2);
        }

        List<Retiro> retiros = retiroRepository.findRetirosByDateRange(fecha1Date, fecha2Date);

        Double total = getTotalGananciaRetiros(retiros);

        return new ReporteGananciasDTO(manejadorMoneda.cantidadAFormatoRegional(total),
                retiros);
    }

    public MovimientosReporteDTO reporteHistoricoDeMovimientos(LocalDate fecha1, LocalDate fecha2) {
        Date fecha1Date = null;
        Date fecha2Date = null;

        if (fecha1 != null) {
            fecha1Date = Date.valueOf(fecha1);
        }

        if (fecha2 != null) {
            fecha2Date = Date.valueOf(fecha2);
        }

        //obtneemos los retiros
        List<Retiro> retiros = retiroRepository.findRetirosByDateRange(fecha1Date, fecha2Date);

        List<Transaccion> transacciones = transaccionRepository.findByFechaBetween(fecha1Date, fecha2Date);

        List<Recarga> recargas = recargaRepository.findByFechaBetween(fecha1Date, fecha2Date);

        //mandamos a traer los totales de los retiros, transacciones y recargas
        Double totalRetiros = getTotalRetiros(retiros);
        Double totalTransacciones = getTotalTransacciones(transacciones);
        Double totalRecargas = getTotalRecargas(recargas);

        //retornar el reporte
        return new MovimientosReporteDTO(
                manejadorMoneda.cantidadAFormatoRegional(totalRetiros),
                manejadorMoneda.cantidadAFormatoRegional(totalTransacciones),
                manejadorMoneda.cantidadAFormatoRegional(totalRecargas),
                retiros,
                transacciones,
                recargas
        );
    }

    public MovimientosReporteDTO reporteHistoricoDeMovimientosPorUsuario(
            Long usuarioId, LocalDate fecha1, LocalDate fecha2) {
        Date fecha1Date = null;
        Date fecha2Date = null;

        if (fecha1 != null) {
            fecha1Date = Date.valueOf(fecha1);
        }

        if (fecha2 != null) {
            fecha2Date = Date.valueOf(fecha2);
        }

        //obtneemos los retiros
        List<Retiro> retiros = retiroRepository.findByUsuarioAndFechaBetween(
                usuarioId, fecha1Date, fecha2Date);

        List<Transaccion> emisor = transaccionRepository.findByUsuarioEmisorAndFechaBetween(
                usuarioId,
                fecha1Date, fecha2Date);

        List<Transaccion> receptor = transaccionRepository.findByUsuarioReceptorAndFechaBetween(
                usuarioId,
                fecha1Date, fecha2Date);

        List<Recarga> recargas = recargaRepository.findByUsuarioAndFechaBetween(
                usuarioId, fecha1Date, fecha2Date);

        emisor.addAll(receptor);

        //mandamos a traer los totales de los retiros, transacciones y recargas
        Double totalRetiros = getTotalRetiros(retiros);
        Double totalTransacciones = getTotalTransacciones(emisor);
        Double totalRecargas = getTotalRecargas(recargas);

        //retornar el reporte
        return new MovimientosReporteDTO(
                manejadorMoneda.cantidadAFormatoRegional(totalRetiros),
                manejadorMoneda.cantidadAFormatoRegional(totalTransacciones),
                manejadorMoneda.cantidadAFormatoRegional(totalRecargas),
                retiros,
                emisor,
                recargas
        );
    }

    private Double getTotalRetiros(List<Retiro> retiros) {
        Double total = 0.0;
        for (Retiro item : retiros) {
            total += item.getMontoRetirado();
        }
        return total;
    }

    private Double getTotalGananciaRetiros(List<Retiro> retiros) {
        Double total = 0.0;
        for (Retiro item : retiros) {
            total += item.getComision();
        }
        return total;
    }

    private Double getTotalTransacciones(List<Transaccion> transacciones) {
        Double total = 0.0;
        for (Transaccion item : transacciones) {
            total += item.getMonto();
        }
        return total;
    }

    private Double getTotalRecargas(List<Recarga> recargas) {
        Double total = 0.0;
        for (Recarga item : recargas) {
            total += item.getMontoRecargado();
        }
        return total;
    }

    // Método auxiliar para verificar si una cadena es nula o está vacía
    private boolean isNullOrEmpty(String value) {
        return value == null || value.isBlank();
    }
}
