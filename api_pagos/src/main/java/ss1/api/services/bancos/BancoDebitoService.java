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
public class BancoDebitoService implements BancoService {

    @Value("${externo.servicio.urlBancoDebito}")
    private String urlBancoDebito;

    @Override
    public String login(String email, String pin) throws UnauthorizedException {
        RestTemplate restTemplate = new RestTemplate();

        String url = urlBancoDebito + "api/v1.0/auth/link-account";

        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("pin", pin);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (responseBody != null && responseBody.containsKey("link_token")) {
            return responseBody.get("link_token").toString();
        } else {
            throw new UnauthorizedException("No se pudo obtener el token de autenticación.");
        }
    }

    @Override
    public boolean recargarDesdeBanco(String token, Double monto) throws UnauthorizedException {
        String url = urlBancoDebito + "api/v1.0/transaction/debit-link";
        return realizarTransaccion(token, monto, url, "amount");
    }

    @Override
    public boolean retirarABanco(String token, Double monto) throws UnauthorizedException {
        String url = urlBancoDebito + "api/v1.0/transaction/credit-link";
        return realizarTransaccion(token, monto, url, "amount");
    }

    private boolean realizarTransaccion(String token, Double monto, String url, String saldoKey) throws UnauthorizedException {
        Map<String, Object> body = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();

        body.put(saldoKey, monto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (responseBody != null && Boolean.TRUE.equals(responseBody.get("success"))) {
            return true;
        } else {

            if (responseBody != null && responseBody.get("message") != null) {

                throw new UnauthorizedException(
                        "Mensaje desde el banco: " + (String) responseBody.get("message"));
            }

            throw new UnauthorizedException("Transacción fallida en el banco de debito.");
        }
    }
}
