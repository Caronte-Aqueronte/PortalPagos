/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.tools;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class ManejadorMoneda {

    /**
     * Convierte un número de tipo Double a un formato de String con dos
     * decimales y comas para los miles.
     *
     * @param cantidad Número de tipo Double que se desea formatear.
     * @return Cadena de texto en el formato "1,234.56".
     */
    public String cantidadAFormatoRegional(Double cantidad) {
        if (cantidad == null) {
            return "null";
        }
        DecimalFormatSymbols simbolosPersonalizados = new DecimalFormatSymbols();
        simbolosPersonalizados.setGroupingSeparator(',');
        simbolosPersonalizados.setDecimalSeparator('.');

        DecimalFormat formato = new DecimalFormat("#,###.00", simbolosPersonalizados);
        return formato.format(cantidad);
    }
}
