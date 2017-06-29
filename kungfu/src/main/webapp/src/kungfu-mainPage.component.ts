import { Component } from '@angular/core';
import { GamificacaoService } from './gamificacao.service';
import { Gamificacao } from './gamificacao.entidade';

@Component({
  selector: 'kungfu-MainPage-app',
  template: `
    <kungfu-edit [gamificacao]="this.editableGamificacao" 
      [eventos]="this.lstEvento" 
      [participantes]="this.lstParticipante"
      (save)="save($event)" (clear)="clear()"></kungfu-edit>
    <kungfu-list [gamificacoes]="this.gamificacoes" [isButtonEnabled]=true
      (edit)="edit($event)" (remove)="remove($event)"></kungfu-list>
  `,
})
export class KungfuMainPage {

  gamificacoes : Gamificacao[];
  lstEvento = [];
  lstParticipante = [];
  editableGamificacao: Gamificacao;

  constructor(private gamificacaoService: GamificacaoService) {
    gamificacaoService.errorHandler = error =>
      window.alert('Falha na requisição do serviço');
    this.reload();
  }
  /**
   * Inicializa uma instancia de gamificacao
   */
  clear() {
    this.editableGamificacao = new Gamificacao();    
  }

  edit(gamificacao: Gamificacao) {
    this.editableGamificacao.idGamificacao = gamificacao.idGamificacao;    
    this.editableGamificacao.idEvento = gamificacao.idEvento;
    this.editableGamificacao.idUsuario = gamificacao.idUsuario; 
    this.editableGamificacao.dataRegistro = gamificacao.dataRegistro;   
    this.editableGamificacao.pontuacao = gamificacao.pontuacao;
    this.editableGamificacao.observacao = gamificacao.observacao;
    this.editableGamificacao.dataCalendario = this.parseData2Calendario(gamificacao.dataRegistro);
  }

  remove(gamificacao: Gamificacao) {
    this.gamificacaoService.removeGamificacao(gamificacao)
      .then(() => this.reload());    
  }

  save(gamificacao: Gamificacao) {
    // Atualiza a data de registro com a data do calendario
    gamificacao.dataRegistro = this.parseCalendario2Data(gamificacao.dataCalendario);
    gamificacao.dataCalendario="";
    this.gamificacaoService.saveGamificacao(gamificacao)
        .then(() => this.reload());  
    this.clear();
  }

  private reload() {
    this.gamificacaoService.getGamificacoes()
      .then(gamificacoes => this.gamificacoes = this.parseGamificacao(gamificacoes));

    this.gamificacaoService.getEventos()
      .then(eventos => this.lstEvento = this.parseEvento(eventos));

    this.gamificacaoService.getParticipantes()
      .then(participantes => this.lstParticipante = this.parseParticipante(participantes));

    this.clear();
  }
  
  /*
   * Parse da lista de participantes para o formato de seleção
   */
  private parseParticipante(participantes) {
      let lstParticipante = [];
      let indice: number;
      indice = 0;
      for (let participante of participantes) {
          lstParticipante[indice]={'id':participante.idUsuario, 
                            'descricao':participante.apelido};
          indice++;
      }
      return lstParticipante;
  }

  /*
   * Parse da lista de eventos para o formato de seleção
   */
  private parseEvento(eventos) {
      let lstEventos = [];
      let indice: number;
      indice = 0;
      for (let evento of eventos) {
          lstEventos[indice]={'id':evento.idEvento, 
                            'descricao':evento.descricao,
                            'pontuacao':evento.pontuacao};
          indice++;
      }
      return lstEventos;
  }
  
  private parseGamificacao(lstGamificacoesJason) {
      let gamificacoes: Gamificacao[];
      gamificacoes = [];
      for (let gamificacaoJson of lstGamificacoesJason) {
           let gamificacao = new Gamificacao();
           gamificacao.idGamificacao = gamificacaoJson.idGamificacao;
           gamificacao.idEvento = gamificacaoJson.idEvento;
           gamificacao.nomeEvento = gamificacaoJson.nomeEvento;
           gamificacao.idUsuario = gamificacaoJson.idUsuario;
           gamificacao.apelido = gamificacaoJson.apelido;
           gamificacao.dataRegistro = this.parseData(gamificacaoJson.dataRegistro);
           gamificacao.observacao = gamificacaoJson.observacao;
           gamificacao.pontuacao = Number.parseFloat(gamificacaoJson.pontuacao); 
           gamificacao.dataCalendario = this.parseData2Calendario(gamificacao.dataRegistro);
           gamificacoes.push(gamificacao);       
      } 
      return gamificacoes;
  }

  private parseData(dataString: string) {
       let dia : string; 
       let mes : string; 
       let ano : string; 

       dia = dataString.slice(0,2);
       mes = dataString.slice(3,5);       
       ano = dataString.slice(6,10);

       let dataFormatada = mes +'/' + dia + '/' + ano;
       return new Date(dataFormatada);
  }
  
  /**
   * Parse de data no formato Date para um objeto de calendario
   */
  private parseData2Calendario(data: Date) {
       return { date: { year: data.getFullYear(), month: data.getMonth() + 1, 
                                day: data.getDate() } };
  }

  /**
   * Parse de calendario aara o formato Date
   */
  private parseCalendario2Data(calendario) {
       return new Date(calendario.date.year, calendario.date.month - 1, calendario.date.day );
  }
}