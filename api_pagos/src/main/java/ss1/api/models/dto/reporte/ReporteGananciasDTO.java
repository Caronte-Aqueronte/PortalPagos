/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.models.dto.reporte;

import java.util.List;
import ss1.api.models.Retiro;

/**
 *
 * @author Luis Monterroso
 */
public class ReporteGananciasDTO {

    private String totalRetiros;

    private List<Retiro> retiros;

    public ReporteGananciasDTO(String totalRetiros, List<Retiro> retiros) {
        this.totalRetiros = totalRetiros;
        this.retiros = retiros;
    }

    public String getTotalRetiros() {
        return totalRetiros;
    }

    public void setTotalRetiros(String totalRetiros) {
        this.totalRetiros = totalRetiros;
    }

    public List<Retiro> getRetiros() {
        return retiros;
    }

    public void setRetiros(List<Retiro> retiros) {
        this.retiros = retiros;
    }
    
    
    
}
