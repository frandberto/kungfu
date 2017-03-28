import { Component } from '@angular/core';
import { GamificacaoService } from './gamificacao.service';

@Component({
  selector: 'kungfu-app',
  template: `
    <kungfu-edit [gamificacao]="editableGamificacao" 
      [eventos]="this.lstEvento" 
      [participantes]="this.lstParticipante"
      (save)="save($event)" (clear)="clear()"></kungfu-edit>
    <kungfu-list [gamificacoes]="this.gamificacoes"
      (edit)="edit($event)" (remove)="remove($event)"></kungfu-list>
  `,
})
export class AppComponent {

  gamificacoes = [];
  lstEvento = [];
  lstParticipante = [];
  editableGamificacao = {};

  constructor(private gamificacaoService: GamificacaoService) {
    gamificacaoService.errorHandler = error =>
      window.alert('Falha na requisição do serviço');
    this.reload();
  }

  clear() {
    this.editableGamificacao = {};
  }

  edit(gamificacao) {
    //this.editableGamificacao = gamificacao;
    this.editableGamificacao.idGamificacao = gamificacao.idGamificacao;    
    this.editableGamificacao.idEvento = gamificacao.idEvento;
    this.editableGamificacao.idUsuario = gamificacao.idUsuario; 
    this.editableGamificacao.dataRegistro = gamificacao.dataRegistro;   
    this.editableGamificacao.pontuacao = gamificacao.pontuacao;
    this.editableGamificacao.observacao = gamificacao.observacao;
  }

  remove(gamificacao) {
    this.gamificacaoService.removeGamificacao(gamificacao)
      .then(() => this.reload());    
  }

  save(gamificacao) {    
    this.gamificacaoService.saveGamificacao(gamificacao)
        .then(() => this.reload());  
    this.clear();
  }

  private reload() {
    this.gamificacaoService.getGamificacoes()
      .then(gamificacoes => this.gamificacoes = gamificacoes);

    this.gamificacaoService.getEventos()
      .then(eventos => this.lstEvento = this.parseEvento(eventos));

     this.gamificacaoService.getParticipantes()
      .then(participantes => this.lstParticipante = this.parseParticipante(participantes));
    
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

}
