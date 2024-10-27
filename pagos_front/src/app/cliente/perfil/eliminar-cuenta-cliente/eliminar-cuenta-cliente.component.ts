import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UsuarioService } from '../../../services/usuario.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-eliminar-cuenta-cliente',
  templateUrl: './eliminar-cuenta-cliente.component.html',
  styleUrl: './eliminar-cuenta-cliente.component.css',
})
export class EliminarCuentaClienteComponent {
  deleteAccountForm: FormGroup;
  errorMessage: string = '';
  successMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService, // Servicio que maneja las cuentas
    private router: Router,
    private toastr: ToastrService // Servicio de notificaciones
  ) {
    // Inicializa el formulario con la validación de contraseña requerida
    this.deleteAccountForm = this.fb.group({
      password: ['', [Validators.required]],
    });
  }

  eliminarCuenta() {
    if (this.deleteAccountForm.invalid) {
      return;
    }

    const password = this.deleteAccountForm.get('password')?.value;

    const body = { password: password };

    // Llama al servicio de eliminación de cuenta, pasando la contraseña
    this.usuarioService.eliminarMiUsuario(body).subscribe({
      next: () => {
        this.successMessage = 'Cuenta eliminada exitosamente.';
        this.toastr.success(this.successMessage, 'Éxito');
        // Redirige al usuario a la página de inicio después de la eliminación
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.log(err);
        // Manejo del error, muestra el mensaje adecuado
        this.errorMessage =
          err.error || 'Ocurrió un error al intentar eliminar la cuenta.';
        this.toastr.error(this.errorMessage, 'Error');
      },
    });
  }
}
