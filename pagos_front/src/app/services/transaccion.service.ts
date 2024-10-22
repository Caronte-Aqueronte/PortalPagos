import { Injectable } from '@angular/core';
import { environment } from '../enviorment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TransaccionService {
  private baseUrl = environment.apiUrl + '/transaccion/'; // URL base de la API
  private apiPublic = environment.apiUrl + '/transaccion/public'; // URL base de la API
  private apiProtected = environment.apiUrl + '/transaccion/protected'; // URL base de la API

  constructor(private http: HttpClient) {}

  transferir(body: any): Observable<any> {
    const url = `${this.apiProtected}/pagar`; // Agrega el endpoint específico
    return this.http.post(url, body);
  }
}
