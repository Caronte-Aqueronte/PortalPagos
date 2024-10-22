import { Component } from '@angular/core';

@Component({
  selector: 'app-dash-cliente',
  templateUrl: './dash-cliente.component.html',
  styleUrl: './dash-cliente.component.css',
})
export class DashClienteComponent {
  menuAbierto = false;

  toggleMenu() {
    this.menuAbierto = !this.menuAbierto;
  }
}
