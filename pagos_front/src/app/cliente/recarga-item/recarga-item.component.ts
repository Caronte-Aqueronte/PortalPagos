import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-recarga-item',
  templateUrl: './recarga-item.component.html',
  styleUrl: './recarga-item.component.css',
})
export class RecargaItemComponent {
  @Input() recarga!: any;
}
