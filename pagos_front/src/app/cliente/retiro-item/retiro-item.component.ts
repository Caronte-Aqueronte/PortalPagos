import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-retiro-item',
  templateUrl: './retiro-item.component.html',
  styleUrl: './retiro-item.component.css'
})
export class RetiroItemComponent {
  @Input() retiro!: any;
}
