import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError, tap, map } from 'rxjs/operators';
import { Cliente } from '../models/cliente';
import { ConfigService } from './config.service';
import { SimulacaoEmprestimo } from '../models/simulacao-emprestimo';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
const controller = "clientes";

@Injectable({
    providedIn: 'root'
})
export class ClienteService {
    
    private apiUrl: string = '';
    
    constructor(private http: HttpClient, private configService: ConfigService) { 
        this.apiUrl = 'http://localhost:8080/api/' + controller;
    }

    private handleError<T> (operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {
            console.error(error);
            return of(result as T);
        };
    }

    listar(): Observable<Cliente[]> {
        return this.http.get<Cliente[]>(this.apiUrl)
          .pipe(
            tap(clientes => console.log('listou clientes')),
            catchError(this.handleError('listar', []))
          );
      }
      
    obter(id: number): Observable<Cliente> {
        const url = this.apiUrl + "/" + id;
        return this.http.get<Cliente>(url).pipe(
          tap(_ => console.log('obteve id=${id}')),
          catchError(this.handleError<Cliente>('obter id=${id}'))
        );
    }
      
    adicionar (cliente): Observable<Cliente> {
        return this.http.post<Cliente>(this.apiUrl, cliente, httpOptions).pipe(
            tap((product: Cliente) => console.log('adicionou id=${cliente.codigo}')),
            catchError(this.handleError<Cliente>('adicionar'))
        );
    }
    
    atualizar(id, cliente): Observable<any> {
        const url = this.apiUrl + "/" + id;
        return this.http.put(url, cliente, httpOptions).pipe(
            tap(_ => console.log('atualizou id=${id}')),
            catchError(this.handleError<any>('atualizar'))
        );
    }
    
    deletar(id): Observable<Cliente> {
        const url = this.apiUrl + "/" + id;
    
        return this.http.delete<Cliente>(url, httpOptions).pipe(
            tap(_ => console.log('deletou id=${id}')),
            catchError(this.handleError<Cliente>('deletar'))
        );
    }

    simularEmprestimo(dadosSimulacao: SimulacaoEmprestimo): Observable<any> {
        const url = this.apiUrl + "/simular-emprestimo/" + dadosSimulacao.codigoCliente;
        return this.http.post(url, dadosSimulacao, httpOptions).pipe(
            tap(_ => console.log('simulou id=${id}')),
            catchError(this.handleError<any>('simularEmprestimo'))
        );
    }
}