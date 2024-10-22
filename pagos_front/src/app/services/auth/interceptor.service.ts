import { Injectable } from '@angular/core';
import { AuthService } from '../auth.service';
import { HttpEvent, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InterceptorService {
  constructor(private authService: AuthService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // Obtener el token JWT del servicio de autenticación
    const token = this.authService.getToken();

    // Si el token existe, clona la solicitud y añade el token en el encabezado Authorization
    if (token) {
      const clonedRequest = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`),
      });

      // Pasar la solicitud clonada con el token al siguiente manejador
      return next.handle(clonedRequest);
    }

    // Si no hay token, pasa la solicitud original
    return next.handle(req);
  }
}
