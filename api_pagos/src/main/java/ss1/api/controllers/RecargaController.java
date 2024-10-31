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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ss1.api.models.dto.RecargaDTO;
import ss1.api.services.RecargaService;

/**
 *
 * @author Luis Monterroso
 */
@RestController
@RequestMapping("/api/recarga")
public class RecargaController {

    @Autowired
    private RecargaService recargaService;

    @GetMapping("/protected/getMisUltimos20Recargas")
    public ResponseEntity<?> getMisUltimos20Recargas() {
        List<RecargaDTO> data = recargaService.getMisUltimos20Recargas();
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/protected/getMisRecargasEnDosFechas")
    public ResponseEntity<?> getMisRecargasEnDosFechas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha1,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha2) {
        List<RecargaDTO> data = recargaService.getMisRecargasEnDosFechas(fecha1, fecha2);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
