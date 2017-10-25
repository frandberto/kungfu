import { Component, Input, Output } from '@angular/core';
import { routerTransition } from '../../router.animations';
import { Gamificacao} from '../../shared/domain/gamificacao';
import { GamificacaoService} from '../../shared/services/gamificacao/gamificacao.service';
import { IEntidade } from '../../shared/domain/ientidade';
import { Observable } from 'rxjs';
import { Subscription } from 'rxjs/Subscription';

@Component({
    selector: 'app-visualizar',
    templateUrl: './visualizar.component.html',
    styleUrls: ['./visualizar.component.scss'],
    animations: [routerTransition()]
})
export class VisualizarComponent {

  private gamificacoes : Gamificacao[];  
  private periodo = '';
  @Input() @Output() lstSelecaoPeriodo : Array<IEntidade>;
  private idPeriodoSelecionado: string;
  
  constructor(private gamificacaoService: GamificacaoService) {
    this.gamificacaoService.getPeriodoAtual().
    subscribe((periodoAtual:IEntidade) => this.idPeriodoSelecionado = periodoAtual.id);

    this.gamificacaoService.getPeriodosSelecao().
    subscribe((lstSelecao:IEntidade[]) => this.lstSelecaoPeriodo = lstSelecao);

    this.reload();
  }

  private reload() {        
    this.gamificacaoService.getGamificacoesPorPerido(this.idPeriodoSelecionado).
    subscribe(gamificacoes => this.gamificacoes = this.parseGamificacao(gamificacoes));
  }

  /**
   * Verifica se a entidade está selecionada
   * @param entidade
   */
  isSelected(entidade: IEntidade) {
      if (entidade.id === this.idPeriodoSelecionado) {
        return 'selected';
      }
      return '';
  }
  
  /**
   * Retorna o Observable<IEntidade> dos perídos de seleção
   */
  getPeriodosSelecao(): Observable<IEntidade[]> {
        return this.gamificacaoService.getPeriodosSelecao();
  }
  
  /**
   * Realiza o parse da lista lstGamificacoesJason para array de objetos Gamificacao
   * @param lstGamificacoesJason
   */
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

  /**
   * Realiza o parse da data para o formato DD/MM/AAAA
   * @param dataString
   */
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
   * Trata o evento de mudança da seleção do período, com a 
   * atualização do idPeriodoSelecionado com o novo valor da seleção e
   * recarrega a listagem com base na nova seleção.
   * @param event evento de mudança
   */
  onSelectedChange(event) {      
    this.idPeriodoSelecionado = event.currentTarget.value;
    this.reload();
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

  /**
   * Parse de data no formato Date para um objeto de calendario
   */
  private parseData2Calendario(data: Date) {
       return { date: { year: data.getFullYear(), month: data.getMonth() + 1, 
                                day: data.getDate() } };
  }

}
