import { Component } from '@angular/core';

@Component({
  selector: 'app-centro-cuentas',
  templateUrl: './centro-cuentas.component.html',
  styleUrl: './centro-cuentas.component.css',
})
export class CentroCuentasComponent {
  // Lista de cuentas bancarias simuladas
  accounts = [
    {
      bankName: 'Banco Nacional',
      accountNumber: '1234567890',
      holderName: 'Juan Pérez',
    },
    {
      bankName: 'Banco Internacional',
      accountNumber: '0987654321',
      holderName: 'Ana Gómez',
    },
  ];

  // Agregar una nueva cuenta (simulación)
  addAccount() {
    const newAccount = {
      bankName: 'Banco Nuevo',
      accountNumber: '1122334455',
      holderName: 'Carlos Rodríguez',
    };
    this.accounts.push(newAccount); // Simulación de agregar cuenta
  }

  // Eliminar cuenta bancaria
  removeAccount(account: any) {
    this.accounts = this.accounts.filter((a) => a !== account); // Remover cuenta
  }
}
