import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../enviorment';
@Injectable({
  providedIn: 'root'
})
export class SaldoService {

  private baseUrl = environment.apiUrl + '/saldo/'; // URL base de la API
  private apiPublic = environment.apiUrl + '/saldo/public'; // URL base de la API
  private apiProtected = environment.apiUrl + '/saldo/protected'; // URL base de la API


  constructor(private http: HttpClient) { }

    // Método para obtener la información del usuario por su ID
    getMiSaldo(id: number): Observable<any> {
      const url = `${this.apiProtected}/getMiSaldo/${id}`; // URL con el ID del usuario
      return this.http.get(url); // Realiza la solicitud GET
    }
  
}
