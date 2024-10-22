import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TransaccionService } from '../../services/transaccion.service';

@Component({
  selector: 'app-inicio-cliente-page',
  templateUrl: './inicio-cliente-page.component.html',
  styleUrl: './inicio-cliente-page.component.css',
})
export class InicioClientePageComponent {
  transferForm: FormGroup; // Formulario reactivo
  showTransferPopup: boolean = false; // Variable para controlar la visibilidad del popup
  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private transaccionService: TransaccionService
  ) {
    // Inicializar el formulario reactivo de transferencia
    this.transferForm = this.fb.group({
      correoReceptor: ['', [Validators.required, Validators.email]], // Validación para email
      concepto: [
        '',
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(300),
        ],
      ], // Validación para email
      cantidad: ['', [Validators.required, Validators.min(1)]], // Validación para monto (mínimo 1)
    });
  }

  // Método para abrir el popup
  openTransferPopup() {
    this.showTransferPopup = true;
  }

  // Método para cerrar el popup
  closeTransferPopup() {
    this.showTransferPopup = false;
  }

  // Método para enviar la transferencia
  submitTransfer() {
    if (this.transferForm.valid) {
      const formData = this.transferForm.value;
      console.log('Transferencia enviada:', formData);

      this.transaccionService.transferir(formData).subscribe(
        (response: any) => {
          this.successMessage = 'Transferencia realizada correctamente.';
          this.errorMessage = null;
          this.transferForm.reset(); // Resetea el formulario tras éxito
        },
        (error: any) => {
          console.log(error);
          if (error.error) {
            this.successMessage = null;
            this.errorMessage = error.error;
            this.transferForm.reset(); // Resetea el formulario tras éxito
          } else {
            this.successMessage = null;
            this.errorMessage = 'Error desconocido';
          }
        }
      );

      this.closeTransferPopup();
    } else {
      // Si el formulario no es válido, puedes mostrar mensajes de error
      console.error('Formulario de transferencia no válido');
    }
  }

  closeErrorAlert() {
    this.errorMessage = null;
  }

  closeSuccessAlert() {
    this.successMessage = null;
  }
}
