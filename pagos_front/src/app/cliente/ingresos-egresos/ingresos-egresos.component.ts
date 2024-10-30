import { Component, HostListener, OnInit } from '@angular/core';
import { TransaccionService } from '../../services/transaccion.service';
import { AuthService } from '../../services/auth.service';
import { RecargaService } from '../../services/recarga.service';
import { RetiroService } from '../../services/retiro.service';
import { Color, ScaleType } from '@swimlane/ngx-charts';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-ingresos-egresos',
  templateUrl: './ingresos-egresos.component.html',
  styleUrl: './ingresos-egresos.component.css',
  template: `
    <ngx-charts-advanced-pie-chart
      [results]="chartData"
      [view]="view"
      [scheme]="colorScheme"
      [gradient]="gradient"
      [animations]="animations"
    >
    </ngx-charts-advanced-pie-chart>
  `,
})
export class IngresosEgresosComponent implements OnInit {
  ingresos: any;
  egresos: any;
  retiros: any;
  recargas: any;

  fechaInicio: string | null = null;
  fechaFin: string | null = null;

  // Configuración del gráfico
  chartData: any[] = [];
  view: [number, number] = [300, 300];
  gradient = true;
  animations = true;
  colorScheme: Color = {
    name: 'customScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#4CAF50', '#FF5722', '#FFC107', '#2196F3'],
  };

  constructor(
    private transaccionService: TransaccionService,
    private authService: AuthService,
    private recargaService: RecargaService,
    private retiroService: RetiroService
  ) {}

  ngOnInit(): void {
    this.adjustChartSize();
    this.filtrarPorFecha();
  }

  @HostListener('window:resize', ['$event'])
  onResize() {
    this.adjustChartSize();
  }

  private adjustChartSize(): void {
    const width = window.innerWidth;
    const chartWidth = Math.min(width * 0.9, 500); // Ajusta el 90% del ancho de la ventana con un máximo de 500
    this.view = [chartWidth, chartWidth]; // Mantiene un gráfico cuadrado
  }

  filtrarPorFecha() {
    const id = this.authService.getUser().id;
    forkJoin({
      egresos: this.transaccionService.getMisPagosEnDosFechas(
        id,
        this.fechaInicio,
        this.fechaFin
      ),
      ingresos: this.transaccionService.getMisIngresosEnDosFechas(
        id,
        this.fechaInicio,
        this.fechaFin
      ),
      retiros: this.retiroService.getMisRetirosEnDosFechas(
        this.fechaInicio,
        this.fechaFin
      ),
      recargas: this.recargaService.getMisRecargasEnDosFechas(
        this.fechaInicio,
        this.fechaFin
      ),
    }).subscribe({
      next: (response) => {
        this.egresos = response.egresos;
        this.ingresos = response.ingresos;
        this.retiros = response.retiros;
        this.recargas = response.recargas;
        this.generateChartData();
      },
      error: (error) => {
        console.error(error);
        alert('Error al cargar los datos');
      },
    });
  }

  generateChartData(): void {
    this.chartData = [
      { name: 'Ingresos', value: this.ingresos.length },
      { name: 'Egresos', value: this.egresos.length },
      { name: 'Retiros', value: this.retiros.length },
      { name: 'Recargas', value: this.recargas.length },
    ];
  }
}
