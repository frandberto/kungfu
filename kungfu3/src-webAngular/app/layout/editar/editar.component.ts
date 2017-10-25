import { Component, Input, Output } from '@angular/core';
import { routerTransition } from '../../router.animations';
import { Gamificacao} from '../../shared/domain/gamificacao';
import { GamificacaoService} from '../../shared/services/gamificacao/gamificacao.service';
import { IEntidade } from '../../shared/domain/ientidade';
import { Observable } from 'rxjs';
import { Subscription } from 'rxjs/Subscription';
import { MyDatePickerModule, IMyOptions } from 'mydatepicker';
import { Message } from '../../shared/domain/message';

@Component({
    selector: 'app-editar',
    templateUrl: './editar.component.html',
    styleUrls: ['./editar.component.scss'],
    animations: [routerTransition()]
})
export class EditarComponent {

  private gamificacoes : Gamificacao[];  
  private periodo = '';  
  @Input() @Output() participantes = [];
  @Input() @Output() eventos = [];
  @Input() @Output() lstSelecaoPeriodo : Array<IEntidade>;
  @Input() @Output() lstSelecaoParticipantes : Array<IEntidade>;
  @Input() @Output() lstSelecaoEventos : Array<IEntidade>;
  @Input() @Output() lstGamificacao : Array<Gamificacao>;
  @Input() @Output() periodoSelecionado: string;
  private idPeriodoSelecionado: string;
  private lstEvento = [];
  private lstParticipante = [];
  private gamificacao: Gamificacao = new Gamificacao();  
  private pontuacao: string;
  private message: Message = new Message();
  

  /**
   * Construtor com a inicializa das listas de participantes, eventos.
   * @param gamificacaoService
   */
  constructor(private gamificacaoService: GamificacaoService) {
    // Periodo atual
    this.gamificacaoService.getPeriodoAtual().
    subscribe((periodoAtual:IEntidade) => this.idPeriodoSelecionado = periodoAtual.id);
    
    // Período de Seleção
    this.gamificacaoService.getPeriodosSelecao().
    subscribe((lstSelecao:IEntidade[]) => this.lstSelecaoPeriodo = lstSelecao);

    // Lista de períodos
    this.gamificacaoService.getPeriodosSelecao().
    subscribe((lstSelecaoPeriodo:IEntidade[]) => this.lstSelecaoPeriodo = lstSelecaoPeriodo);

    // Lista de participantes
    this.gamificacaoService.getParticipantes().
    subscribe((lstSelecaoParticipantes:IEntidade[]) => this.lstSelecaoParticipantes = lstSelecaoParticipantes);

    // Lista de eventos
    this.gamificacaoService.getEventos().
    subscribe((lstSelecaoEventos:IEntidade[]) => 
            this.lstSelecaoEventos = lstSelecaoEventos);

    // Carrega as 
    this.reload();
  }

  private reload() {        
    this.gamificacaoService.getGamificacoesPorPerido(this.idPeriodoSelecionado).
    subscribe(gamificacoes => this.gamificacoes = this.parseGamificacao(gamificacoes));
    this.message.start();
  }

  

  isSelected(entidade: IEntidade) {
      if (entidade.id === this.idPeriodoSelecionado) {
        return 'selected';
      }
      return '';
  }

  isParticipanteSelected(entidade: IEntidade) {
      if (entidade.id === this.gamificacao.idUsuario) {
        return 'selected';
      }
      return '';
  }

  isEventoSelected(entidade: IEntidade) {
      if (entidade.id === this.gamificacao.idEvento) {
        return 'selected';
      }
      return '';
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
           gamificacao.pontuacao = gamificacaoJson.pontuacao; 
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
  
  /**
   * Trata o evento de mudança da seleção do período, com a 
   * atualização do idPeriodoSelecionado com o novo valor da seleção e
   * recarrega a listagem com base na nova seleção.
   * @param event evento de mudança
   */

  onSelectedChangePeriodo(event) {      
    this.idPeriodoSelecionado = event.currentTarget.value;
    this.reload();
  }

  onSelectedChangeParticipante(event) {
       let idParticipante = event.currentTarget.value;       
       this.gamificacao.idUsuario = idParticipante;          
  }

  onSelectedChangeEvento(event) {
       let idEvento = event.currentTarget.value;
       this.gamificacao.idEvento = idEvento;
       this.gamificacao.pontuacao = this.obterPontuacaoEvento(idEvento);
  }
  
  /**
 * Retorna a data formatada em dd/mm/aaaa
 */
  getDataFormatada(data: Date) {
      let dia = data.getDate();
      let mes = data.getMonth() + 1;
      let ano = data.getFullYear();
      let dataAtualFormatada : string;
      dataAtualFormatada = dia + '/' + mes + '/' + ano;      
      return dataAtualFormatada;
  }

  idEventoSelecionado: string;
  idParticipanteSelecionado: string;
 
  private validar() {
      if (this.gamificacao.idUsuario == "" || 
          this.gamificacao.idEvento == "" ) {
          //window.alert('Preencha todos os campos obrigatórios');
          return false;
      }
      return true;          
  }

  private obterPontuacaoEvento(idEvento) {
    for (let evento of this.lstSelecaoEventos) {
      if (evento.id == idEvento) {
        return evento.extraPropriedade;
      }
    }
    return null;
  }

  
  private datePickerOptions: IMyOptions = {        
        dateFormat: 'dd/mm/yyyy',
        editableDateField: true,
        dayLabels: {su: "Dom", mo: "Seg", tu: "Ter", we: "Qua", th: "Qui", fr: "Sex", sa: "Sab"},
        monthLabels: { 1: "Jan", 2: "Fev", 3: "Mar", 4: "Abr", 5: "Mai", 6: "Jun", 7: "Jul", 8: "Ago", 9: "Set", 10: "Out", 11: "Nov", 12: "Dez" },
        todayBtnTxt: "Hoje",
        firstDayOfWeek: "su",
        sunHighlight: true,
    };

    onClear() {
       this.gamificacao = new Gamificacao();
       this.message.start();
    }

    onSave() {
      if (this.validar()) {
         // Atualiza a data de registro com a data do calendario
         this.gamificacao.dataRegistro = 
                this.parseCalendario2Data(this.gamificacao.dataCalendario);
         this.gamificacao.dataCalendario="";
          this.gamificacaoService.saveGamificacao(this.gamificacao)
          .subscribe(() => {this.reload(); 
                            this.onClear();
                            this.message.setInfoMessage("Transação realizada com sucesso!");
                            });        
      } else {
        this.message.setErrorMessage('Preencha os campos obrigatórios!');
      }
    }

    onEdit(gamificacao: Gamificacao) {
      this.gamificacao = gamificacao;
    }

    onRemove(gamificacao: Gamificacao) {
      this.gamificacaoService.removeGamificacao(gamificacao).
      subscribe(() => {this.reload();});
    }
}
