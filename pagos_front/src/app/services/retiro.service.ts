import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../enviorment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class RetiroService {
  private baseUrl = environment.apiUrl + '/retiro/'; // URL base de la API
  private apiPublic = environment.apiUrl + '/retiro/public'; // URL base de la API
  private apiProtected = environment.apiUrl + '/retiro/protected'; // URL base de la API

  constructor(private http: HttpClient) {}

  getMis20RetirosMasRecientes(): Observable<any> {
    const url = `${this.apiProtected}/getMis20RetirosMasRecientes`; // URL con el ID del usuario
    return this.http.get(url); // Realiza la solicitud GET
  }

  getMisRetirosEnDosFechas(fecha1?: any, fecha2?: any): Observable<any> {
    // Construye la URL base con el ID del usuario
    let url = `${this.apiProtected}/getMisRetirosEnDosFechas`;

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
