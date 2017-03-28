import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';

@Injectable()
export class GamificacaoService {

  errorHandler = error => console.error('GamificacaoService error', error);
  private baseUrl = 'http://localhost:8180/kungfu/api/kungfu-leader';

  constructor(private http: Http) { }

  saveGamificacao(gamificacao) {
    let body = JSON.stringify(gamificacao);    
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this.http.post(`${this.baseUrl}/gamificacao`, body, options)
      .toPromise()
      .catch(this.errorHandler);
  }

  getGamificacoes() {
    return this.http.get(`${this.baseUrl}/gamificacoes`)
      .toPromise()
      .then(response => response.json())
      .catch(this.errorHandler);
  }

  getEventos() {
    return this.http.get(`${this.baseUrl}/selecaoEventos`)
      .toPromise()
      .then(response => response.json())
      .catch(this.errorHandler);
  }

  getParticipantes() {
    return this.http.get(`${this.baseUrl}/selecaoUsuarios`)
      .toPromise()
      .then(response => response.json())
      .catch(this.errorHandler);
  }

  removeGamificacao(gamificacao) {
    let body = gamificacao.idGamificacao;
    let headers = new Headers({ 'Content-Type': 'text/plain' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${this.baseUrl}/exclusao`, body, options)
      .toPromise()
      .catch(this.errorHandler);
  }

}
