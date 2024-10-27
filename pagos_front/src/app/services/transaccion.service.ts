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

  getMisUltimas20Transacciones(id: any): Observable<any> {
    const url = `${this.apiProtected}/getMisUltimas20Transacciones/${id}`; // URL con el ID del usuario
    return this.http.get(url); // Realiza la solicitud GET
  }

  getMisPagosEnDosFechas(id: any, fecha1?: any, fecha2?: any): Observable<any> {
    // Construye la URL base con el ID del usuario
    let url = `${this.apiProtected}/getMisPagosEnDosFechas/${id}`;

    // Agrega las fechas como parámetros de consulta si están presentes
    const params: string[] = [];
    if (fecha1) params.push(`fecha1=${fecha1}`);
    if (fecha2) params.push(`fecha2=${fecha2}`);

    // Si hay parámetros de fecha, los añade a la URL
    if (params.length > 0) {
      url += `?${params.join('&')}`;
    }

    // Realiza la solicitud GET
    return this.http.get(url);
  }

  getMisIngresosEnDosFechas(id: any, fecha1?: any, fecha2?: any): Observable<any> {
    // Construye la URL base con el ID del usuario
    let url = `${this.apiProtected}/getMisIngresosEnDosFechas/${id}`;

    // Agrega las fechas como parámetros de consulta si están presentes
    const params: string[] = [];
    if (fecha1) params.push(`fecha1=${fecha1}`);
    if (fecha2) params.push(`fecha2=${fecha2}`);

    // Si hay parámetros de fecha, los añade a la URL
    if (params.length > 0) {
      url += `?${params.join('&')}`;
    }

    // Realiza la solicitud GET
    return this.http.get(url);
  }
}
