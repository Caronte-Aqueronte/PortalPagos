import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { DashClienteComponent } from './cliente/dash-cliente/dash-cliente.component';
import { InicioClientePageComponent } from './cliente/inicio-cliente-page/inicio-cliente-page.component';
import { MiPerfilClienteComponent } from './cliente/perfil/mi-perfil-cliente/mi-perfil-cliente.component';
import { EliminarCuentaClienteComponent } from './cliente/perfil/eliminar-cuenta-cliente/eliminar-cuenta-cliente.component';
import { CentroCuentasComponent } from './cliente/perfil/centro-cuentas/centro-cuentas.component';
import { InfoComponent } from './cliente/perfil/info/info.component';
import { IngresosEgresosComponent } from './cliente/ingresos-egresos/ingresos-egresos.component';
import { CrearUsuarioComponent } from './auth/crear-usuario/crear-usuario.component';

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
          { path: 'centro_cuentas', component: CentroCuentasComponent },
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
