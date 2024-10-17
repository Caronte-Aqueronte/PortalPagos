import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EliminarCuentaClienteComponent } from './eliminar-cuenta-cliente.component';

describe('EliminarCuentaClienteComponent', () => {
  let component: EliminarCuentaClienteComponent;
  let fixture: ComponentFixture<EliminarCuentaClienteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EliminarCuentaClienteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EliminarCuentaClienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
