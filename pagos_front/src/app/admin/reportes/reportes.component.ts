import { Component, OnInit } from '@angular/core';
import { ReporteService } from '../../services/reporte.service';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-reportes',
  templateUrl: './reportes.component.html',
  styleUrl: './reportes.component.css',
})
export class ReportesComponent implements OnInit {
  // Variables para filtros y tipos de reportes
  reportTypes = [
    'Usuarios inscritos',
    'Transacciones Fallidas',
    'Historico de Movimientos Por Usuario',
    'Ingresos/Egresos',
    'Ganancias',
  ];
  selectedReportType: string = this.reportTypes[0];

  showDateFilter = false;
  showRoleFilter = false;
  showStatusFilter = false;
  showUserFilter = false;

  // Variables para filtros
  fechaInicio: string | null = null;
  fechaFin: string | null = null;
  selectedRole: string | null = null;
  selectedStatus: string | null = null;
  selectedUser: string | null = null;

  // Datos para los filtros dinámicos
  roles = ['ADMIN', 'CLIENTE', ''];
  statuses = ['Activos', 'Eliminados', ''];

  // Datos de la tabla y columnas mostradas dinámicamente
  displayedColumns: string[] = [];
  reportData: any[] = [];

  //para tabla de retiros
  displayedColumnsRetiros: string[] = [];
  reportDataRetiros: any[] = [];
  columnMapRetiros: { [key: string]: string } = {};

  //para tabla de recargas
  displayedColumnsRecargas: string[] = [];
  reportDataRecarga: any[] = [];
  columnMapRecargas: { [key: string]: string } = {};

  // Mapeo de nombres de columnas a propiedades de objetos
  columnMap: { [key: string]: string } = {};

  totalTransacciones: any;
  totalRecargas: any;
  totalRetiros: any;

  usuarios: any;

  constructor(
    private reporteService: ReporteService,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    this.updateFiltersAndColumns();
    this.getUsuariosExceptoElMio();
  }

  onReportTypeChange() {
    this.updateFiltersAndColumns();
  }

  generarReporte() {
    switch (this.selectedReportType) {
      case 'Usuarios inscritos':
        this.getReporteUsuarios();
        break;

      case 'Transacciones Fallidas':
        this.reporteTransaccionesFallidas();
        break;

      case 'Historico de Movimientos Por Usuario':
        this.reporteHistoricoMovimientosPorUsuario();
        break;

      case 'Ingresos/Egresos':
        this.reporteIngresosEgreso();
        break;

      case 'Ganancias':
        this.reporteGanancias();
        break;

      default:
        this.displayedColumns = [];
        this.reportData = [];
    }
  }

  // Actualizar filtros y columnas según el tipo de reporte seleccionado
  updateFiltersAndColumns() {
    this.showDateFilter = false;
    this.showRoleFilter = false;
    this.showStatusFilter = false;
    this.showUserFilter = false;

    switch (this.selectedReportType) {
      case 'Usuarios inscritos':
        this.displayedColumns = ['Nombre', 'Correo', 'Rol', 'Estado'];
        this.columnMap = {
          Nombre: 'nombres',
          Correo: 'email',
          Rol: 'rol.nombre',
          Estado: 'deleted',
        };
        this.showRoleFilter = true;
        this.showStatusFilter = true;
        break;

      case 'Transacciones Fallidas':
        this.displayedColumns = [
          'ID',
          'Emisor',
          'Monto',
          'Motivo Error',
          'Fecha',
        ];
        this.columnMap = {
          ID: 'id',
          Emisor: 'emisor',
          Monto: 'montoIntentado',
          'Motivo Error': 'motivoError',
          Fecha: 'fechaString',
        };
        this.showDateFilter = true;
        break;

      case 'Historico de Movimientos Por Usuario':
        this.displayedColumns = [
          'ID',
          'Emisor',
          'Receptor',
          'Monto',
          'Concepto',
        ];
        this.columnMap = {
          ID: 'id',
          Monto: 'monto',
          Receptor: 'receptor.email',
          Emisor: 'emisor.email',
          Concepto: 'concepto',
        };

        this.displayedColumnsRecargas = [
          'ID',
          'Usuario',
          'Monto',
          'Entidad Financiera',
        ];
        this.columnMapRecargas = {
          ID: 'id',
          Usuario: 'usuario.email',
          Monto: 'montoRecargado',
          'Entidad Financiera': 'entidadFinanciera',
        };

        this.displayedColumnsRetiros = [
          'ID',
          'Cuenta Destino',
          'Monto',
          'Comision',
        ];
        this.columnMapRetiros = {
          ID: 'id',
          'Cuenta Destino': 'cuentaDestino',
          Monto: 'montoRetirado',
          Comision: 'comision',
        };

        this.showDateFilter = true;
        this.showUserFilter = true;
        break;

      case 'Ingresos/Egresos':
        this.displayedColumns = [
          'ID',
          'Emisor',
          'Receptor',
          'Monto',
          'Concepto',
        ];
        this.columnMap = {
          ID: 'id',
          Monto: 'monto',
          Receptor: 'receptor.email',
          Emisor: 'emisor.email',
          Concepto: 'concepto',
        };

        this.displayedColumnsRecargas = [
          'ID',
          'Usuario',
          'Monto',
          'Entidad Financiera',
        ];
        this.columnMapRecargas = {
          ID: 'id',
          Usuario: 'usuario.email',
          Monto: 'montoRecargado',
          'Entidad Financiera': 'entidadFinanciera',
        };

        this.displayedColumnsRetiros = [
          'ID',
          'Cuenta Destino',
          'Monto',
          'Comision',
        ];
        this.columnMapRetiros = {
          ID: 'id',
          'Cuenta Destino': 'cuentaDestino',
          Monto: 'montoRetirado',
          Comision: 'comision',
        };

        this.showDateFilter = true;
        break;

      case 'Ganancias':
        this.displayedColumns = ['ID', 'Cuenta Destino', 'Monto', 'Comision'];
        this.columnMap = {
          ID: 'id',
          'Cuenta Destino': 'cuentaDestino',
          Monto: 'montoRetirado',
          Comision: 'comision',
        };
        this.showDateFilter = true;
        break;

      default:
        this.displayedColumns = [];
        this.reportData = [];
    }
  }

  reporteHistoricoMovimientosPorUsuario() {
    this.reporteService
      .reporteHistoricoMovimientosPorUsuario(
        this.selectedUser,
        this.fechaInicio,
        this.fechaFin
      )
      .subscribe({
        next: (response) => {
          console.log(response);
          this.reportData = response.transacciones;
          this.reportDataRecarga = response.recargas;
          this.reportDataRetiros = response.retiros;
          this.totalRecargas = response.totalRecargas;
          this.totalRetiros = response.totalRetiros;
          this.totalTransacciones = response.totalTransacciones;
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  getReporteUsuarios() {
    this.reporteService
      .reporteUsuarios(this.selectedRole, this.selectedStatus)
      .subscribe({
        next: (response) => {
          this.reportData = response;
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  getUsuariosExceptoElMio() {
    this.usuarioService.getUsuariosExceptoElMio().subscribe({
      next: (response) => {
        this.usuarios = response;
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  // Métodos de simulación para otros tipos de reporte
  reporteTransaccionesFallidas() {
    this.reporteService
      .reporteTransaccionesFallidas(this.fechaInicio, this.fechaFin)
      .subscribe({
        next: (response) => {
          console.log(response);
          this.reportData = response;
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  reporteIngresosEgreso() {
    this.reporteService
      .reporteIngresosEgreso(this.fechaInicio, this.fechaFin)
      .subscribe({
        next: (response) => {
          console.log(response);
          this.reportData = response.transacciones;
          this.reportDataRecarga = response.recargas;
          this.reportDataRetiros = response.retiros;
          this.totalRecargas = response.totalRecargas;
          this.totalRetiros = response.totalRetiros;
          this.totalTransacciones = response.totalTransacciones;
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  reporteGanancias() {
    this.reporteService
      .reporteGanancias(this.fechaInicio, this.fechaFin)
      .subscribe({
        next: (response) => {
          console.log(response);
          this.reportData = response.retiros;
          this.totalRetiros = response.totalRetiros;
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  // Utilidad para acceder a propiedades anidadas
  getNestedValue(item: any, propertyPath: string) {
    return propertyPath
      .split('.')
      .reduce(
        (obj, key) => (obj && obj[key] !== undefined ? obj[key] : null),
        item
      );
  }
}
