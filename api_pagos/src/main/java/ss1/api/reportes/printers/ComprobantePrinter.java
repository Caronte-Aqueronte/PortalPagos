/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.reportes.printers;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.springframework.stereotype.Component;
import ss1.api.models.dto.TransaccionDTO;

/**
 *
 * @author Luis Monterroso
 */
@Component
public class ComprobantePrinter extends Printer {

    private TransaccionDTO transaccion;
    private String nombreTienda;
    private byte[] logoTienda;

    /**
     * Inicializa el proceso de generación del comprobante de pago en PDF.
     * Configura los datos de la transacción, el nombre y logo de la tienda, y
     * construye los parámetros para el reporte.
     *
     * @param transaccion Objeto TransaccionDTO que contiene los detalles de la
     * transacción.
     * @param nombreTienda Nombre de la tienda para el comprobante.
     * @param logoTienda Logo de la tienda en formato de array de bytes.
     * @return Un array de bytes que representa el PDF generado.
     * @throws Exception Si ocurre un error durante la generación del reporte.
     */
    public byte[] init(TransaccionDTO transaccion, String nombreTienda,
            byte[] logoTienda) throws Exception {

        this.transaccion = transaccion;
        this.logoTienda = logoTienda;
        this.nombreTienda = nombreTienda;

        // Si pasaron las comprobaciones, se construyen los parámetros del reporte
        Map<String, Object> parametrosReporte = this.construirClientesFrecuentes();
        // Se abre y exporta el reporte
        return this.exportarPdf("ComprobantePago", parametrosReporte);
    }

    /**
     * Construye los parámetros necesarios para generar el reporte de
     * JasperReports. Prepara los datos en el formato requerido y los agrega a
     * un mapa de parámetros.
     *
     * @return Un mapa de parámetros que contiene los datos necesarios para el
     * reporte.
     * @throws Exception Si ocurre un error al procesar los datos para el
     * reporte.
     */
    private Map<String, Object> construirClientesFrecuentes() throws Exception {
        // Crear el mapa de parámetros para el reporte
        Map<String, Object> parametrosReporte = new HashMap<>();

        // Convertir la lista de clientes a un JRBeanArrayDataSource para JasperReports
        JRBeanArrayDataSource detalle = new JRBeanArrayDataSource(
                List.of(transaccion).toArray()
        );

        //convertir la imagen de la tienda externa a un input stream
        ByteArrayInputStream imagenTienda = new ByteArrayInputStream(logoTienda);

        // Agregar parámetros clave al mapa
        parametrosReporte.put("nombre_tienda", nombreTienda);
        parametrosReporte.put("imagen_tienda", imagenTienda);
        parametrosReporte.put("id", transaccion.getId());
        parametrosReporte.put("total", transaccion.getMonto());
        parametrosReporte.put("detalle", detalle);
        parametrosReporte.put("fecha", transaccion.getFechaTransaccion());

        return parametrosReporte;
    }

}
