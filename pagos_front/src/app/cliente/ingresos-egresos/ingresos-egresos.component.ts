import { Component, OnInit } from '@angular/core';
import { TransaccionService } from '../../services/transaccion.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-ingresos-egresos',
  templateUrl: './ingresos-egresos.component.html',
  styleUrl: './ingresos-egresos.component.css',
})
export class IngresosEgresosComponent implements OnInit {
  ingresos: any;
  egresos: any;
  retiros: any;
  fechaInicio: string | null = null; // variable enlazada a la fecha de inicio
  fechaFin: string | null = null; // variable enlazada a la fecha de fin

  constructor(
    private transaccionService: TransaccionService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.filtrarPorFecha();
  }

  getMisPagosEnDosFechas() {
    //obtneer id
    var id = this.authService.getUser().id;

    this.transaccionService
      .getMisPagosEnDosFechas(id, this.fechaInicio, this.fechaFin)
      .subscribe(
        (response: any) => {
          this.egresos = response;
        },
        (error: any) => {
          console.log(error.error);
          alert(error.error);
        }
      );
  }

  getMisIngresosEnDosFechas() {
    //obtneer id
    var id = this.authService.getUser().id;

    this.transaccionService
      .getMisIngresosEnDosFechas(id, this.fechaInicio, this.fechaFin)
      .subscribe(
        (response: any) => {
          this.ingresos = response;
        },
        (error: any) => {
          console.log(error.error);
          alert(error.error);
        }
      );
  }


  filtrarPorFecha() {
    this.getMisPagosEnDosFechas();
    this.getMisIngresosEnDosFechas();
  }
}
