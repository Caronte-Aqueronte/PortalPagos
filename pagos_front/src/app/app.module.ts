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
import { InicioClientePageComponent } from './cliente/inicio-cliente-page/inicio-cliente-page.component'; // Importar los iconos sólidos

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ErrorAlertComponent,
    DashClienteComponent,
    InicioClientePageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FontAwesomeModule,
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
