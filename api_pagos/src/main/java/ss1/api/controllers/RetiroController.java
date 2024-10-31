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
import ss1.api.models.dto.RetiroDTO;
import ss1.api.services.RetiroService;

/**
 *
 * @author Luis Monterroso
 */
@RestController
@RequestMapping("/api/retiro")
public class RetiroController {

    @Autowired
    private RetiroService retiroService;

    @GetMapping("/protected/getMis20RetirosMasRecientes")
    public ResponseEntity<?> getMis20RetirosMasRecientes() {
        List<RetiroDTO> data = retiroService.getMis20RetirosMasRecientes();
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/protected/getMisRetirosEnDosFechas")
    public ResponseEntity<?> getMisRetirosEnDosFechas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha1,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha2) {
        List<RetiroDTO> data = retiroService.getMisRetirosEnDosFechas(fecha1, fecha2);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

}
