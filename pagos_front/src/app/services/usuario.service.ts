import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../enviorment';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {
  private baseUrl = environment.apiUrl + '/usuario/'; // URL base de la API
  private apiPublic = environment.apiUrl + '/usuario/public'; // URL base de la API
  private apiProtected = environment.apiUrl + '/usuario/protected'; // URL base de la API

  constructor(private http: HttpClient) {}

  // Método para login
  login(email: any, password: any): Observable<any> {
    const url = `${this.apiPublic}/login`; // Agrega el endpoint específico
    const body = { email, password };
    return this.http.post(url, body);
  }

  crearUsuario(body: any): Observable<any> {
    const url = `${this.apiPublic}/crearUsuario`; // Agrega el endpoint específico
    return this.http.post(url, body);
  }

  eliminarMiUsuario(body: any): Observable<any> {
    const url = `${this.apiProtected}/eliminarMiUsuario`;
    return this.http.request('DELETE', url, { body }); // Enviar el body en una solicitud DELETE
  }

  // Método para editarPerfil
  editarPerfil(body: any): Observable<any> {
    const url = `${this.apiProtected}/editarPerfil`; // Agrega el endpoint específico
    return this.http.patch(url, body);
  }

  // Método para obtener la información del usuario por su ID
  getMiUsuario(id: number): Observable<any> {
    const url = `${this.apiProtected}/getMiUsuario/${id}`; // URL con el ID del usuario
    return this.http.get(url); // Realiza la solicitud GET
  }

  // Método para obtener la información del usuario por su ID
  cambiarPassword(body: any): Observable<any> {
    const url = `${this.apiProtected}/editarPassword`; // Agrega el endpoint específico
    return this.http.patch(url, body);
  }
}
