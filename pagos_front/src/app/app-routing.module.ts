import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { DashClienteComponent } from './cliente/dash-cliente/dash-cliente.component';
import { InicioClientePageComponent } from './cliente/inicio-cliente-page/inicio-cliente-page.component';
import { MiPerfilClienteComponent } from './cliente/perfil/mi-perfil-cliente/mi-perfil-cliente.component';
import { EliminarCuentaClienteComponent } from './cliente/perfil/eliminar-cuenta-cliente/eliminar-cuenta-cliente.component';
import { InfoComponent } from './cliente/perfil/info/info.component';
import { IngresosEgresosComponent } from './cliente/ingresos-egresos/ingresos-egresos.component';
import { CrearUsuarioComponent } from './auth/crear-usuario/crear-usuario.component';
import { DashAdminComponent } from './admin/dash-admin/dash-admin.component';
import { MiPerfilAdminComponent } from './admin/perfil/mi-perfil-admin/mi-perfil-admin.component';
import { ReportesComponent } from './admin/reportes/reportes.component';
import { UsuariosComponent } from './admin/usuarios/usuarios.component';

const routes: Routes = [
  {
    path: '*',
    component: LoginComponent,
  },
  {
    path: '',
    component: LoginComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'crear_usuario',
    component: CrearUsuarioComponent,
  },
  {
    path: 'admin-dashboard',
    component: DashAdminComponent,
    children: [
      {
        path: 'perfil',
        component: MiPerfilAdminComponent,

        children: [{ path: 'info', component: InfoComponent }],
      },
      { path: 'usuarios', component: UsuariosComponent },
      { path: 'reportes', component: ReportesComponent },
    ],
  },
  {
    path: 'dashboard-cliente',
    component: DashClienteComponent,
    children: [
      {
        path: 'inicio',
        component: InicioClientePageComponent,
      },
      {
        path: 'ingresos_egresos',
        component: IngresosEgresosComponent,
      },
      {
        path: 'perfil',
        component: MiPerfilClienteComponent,

        children: [
          { path: 'borrar', component: EliminarCuentaClienteComponent },
          { path: 'info', component: InfoComponent },
        ],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
