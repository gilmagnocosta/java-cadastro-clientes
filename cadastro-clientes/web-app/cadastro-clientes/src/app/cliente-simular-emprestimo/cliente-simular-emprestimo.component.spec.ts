import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClienteSimularEmprestimoComponent } from './cliente-simular-emprestimo.component';

describe('ClienteSimularEmprestimoComponent', () => {
  let component: ClienteSimularEmprestimoComponent;
  let fixture: ComponentFixture<ClienteSimularEmprestimoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClienteSimularEmprestimoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClienteSimularEmprestimoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
