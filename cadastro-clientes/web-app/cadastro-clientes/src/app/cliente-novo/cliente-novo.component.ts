import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ClienteService } from '../services/cliente.service';
import { ValidaTipoCliente } from '../utils/valida-tipo-cliente.validator';

@Component({
  selector: 'app-cliente-novo',
  templateUrl: './cliente-novo.component.html',
  styleUrls: ['./cliente-novo.component.scss']
})
export class ClienteNovoComponent implements OnInit {

  clienteForm: FormGroup;
  nome:string='';
  rendimentoMensal:string='';
  risco:string='';
  endereco:string='';
  valorTotalPatrimonio:number=null;
  valorTotalDividas:number=null;
  tipoCliente:number=null;
  atualmenteEmpregado:Boolean=null;
  carregando = false;

  constructor(private router: Router, private service: ClienteService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.clienteForm = this.formBuilder.group({
      'nome' : [null, Validators.required],
      'rendimentoMensal' : [0, Validators.required],
      'risco' : [null, Validators.required],
      'endereco' : [null, Validators.required],
      'valorTotalPatrimonio' : [0, Validators.required],
      'valorTotalDividas' : [0, Validators.required],
      'tipoCliente' : [null, Validators.required],
      'atualmenteEmpregado' : [false, Validators.required]
    },
    {
      validator: ValidaTipoCliente('tipoCliente', 'valorTotalPatrimonio', 'valorTotalDividas')
    });
  }

  get f() { return this.clienteForm.controls; }

  onFormSubmit(form: NgForm) {
    this.carregando = true;
    this.service.adicionar(form)
      .subscribe(res => {
          this.carregando = false;
          this.router.navigate(['/clientes']);
        }, (err) => {
          console.log(err);
          this.carregando = false;
        });
  }

  definirRisco() {
    let rendimento = this.clienteForm.get('rendimentoMensal').value;
        
    if (rendimento <= 2000){
      this.risco = 'C';
    }else if (rendimento > 2000 && rendimento <= 8000){
      this.risco = 'B';
    } else if(rendimento > 8000){
      this.risco = 'A'
    }
  }
}
