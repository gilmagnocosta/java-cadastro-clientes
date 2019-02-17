import { Component, OnInit } from '@angular/core';
import { NgForm, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ClienteService } from '../services/cliente.service';
import { ResultadoSimulacao } from '../models/resultado-simulacao';
import { Cliente } from '../models/cliente';
import { SimulacaoEmprestimo } from '../models/simulacao-emprestimo';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-cliente-simular-emprestimo',
  templateUrl: './cliente-simular-emprestimo.component.html',
  styleUrls: ['./cliente-simular-emprestimo.component.scss']
})
export class ClienteSimularEmprestimoComponent implements OnInit {

  simulacaoForm: FormGroup;
  cliente: Cliente;
  dadosSimulacao: SimulacaoEmprestimo;
  resultadoSimulacao: ResultadoSimulacao;
  carregando = false;
  codigo: string;

  constructor(private router: Router, private route: ActivatedRoute,  private service: ClienteService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.inicializarSimulacao();

    this.service.obter(this.route.snapshot.params['id'])
    .subscribe(data => {
      this.cliente = new Cliente();
      this.cliente.codigo = data.codigo;
      this.cliente.nome = data.nome;
    });

    this.simulacaoForm = this.formBuilder.group({
      'valorSolicitado' : [null, Validators.min(100)],
      'mesesDuracao' : [0, Validators.compose([Validators.min(1), Validators.max(200)])]
    });
  }

  onFormSubmit(form: NgForm) {
    this.carregando = true;
    
    this.dadosSimulacao = new SimulacaoEmprestimo();
    this.dadosSimulacao.mesesDuracao = this.simulacaoForm.get('mesesDuracao').value;
    this.dadosSimulacao.valorSolicitado = this.simulacaoForm.get('valorSolicitado').value;
    this.dadosSimulacao.codigoCliente = this.cliente.codigo;

    this.service.simularEmprestimo(this.dadosSimulacao)
      .subscribe(res => {
          this.carregando = false;
          this.resultadoSimulacao = new ResultadoSimulacao();
          this.resultadoSimulacao.valorPrestacao = res.valorPrestacao;
          this.resultadoSimulacao.valorTotal = res.valorTotal;
        }, (err) => {
          console.log(err);
          this.carregando = false;
        });
  }

  inicializarSimulacao(){
    this.cliente = new Cliente();
    this.dadosSimulacao = null;
    this.resultadoSimulacao = null;
  }
  
}
