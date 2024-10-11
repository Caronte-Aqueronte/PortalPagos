import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../enviorment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {


  private baseUrl = environment.apiUrl + '/usuario/'; // URL base de la API
  private apiPublic = environment.apiUrl + '/usuario/public'; // URL base de la API
  private apiPrivate = environment.apiUrl + '/usuario/private'; // URL base de la API

  constructor(private http: HttpClient) {}

  // Método para login
  login(email: any, password: any): Observable<any> {
    const url = `${this.apiPublic}/login`; // Agrega el endpoint específico
    const body = { email, password };
    return this.http.post(url, body);
  }
}
