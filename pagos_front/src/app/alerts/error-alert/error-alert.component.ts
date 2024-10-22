import {
  Component,
  EventEmitter,
  HostListener,
  Input,
  Output,
} from '@angular/core';

@Component({
  selector: 'app-error-alert',
  templateUrl: './error-alert.component.html',
  styleUrl: './error-alert.component.css',
})
export class ErrorAlertComponent {
  @Input() message: string | null = null; // Mensaje a mostrar en el pop-up
  @Output() close = new EventEmitter<void>(); // Evento para cerrar el pop-up

  onClose() {
    this.close.emit(); // Emite el evento de cierre
  }
}
