/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.controllers;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ss1.api.models.TransaccionFallida;
import ss1.api.models.Usuario;
import ss1.api.services.ReporteService;

/**
 *
 * @author Luis Monterroso
 */
@RestController
@RequestMapping("/api/reporte")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/admin/reporteUsuarios")
    public ResponseEntity<?> getUsuariosSegunSuEstado(
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) String estadoUsuario) {
        List<Usuario> data = reporteService.getUsuariosSegunSuEstado(rol, estadoUsuario);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/admin/reporteTransaccionesFallidas")
    public ResponseEntity<?> reporteTransaccionesFallidas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha1,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha2) {
        List<TransaccionFallida> data = reporteService.reporteTransaccionFallida(fecha1, fecha2);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
