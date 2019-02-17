import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError, tap, map } from 'rxjs/operators';
import { Cliente } from '../models/cliente';
import { ConfigService } from './config.service';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
const controller = "clientes";

@Injectable({
    providedIn: 'root'
})
export class ClienteService {
    
    private apiUrl: string = '';
    //private headers: Headers;
    //private options:RequestOptions;

    constructor(private http: HttpClient, private configService: ConfigService) { 
        this.apiUrl = 'http://localhost:8080/api/' + controller;
    }

    private handleError<T> (operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {
        
            // TODO: send the error to remote logging infrastructure
            console.error(error); // log to console instead
        
            // Let the app keep running by returning an empty result.
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
}