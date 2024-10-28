import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TransaccionService } from '../../services/transaccion.service';
import { AuthService } from '../../services/auth.service';
import { SaldoService } from '../../services/saldo.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-inicio-cliente-page',
  templateUrl: './inicio-cliente-page.component.html',
  styleUrl: './inicio-cliente-page.component.css',
})
export class InicioClientePageComponent implements OnInit {
  externoForm: FormGroup;
  showDialog = false;
  operationType = 'Recargar';

  saldoUsuario: any;
  transferForm: FormGroup; // Formulario reactivo
  showTransferPopup: boolean = false; // Variable para controlar la visibilidad del popup
  errorMessage: string | null = null;
  successMessage: string | null = null;
  transacciones: any;

  constructor(
    private fb: FormBuilder,
    private transaccionService: TransaccionService,
    private authService: AuthService,
    private saldoService: SaldoService,
    private toastr: ToastrService
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

    this.externoForm = this.fb.group({
      operationType: ['Recargar', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      pin: ['', [Validators.required, Validators.minLength(3)]],
      monto: ['', [Validators.required, Validators.min(1)]],
      banco: ['credito', Validators.required],
    });

    // Escucha cambios en el tipo de operación
    this.externoForm.get('operationType')?.valueChanges.subscribe((value) => {
      this.operationType = value;
    });
  }
  ngOnInit(): void {
    this.getSaldoDelUsuario();
    this.getTransaccionesDelUsuario();
  }

  getTransaccionesDelUsuario() {
    //obtneer id
    var id = this.authService.getUser().id;

    this.transaccionService.getMisUltimas20Transacciones(id).subscribe(
      (response: any) => {
        console.log(response);
        this.transacciones = response;
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
  }
  getSaldoDelUsuario() {
    //obtneer id
    var id = this.authService.getUser().id;

    this.saldoService.getMiSaldo(id).subscribe(
      (response: any) => {
        this.saldoUsuario = response.saldoDisponible;
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

          this.getSaldoDelUsuario();
          this.getTransaccionesDelUsuario();
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

  // Envía el formulario
  submitTransaction() {
    if (this.externoForm.valid) {
      const formData = this.externoForm.value;

      if (formData.operationType == 'Recargar') {
        this.recargar(formData);
      } else if (formData.operationType == 'Retirar') {
        this.retirar(formData);
      } else {
        this.toastr.error('Operacion invalida.', 'Error');
      }

      this.closeDialog();
    } else {
      this.toastr.error(
        'Por favor completa el formulario correctamente.',
        'Error'
      );
    }
  }

  recargar(body: any) {
    // Llama al servicio de eliminación de cuenta, pasando la contraseña
    this.saldoService.recargar(body).subscribe({
      next: () => {
        this.toastr.success(
          `Recarga realizada exitosamente por Q${body.monto}`,
          'Éxito'
        );
      },
      error: (err) => {
        // Manejo del error, muestra el mensaje adecuado
        var mensajeError =
          err.error || 'Ocurrió un error al intentar recargar.';
        this.toastr.error(mensajeError, 'Error');
      },
    });
  }

  retirar(body: any) {
    // Llama al servicio de eliminación de cuenta, pasando la contraseña
    this.saldoService.retirar(body).subscribe({
      next: () => {
        this.toastr.success(
          `Retiro realizado exitosamente por Q${body.monto}`,
          'Éxito'
        );
      },
      error: (err) => {
        // Manejo del error, muestra el mensaje adecuado
        var mensajeError = err.error || 'Ocurrió un error al intentar retirar.';
        this.toastr.error(mensajeError, 'Error');
      },
    });
  }

  closeErrorAlert() {
    this.errorMessage = null;
  }

  closeSuccessAlert() {
    this.successMessage = null;
  }

  // Función para abrir el diálogo con el tipo de operación específico
  openDialog(operation: 'Recargar' | 'Retirar') {
    this.operationType = operation;
    this.externoForm.get('operationType')?.setValue(operation);
    this.showDialog = true;
  }

  // Cierra el diálogo
  closeDialog() {
    this.showDialog = false;
  }
}
