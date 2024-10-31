import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { UsuarioService } from '../../../services/usuario.service';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrl: './info.component.css',
})
export class InfoComponent implements OnInit {
  passwordForm: FormGroup;
  profileForm: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private authService: AuthService
  ) {
    // Formulario para cambiar contraseña
    this.passwordForm = this.fb.group({
      oldPassword: ['', [Validators.required]],
      newPassword: ['', [Validators.required, Validators.minLength(3)]],
    });

    // Inicialización del formulario reactivo
    this.profileForm = this.fb.group({
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
      email: [{ value: '', disabled: true }],
    });
  }

  ngOnInit(): void {
    this.cargarCliente();
  }

  cargarCliente() {
    // Cargar la información del usuario al inicializar el componente
    const userId = this.authService.getUser().id; // Obtener el ID del usuario desde el AuthService o el LocalStorage

    this.usuarioService.getMiUsuario(userId).subscribe(
      (response: any) => {
        // Llenar el formulario con la información del usuario traída del backend
        this.profileForm.patchValue({
          nombres: response.nombres,
          apellidos: response.apellidos,
          email: response.email, // Aunque el email está deshabilitado, lo llenamos igual
        });
      },
      (error) => {
        this.errorMessage = 'Error al cargar la información del usuario.';
        console.error('Error al cargar la información del usuario:', error);
      }
    );
  }

  editar() {
    if (this.profileForm.valid) {
      // Obtener el ID del usuario desde el localStorage
      const userId = this.authService.getUser().id;

      // Obtener el formulario con los valores actualizados
      const updatedProfile = this.profileForm.getRawValue();

      // Asegurarse de que el ID esté presente en el cuerpo de la solicitud
      const updatedProfileWithId = { ...updatedProfile, id: userId };

      // Llamar al servicio para actualizar el perfil
      this.usuarioService.editarPerfil(updatedProfileWithId).subscribe(
        (response: any) => {
          // Actualizar la información del usuario en el localStorage
          this.authService.setUser(response);
          this.successMessage = 'Perfil actualizado correctamente.';
          this.errorMessage = null;
        },
        (error) => {
          this.successMessage = null;
          this.errorMessage = error.error ? error.error : 'Error desconocido.';
          console.error('Error al actualizar el perfil:', error);
        }
      );
    }
  }

  cambiarPassword() {
    if (this.passwordForm.valid) {
      // Obtener el ID del usuario desde el localStorage
      const userId = this.authService.getUser().id;
      const passwords = this.passwordForm.getRawValue();

      // Asegurarse de que el ID esté presente en el cuerpo de la solicitud
      const body = { ...passwords, id: userId };

      this.usuarioService.cambiarPassword(body).subscribe(
        (response: any) => {
          this.successMessage = 'Contraseña cambiada correctamente.';
          this.errorMessage = null;
        },
        (error) => {
          console.log(error)
          this.successMessage = null;
          this.errorMessage = error.error;
        }
      );
    }
  }

  closeErrorAlert() {
    this.errorMessage = null;
  }

  closeSuccessAlert() {
    this.successMessage = null;
  }
}
