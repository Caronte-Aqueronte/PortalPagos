import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { UsuarioService } from '../../services/usuario.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.css',
})
export class UsuariosComponent implements OnInit {
  empleados: any[] = []; // Arreglo para almacenar la lista de empleados
  createAdminForm: FormGroup;
  showAdminPopup: boolean = false; // Variable para controlar la visibilidad del popup

  constructor(
    private usuarioService: UsuarioService,
    private toastr: ToastrService,
    private fb: FormBuilder
  ) {
    this.createAdminForm = this.fb.group({
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

  ngOnInit(): void {
    this.obtenerEmpleados(); // Cargar empleados al iniciar el componente
  }

  obtenerEmpleados(): void {
    this.usuarioService.getUsuariosExceptoElMio().subscribe({
      next: (response) => {
        console.log(response);
        this.empleados = response;
      },
      error: (err) => {
        console.log(err);
        // Manejo del error, muestra el mensaje adecuado
        var errorMessage =
          err.error || 'Ocurrió un error al obtener los usuarios.';
        this.toastr.error(errorMessage, 'Error');
      },
    });
  }

  eliminarEmpleado(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar este empleado?')) {
      this.usuarioService.eliminarUsuarioParaAdmins(id).subscribe({
        next: () => {
          this.toastr.success('Usuario eliminado exitosamente.', 'Hecho');
          this.obtenerEmpleados();
        },
        error: (err) => {
          // Manejo del error, muestra el mensaje adecuado
          var errorMessage = '';

          if (err.error) {
            errorMessage = err.error;
          } else {
            errorMessage = 'Ocurrió un error al intentar eliminar el usuario.';
          }

          this.toastr.error(errorMessage, 'Error');
        },
      });
    }
  }

  crearAdmin() {
    if (!this.createAdminForm.valid || !this.passwordsMatch()) {
      return;
    }

    if (!confirm('¿Estás seguro de que deseas crear este administrador?')) {
      return;
    }


    this.usuarioService.crearAdmin(this.createAdminForm.value).subscribe({
      next: () => {
        this.toastr.success('Usuario creado exitosamente.', 'Hecho');
        this.obtenerEmpleados();
      },
      error: (err) => {
        // Manejo del error, muestra el mensaje adecuado
        var errorMessage = '';

        if (err.error) {
          errorMessage = err.error;
        } else {
          errorMessage = 'Ocurrió un error al intentar eliminar el usuario.';
        }

        this.toastr.error(errorMessage, 'Error');
      },
    });
  }

  // Validación personalizada para contraseñas iguales
  passwordsMatch(): boolean {
    const password = this.createAdminForm.get('password')?.value;
    const confirmPassword = this.createAdminForm.get('confirmPassword')?.value;
    return password === confirmPassword;
  }

  // Método para abrir el popup
  openAdminPopup() {
    this.showAdminPopup = true;
  }

  // Método para cerrar el popup
  closeAdminPopup() {
    this.showAdminPopup = false;
  }
}
