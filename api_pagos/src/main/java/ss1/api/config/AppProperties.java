package ss1.api.config;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author Luis Monterroso
 */
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String hostFrontDev;

    public String getHostFrontDev() {
        return hostFrontDev;
    }

    public void setHostFrontDev(String hostFrontDev) {
        this.hostFrontDev = hostFrontDev;
    }
}
