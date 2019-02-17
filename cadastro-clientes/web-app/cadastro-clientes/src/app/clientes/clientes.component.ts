import { Component, OnInit } from '@angular/core';
import { Cliente } from '../models/cliente';
import { ClienteService } from '../services/cliente.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.scss']
})
export class ClientesComponent implements OnInit {
  colunas: string[] = ['codigo', 'nome', 'endereco', 'acoes'];
  linhas: Cliente[] = [];
  carregando = true;
  
  constructor(private router: Router, private service: ClienteService) { }

  ngOnInit() {
    this.listar();  
  }

  listar(){
    this.service.listar()
    .subscribe(res => {
      this.linhas = res;
      console.log(this.linhas);
      this.carregando = false;
    }, err => {
      console.log(err);
      this.carregando = false;
    })
  }

  deletar(id: string) {
    if(confirm("Tem certeza que deseja deletar este cliente?")) {
      this.carregando = true;
      this.service.deletar(id)
      .subscribe(res=> {
        this.listar();
      }, (err) => {
        console.log(err);
        this.carregando = false;
      });
    }
  }

}
