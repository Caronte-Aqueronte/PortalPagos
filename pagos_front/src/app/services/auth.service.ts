import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor() {}

  // Almacena el JWT
  setToken(token: string) {
    localStorage.setItem('jwt', token);
  }

  // Almacena la información del usuario
  setUser(user: any) {
    localStorage.setItem('user', JSON.stringify(user));
  }

  // Obtener el JWT
  getToken(): string | null {
    return localStorage.getItem('jwt');
  }

  // Obtener la información del usuario
  getUser(): any {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  // Obtener el rol del usuario
  getUserRole(): string | null {
    const user = this.getUser();
    return user && user.rol ? user.rol.nombre : null; // Devuelve el nombre del rol
  }

  // Saber si el usuario está autenticado
  isLoggedIn(): boolean {
    return this.getToken() !== null;
  }

  // Eliminar la información del usuario y el JWT al cerrar sesión
  logout() {
    localStorage.removeItem('jwt');
    localStorage.removeItem('user');
  }
}
