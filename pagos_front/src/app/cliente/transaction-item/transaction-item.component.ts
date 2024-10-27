import { Component, Input } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-transaction-item',
  templateUrl: './transaction-item.component.html',
  styleUrl: './transaction-item.component.css',
})
export class TransactionItemComponent {
  @Input() transaccion!: any;

  constructor(private authService: AuthService) {}

  get isDeposit(): boolean {
    return this.transaccion.receptor === this.authService.getUser().email;
  }

  get transactionLabel(): string {
    return this.isDeposit
      ? `de: ${this.transaccion.emisor}`
      : `para: ${this.transaccion.receptor}`;
  }
}
