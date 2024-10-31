import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UsuarioService } from '../../services/usuario.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-crear-usuario',
  templateUrl: './crear-usuario.component.html',
  styleUrl: './crear-usuario.component.css',
})
export class CrearUsuarioComponent {
  registerForm: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      email: [
        '',
        [
          Validators.required,
          Validators.email,
          Validators.minLength(1),
          Validators.maxLength(250),
        ],
      ],
      nombres: [
        '',
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(250),
        ],
      ],
      apellidos: [
        '',
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(250),
        ],
      ],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(250),
        ],
      ],
      confirmPassword: [
        '',
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(250),
        ],
      ],
    });
  }

  // Validación personalizada para contraseñas iguales
  passwordsMatch(): boolean {
    const password = this.registerForm.get('password')?.value;
    const confirmPassword = this.registerForm.get('confirmPassword')?.value;
    return password === confirmPassword;
  }

  createUser() {
    if (this.registerForm.valid && this.passwordsMatch()) {
      console.log('Formulario válido:', this.registerForm.value);

      this.usuarioService.crearUsuario(this.registerForm.value).subscribe(
        (response: any) => {
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
          // Si hay un mensaje enviado por el servidor, lo mostramos
          if (error.error) {
            this.errorMessage = error.error;
          } else {
            this.errorMessage = 'Error desconocido.';
          }
        }
      );
    } else {
      console.log('Formulario inválido o las contraseñas no coinciden.');
    }
  }

  closeAlert() {
    this.errorMessage = null;
  }
}
