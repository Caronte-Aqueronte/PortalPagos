import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  errorMessage: string | null = null;

  // Definir el formulario reactivo
  public loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(6),
    ]),
  });

  constructor(private usuarioService: UsuarioService) {}
  login() {
    if (this.loginForm.valid) {
      const email = this.loginForm.value.email;
      const password = this.loginForm.value.password;

      this.usuarioService.login(email, password).subscribe(
        (response: any) => {
          console.log('Login exitoso:', response);
          this.errorMessage = null; // Limpiar el mensaje de error si el login es exitoso
        },
        (error: any) => {
          // Si el código de estado es 500, mostrar mensaje genérico
          if (error.status === 500) {
            this.errorMessage =
              'Error interno del servidor. Inténtalo de nuevo más tarde.';
          }
          // Si hay un mensaje enviado por el servidor, lo mostramos
          else if (error.error) {
            this.errorMessage = error.error;
          }
          // Si no hay mensaje pero el código de error es distinto de 200
          else if (error.status !== 200) {
            this.errorMessage =
              'Solicitud incorrecta. Por favor, revisa los campos.';
          }
          // Otros errores genéricos
          else {
            this.errorMessage = 'Error desconocido. Inténtalo de nuevo.';
          }
        }
      );
    }
  }

  closeAlert() {
    this.errorMessage = null;
  }
}
