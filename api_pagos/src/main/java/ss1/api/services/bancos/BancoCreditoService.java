/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ss1.api.services.bancos;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ss1.api.excepciones.UnauthorizedException;

/**
 *
 * @author Luis Monterroso
 */
@Service
public class BancoCreditoService implements BancoService {

    @Value("${externo.servicio.urlBancoCredito}")
    private String urlBancoCredito;

    @Override
    public String login(String email, String pin) throws UnauthorizedException {
        RestTemplate restTemplate = new RestTemplate();

        String url = urlBancoCredito + "/tarjeta-credito/v1/auth/login";

        Map<String, String> body = new HashMap<>();
        body.put("correo_electronico", email);
        body.put("pin", pin);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (responseBody != null && responseBody.containsKey("token")) {
            return responseBody.get("token").toString();
        } else {
            throw new UnauthorizedException("No se pudo obtener el token de autenticación.");
        }
    }

    @Override
    public boolean recargarDesdeBanco(String token, Double monto) throws UnauthorizedException {
        String url = urlBancoCredito + "/tarjeta-credito/v1/tarjeta/generar-debito";
        return realizarTransaccion(token, monto, url, "monto");
    }

    @Override
    public boolean retirarABanco(String token, Double monto) throws UnauthorizedException {
        String url = urlBancoCredito + "/tarjeta-credito/v1/tarjeta/generar-credito";
        return realizarTransaccion(token, monto, url, "monto");
    }

    private boolean realizarTransaccion(String token, Double monto, String url, String saldoKey) throws UnauthorizedException {
        Map<String, Object> body = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();

        body.put(saldoKey, monto);
        body.put("nombre_pasarela", "PayFlow");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (responseBody != null && Boolean.TRUE.equals(responseBody.get("ok"))) {
            return true;
        } else {
            if (responseBody != null && responseBody.get("mensaje") != null) {

                throw new UnauthorizedException("Mensaje desde el banco: " + (String) responseBody.get("mensaje")
                );
            }
            throw new UnauthorizedException("Transacción fallida en el banco de credito.");
        }
    }
}
