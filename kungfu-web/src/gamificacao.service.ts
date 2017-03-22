import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

@Injectable()
export class GamificacaoService {

  errorHandler = error => console.error('GamificacaoService error', error);
  private baseUrl = 'http://localhost:8180/kungfu/api/kungfu-leader';

  constructor(private http: Http) { }

  saveGamificacao(gamificacao) {
    const json = JSON.stringify(gamificacao);
    return this.http.post(`${this.baseUrl}/gamificacao`, json)
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
    return this.http.delete(`${this.baseUrl}/gameficacao/${gamificacao.idGamificacao}`)
      .toPromise()
      .catch(this.errorHandler);
  }

}
