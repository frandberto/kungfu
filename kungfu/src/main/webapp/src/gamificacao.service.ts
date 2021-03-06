import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import { Gamificacao } from './gamificacao.entidade';

@Injectable()
export class GamificacaoService {

  errorHandler = error => console.error('GamificacaoService error', error);
  private baseUrl = 'http://localhost:8080/kungfu/api/kungfu-leader';

  constructor(private http: Http) { }

  saveGamificacao(gamificacao: Gamificacao) {
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
  
  getGamificacoesPorPerido(idPeriodo) {
	    return this.http.get(`${this.baseUrl}/gamificacoesPorPeriodo/${idPeriodo}`)
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
  
  getRankings() {
    return this.http.get(`${this.baseUrl}/pontuacoes`)
      .toPromise()
      .then(response => response.json())
      .catch(this.errorHandler);
  }
  
  getRankingsPorPeriodo(idPeriodo) {
	    let myHeaders = new Headers();
	    myHeaders.append('Content-Type', 'application/json');    
	    let myParams = new URLSearchParams();
	    myParams.append('idPeriodoJason', idPeriodo);	
	    let options = new RequestOptions({ headers: myHeaders, params: myParams });
	    if (!idPeriodo) {
	    	idPeriodo = "0";
	    }
	  
	    return this.http.get(`${this.baseUrl}/pontuacoes/${idPeriodo}`)
	      .toPromise()
	      .then(response => response.json())
	      .catch(this.errorHandler);
	  }
  
  getPeriodos() {
	    return this.http.get(`${this.baseUrl}/selecaoPeriodos`)
	      .toPromise()
	      .then(response => response.json())
	      .catch(this.errorHandler);
  }
  
  getRankingAnual(idExercicio) {
    return this.http.get(`${this.baseUrl}/pontuacaoAnual/${idExercicio}`)
      .toPromise()
      .then(response => response.json())
      .catch(this.errorHandler);
  }
  
  getHistoricoRanking() {
	    return this.http.get(`${this.baseUrl}/historicoRanking`)
	      .toPromise()
	      .then(response => response.json())
	      .catch(this.errorHandler);
	  }
  
  getPeriodoAtual() {
    return this.http.get(`${this.baseUrl}/periodoAtual`)
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
