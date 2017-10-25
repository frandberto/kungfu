import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { Gamificacao } from '../../domain/gamificacao';
import { IEntidade } from '../../domain/ientidade';
import { IFrequencia } from '../../domain/ifrequencia';
import { IPontuacao } from '../../domain/ipontuacao';
import { Observable } from 'rxjs';

@Injectable()
export class GamificacaoService {

  errorHandler = error => console.error('GamificacaoService error', error);
  private server = 'localhost:8080';
  private baseUrl = `http://${this.server}/kungfuService/api/kungfu-leader`;
  private listaResultado: any;

  constructor(private http: Http) { }

  private lstSelectionElements: IEntidade[] = Array<IEntidade>();

  saveGamificacao(gamificacao: Gamificacao): Observable<Response> {
    let body = JSON.stringify(gamificacao);
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${this.baseUrl}/gamificacao`, body, options);
  }

  getGamificacoes() {
    return this.http.get(`${this.baseUrl}/gamificacoes`)
      .toPromise()
      .then(response => response.json())
      .catch(this.errorHandler);
  }

  getGamificacoesPorPerido(idPeriodo): Observable<Response> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.get(`${this.baseUrl}/gamificacoesPorPeriodo/${idPeriodo}`, options)
      .map(response => response.json());
  }

  getEventos(): Observable<IEntidade[]> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.get(`${this.baseUrl}/selecaoEventos`, options)
      .map(response => { return <IEntidade[]>response.json(); });
  }

  getParticipantes(): Observable<IEntidade[]> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.get(`${this.baseUrl}/selecaoUsuarios`, options)
      .map(response => { return <IEntidade[]>response.json(); });
  }

  getRankings() {
    return this.http.get(`${this.baseUrl}/pontuacoes`)
      .toPromise()
      .then(response => response.json())
      .catch(this.errorHandler);
  }

  getRankingsPorPeriodo(idPeriodo): Observable<Response> {
    let myHeaders = new Headers();
    myHeaders.append('Content-Type', 'application/json');
    let myParams = new URLSearchParams();
    myParams.append('idPeriodoJason', idPeriodo);
    let options = new RequestOptions({ headers: myHeaders, params: myParams });
    if (!idPeriodo) {
      idPeriodo = "0";
    }

    return this.http.get(`${this.baseUrl}/pontuacoes/${idPeriodo}`)
      .map(response => response.json());
  }

  getPeriodos() {
    this.http.get(`${this.baseUrl}/selecaoPeriodos`).
      subscribe(res => res.json);
  }

  getPeriodosSelecao(): Observable<IEntidade[]> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.get(`${this.baseUrl}/selecaoPeriodos`, options).
      map(response => { return <IEntidade[]>response.json(); });
  }

  getAnosSelecao(): Observable<IEntidade[]> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.get(`${this.baseUrl}/selecaoAnos`, options).
      map(response => { return <IEntidade[]>response.json(); });
  }

  getFrequenciaEventos(idPeriodo: string, codigo: string): Observable<IFrequencia[]> {
    // if (!idPeriodo || !codigo) {
    // 	console.log('Erro na Requisição de Frequencia de Eventos. Parâmetros incompletos!');
    //   return null;
    // }	  
    if (idPeriodo === undefined) {
      idPeriodo = '99';
    }
    return this.http.get(`${this.baseUrl}/frequenciaEventos/${idPeriodo}/${codigo}`)
      .map(response => { return <IFrequencia[]>response.json(); });

  }

  getPontuacaoMedia(idAnoSelecionado: string): Observable<IPontuacao[]> {
    return this.http.get(`${this.baseUrl}/pontuacoesMedias/${idAnoSelecionado}`)
      .map(response => { return <IPontuacao[]>response.json(); });

  }

  getPontuacaoUsuario(codigo: string, ano:string): Observable<IPontuacao[]> {
    return this.http.get(`${this.baseUrl}/pontuacoesUsuario/${codigo}/${ano}`)
      .map(response => { return <IPontuacao[]>response.json(); });

  }

  getListaResultado() {
    return this.listaResultado;
  }

  getListaSelectionElements() {
    return this.lstSelectionElements;
  }

  /*
   * Parse da lista de objetos para uma lista de seleção de Entidades Genéricas
   */
  parse2ListaEntidade(lstObjetosJson, listaSelecaoEntidade: Array<IEntidade>) {
    for (let objetosJSON of lstObjetosJson) {
      let itemSelecao: IEntidade;
      itemSelecao.id = objetosJSON.id;
      itemSelecao.descricao = objetosJSON.descricao;
      listaSelecaoEntidade.push(itemSelecao);
    }
  }

  private parse2Entidade(periodo) {
    let entidade: IEntidade;
    entidade.id = periodo.idPeriodo;
    entidade.descricao = periodo.descricao;
    return entidade;
  }

  getRankingAnual(idExercicio): Observable<Response> {
    return this.http.get(`${this.baseUrl}/pontuacaoAnual/${idExercicio}`)
      .map(response => response.json());
  }

  getHistoricoRanking() {
    return this.http.get(`${this.baseUrl}/historicoRanking`)
      .map(response => response.json());
  }

  /**
   * Retorna Observable do período atual
   */
  getPeriodoAtual(): Observable<IEntidade> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.get(`${this.baseUrl}/periodoAtual`, options)
      .map(response => { return <IEntidade>response.json() });
  }

  removeGamificacao(gamificacao): Observable<Response> {
    let body = gamificacao.idGamificacao;
    let headers = new Headers({ 'Content-Type': 'text/plain' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${this.baseUrl}/exclusao`, body, options);
  }

}
