import {
  Component,
  EventEmitter,
  HostListener,
  Input,
  Output,
} from '@angular/core';

@Component({
  selector: 'app-success-alert',
  templateUrl: './success-alert.component.html',
  styleUrl: './success-alert.component.css',
})
export class SuccessAlertComponent {
  @Input() message: string | null = null; // Mensaje a mostrar en el pop-up
  @Output() close = new EventEmitter<void>(); // Evento para cerrar el pop-up

  onClose() {
    this.close.emit(); // Emite el evento de cierre
  }
}
