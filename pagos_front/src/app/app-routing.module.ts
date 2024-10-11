import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { DashClienteComponent } from './cliente/dash-cliente/dash-cliente.component';
import { InicioClientePageComponent } from './cliente/inicio-cliente-page/inicio-cliente-page.component';

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
    path: 'dashboard-cliente',
    component: DashClienteComponent,
    children: [
      {
        path: 'inicio',
        component: InicioClientePageComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
