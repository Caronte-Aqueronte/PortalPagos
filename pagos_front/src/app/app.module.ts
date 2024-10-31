import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { ErrorAlertComponent } from './alerts/error-alert/error-alert.component';
import { InterceptorService } from './services/auth/interceptor.service';
import { DashClienteComponent } from './cliente/dash-cliente/dash-cliente.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { InicioClientePageComponent } from './cliente/inicio-cliente-page/inicio-cliente-page.component';
import { MiPerfilClienteComponent } from './cliente/perfil/mi-perfil-cliente/mi-perfil-cliente.component';
import { EliminarCuentaClienteComponent } from './cliente/perfil/eliminar-cuenta-cliente/eliminar-cuenta-cliente.component';
import { InfoComponent } from './cliente/perfil/info/info.component';
import { SuccessAlertComponent } from './alerts/success-alert/success-alert.component';
import { TransactionItemComponent } from './cliente/transaction-item/transaction-item.component';
import { IngresosEgresosComponent } from './cliente/ingresos-egresos/ingresos-egresos.component';
import { FormsModule } from '@angular/forms';
import { CrearUsuarioComponent } from './auth/crear-usuario/crear-usuario.component';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RecargaItemComponent } from './cliente/recarga-item/recarga-item.component';
import { RetiroItemComponent } from './cliente/retiro-item/retiro-item.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { DashAdminComponent } from './admin/dash-admin/dash-admin.component';
import { MiPerfilAdminComponent } from './admin/perfil/mi-perfil-admin/mi-perfil-admin.component';
import { UsuariosComponent } from './admin/usuarios/usuarios.component';
import { ReportesComponent } from './admin/reportes/reportes.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ErrorAlertComponent,
    DashClienteComponent,
    InicioClientePageComponent,
    MiPerfilClienteComponent,
    EliminarCuentaClienteComponent,
    InfoComponent,
    SuccessAlertComponent,
    TransactionItemComponent,
    IngresosEgresosComponent,
    CrearUsuarioComponent,
    RecargaItemComponent,
    RetiroItemComponent,
    DashAdminComponent,
    MiPerfilAdminComponent,
    UsuariosComponent,
    ReportesComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FontAwesomeModule,
    FormsModule,
    BrowserAnimationsModule,
    NgxChartsModule,
    ToastrModule.forRoot({
      timeOut: 3000, // Duración de los mensajes
      positionClass: 'toast-top-right', // Posición
      preventDuplicates: true, // Evita mensajes duplicados
    }),
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: InterceptorService,
      multi: true, // Permite múltiples interceptores si es necesario
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
  constructor(library: FaIconLibrary) {
    library.addIconPacks(fas); // Añadir el pack de iconos sólidos
  }
}
