/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.reportes.printers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class Printer {

    /**
     * Exporta el reporte en formato PDF.
     *
     * @param reportePath Ruta del archivo .jasper
     * @param parametros Parámetros a inyectar en el reporte.
     * @return Un array de bytes que representa el archivo PDF generado.
     * @throws Exception
     */
    public byte[] exportarPdf(String reportePath, Map parametros) throws Exception {
        JasperPrint jasperPrint = this.calcarReporte(reportePath, parametros);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
        return out.toByteArray();
    }

    /**
     * Método que se encarga de llenar (calcar) el reporte con los datos y
     * parámetros proporcionados.
     *
     * @param reportePath Ruta del archivo .jasper
     * @param parametros Parámetros a inyectar en el reporte.
     * @return El objeto JasperPrint resultante.
     * @throws Exception
     */
    private JasperPrint calcarReporte(String reportePath, Map parametros) throws Exception {
        // Obtener la configuración de la tienda y agregar el logo y el nombre a los parámetros
        parametros.put("imagen_pasarela",
                getClass().getResourceAsStream("/img/logo.png"));

        // Cargar el reporte .jasper desde la ruta especificada
        JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/imprimibles/" + reportePath + ".jasper"));

        // Llenar el reporte con los datos
        return JasperFillManager.fillReport(reporte, parametros, new JREmptyDataSource());
    }
}
