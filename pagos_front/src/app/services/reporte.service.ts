import { Injectable } from '@angular/core';
import { environment } from '../enviorment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ReporteService {
  private apiAdmin = environment.apiUrl + '/reporte/admin'; // URL base de la API

  constructor(private http: HttpClient) {}

  reporteUsuarios(rol?: any, estadoUsuario?: any): Observable<any> {
    // Construye la URL base con el ID del usuario
    let url = `${this.apiAdmin}/reporteUsuarios`;

    // Agrega las fechas como parámetros de consulta si están presentes
    const params: string[] = [];
    if (rol) params.push(`rol=${rol}`);
    if (estadoUsuario) params.push(`estadoUsuario=${estadoUsuario}`);

    // Si hay parámetros de fecha, los añade a la URL
    if (params.length > 0) {
      url += `?${params.join('&')}`;
    }

    // Realiza la solicitud GET
    return this.http.get(url);
  }

  reporteTransaccionesFallidas(fecha1?: any, fecha2?: any): Observable<any> {
    // Construye la URL base con el ID del usuario
    let url = `${this.apiAdmin}/reporteTransaccionesFallidas`;

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

  reporteIngresosEgreso(fecha1?: any, fecha2?: any): Observable<any> {
    // Construye la URL base con el ID del usuario
    let url = `${this.apiAdmin}/reporteHistoricoMovimientos`;

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


  reporteGanancias(fecha1?: any, fecha2?: any): Observable<any> {
    // Construye la URL base con el ID del usuario
    let url = `${this.apiAdmin}/reporteGanancias`;

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


  reporteHistoricoMovimientosPorUsuario(idUsuario:any,fecha1?: any, fecha2?: any): Observable<any> {
    // Construye la URL base con el ID del usuario
    let url = `${this.apiAdmin}/reporteHistoricoMovimientosPorUsuario/${idUsuario}`;

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
