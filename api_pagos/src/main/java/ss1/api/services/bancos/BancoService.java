/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ss1.api.services.bancos;

import ss1.api.excepciones.UnauthorizedException;

/**
 *
 * @author Luis Monterroso
 */
public interface BancoService {

    public String login(String email, String pin) throws UnauthorizedException;

    public boolean recargarDesdeBanco(String token, Double monto) throws UnauthorizedException;

    public boolean retirarABanco(String token, Double monto) throws UnauthorizedException;
}
