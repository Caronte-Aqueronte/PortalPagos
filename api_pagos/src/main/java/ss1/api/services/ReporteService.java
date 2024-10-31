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
import ss1.api.models.TransaccionFallida;
import ss1.api.models.Usuario;
import ss1.api.repositories.TransaccionFallidaRepository;
import ss1.api.repositories.UsuarioRepository;

/**
 *
 * @author Luis Monterroso
 */
@org.springframework.stereotype.Service
public class ReporteService {

    @Autowired
    @Lazy
    private UsuarioRepository usuarioRepository;
    @Autowired
    @Lazy
    private TransaccionFallidaRepository transaccionFallidaRepository;

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

    // Método auxiliar para verificar si una cadena es nula o está vacía
    private boolean isNullOrEmpty(String value) {
        return value == null || value.isBlank();
    }
}
