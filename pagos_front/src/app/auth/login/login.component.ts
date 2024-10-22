import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UsuarioService } from '../../services/usuario.service';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  errorMessage: string | null = null;

  // Definir el formulario reactivo
  public loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
    ]),
  });

  ngOnInit(): void {
    this.authService.logout();
  }

  constructor(
    private usuarioService: UsuarioService,
    private router: Router,
    private authService: AuthService
  ) {}

  login() {
    if (this.loginForm.valid) {
      const email = this.loginForm.value.email;
      const password = this.loginForm.value.password;

      this.usuarioService.login(email, password).subscribe(
        (response: any) => {
          console.log('Login exitoso:', response);
          this.errorMessage = null; // Limpiar el mensaje de error si el login es exitoso

          // Guardar el JWT y la información del usuario
          this.authService.setToken(response.jwt);
          this.authService.setUser(response.usuario);

          // Obtener el rol del usuario
          const userRole = this.authService.getUserRole();

          // Redirigir al dashboard correspondiente según el rol del usuario
          if (userRole === 'ADMIN') {
            // this.router.navigate(['/admin-dashboard']);
          } else if (userRole === 'CLIENTE') {
            this.router.navigate(['/dashboard-cliente/inicio']);
          } else {
            this.errorMessage = 'Rol desconocido.';
          }
        },
        (error: any) => {
          console.log(error);
          // Si el código de estado es 500, mostrar mensaje genérico
          if (error.status === 500) {
            this.errorMessage =
              'Error interno del servidor. Inténtalo de nuevo más tarde.';
          }
          // Si hay un mensaje enviado por el servidor, lo mostramos
          else if (error.error) {
            this.errorMessage = error.error;
          }
        }
      );
    }
  }
  closeAlert() {
    this.errorMessage = null;
  }
}
