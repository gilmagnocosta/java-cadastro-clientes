import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { ClienteService } from '../services/cliente.service';
import { ValidaTipoCliente } from '../utils/valida-tipo-cliente.validator';

@Component({
  selector: 'app-cliente-editar',
  templateUrl: './cliente-editar.component.html',
  styleUrls: ['./cliente-editar.component.scss']
})
export class ClienteEditarComponent implements OnInit {

  clienteForm: FormGroup;
  codigo:string='';
  nome:string='';
  rendimentoMensal:string='';
  risco:string='';
  endereco:string='';
  valorTotalPatrimonio:number=null;
  valorTotalDividas:number=null;
  tipoCliente:number=null;
  atualmenteEmpregado:Boolean=null;
  carregando = false;

  constructor(private router: Router, private route: ActivatedRoute, private service: ClienteService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.obtemCliente(this.route.snapshot.params['id']);

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

  obtemCliente(id) {
    this.service.obter(id).subscribe(data => {
      this.codigo = data.codigo;
      this.clienteForm.setValue({
        nome: data.nome,
        rendimentoMensal: data.rendimentoMensal,
        risco: data.risco,
        endereco: data.endereco,
        valorTotalPatrimonio: data.valorTotalPatrimonio,
        valorTotalDividas: data.valorTotalDividas,
        tipoCliente: data.tipoCliente,
        atualmenteEmpregado: data.atualmenteEmpregado
      });
    });
  }

  onFormSubmit(form:NgForm) {
    this.carregando = true;
    this.service.atualizar(this.codigo, form)
      .subscribe(res => {
          let id = res['codigo'];
          this.carregando = false;
          this.router.navigate(['/clientes']);
        }, (err) => {
          console.log(err);
          this.carregando = false;
        }
      );
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
