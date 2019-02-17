import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ClientesComponent } from './clientes/clientes.component';
import { ClienteNovoComponent } from './cliente-novo/cliente-novo.component';
import { ClienteEditarComponent } from './cliente-editar/cliente-editar.component';
import { ClienteSimularEmprestimoComponent } from './cliente-simular-emprestimo/cliente-simular-emprestimo.component';

const routes: Routes = [
  {
    path: 'clientes',
    component: ClientesComponent,
    data: { title: 'Lista de Clientes' }
  },
  {
    path: 'cliente-novo',
    component: ClienteNovoComponent,
    data: { title: 'Adicionar um novo Cliente' }
  },
  {
    path: 'cliente-editar/:id',
    component: ClienteEditarComponent,
    data: { title: 'Edição de Cliente' }
  },
  {
    path: 'cliente-simular-emprestimo/:id',
    component: ClienteSimularEmprestimoComponent,
    data: { title: 'Simulação de Emprestimo de Cliente' }
  },
  { path: '',
    redirectTo: '/',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
