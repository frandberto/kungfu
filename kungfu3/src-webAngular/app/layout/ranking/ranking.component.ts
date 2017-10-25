import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Gamificacao} from '../../shared/domain/gamificacao';
import { GamificacaoService} from '../../shared/services/gamificacao/gamificacao.service';
import { routerTransition } from '../../router.animations';
import { IEntidade } from '../../shared/domain/ientidade';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html',
  animations: [routerTransition()]
})
export class RankingComponent {

 private rankings = [];
 @Input() @Output() lstSelecaoPeriodo : Array<IEntidade>;
 private idPeriodoSelecionado: string;
 
 constructor(private gamificacaoService: GamificacaoService) {
    gamificacaoService.errorHandler = error =>
      window.alert('Falha na requisição do serviço');

    // Recupera lista de seleção de períodos
    this.gamificacaoService.getPeriodosSelecao().
    subscribe((lstSelecao:IEntidade[]) => this.lstSelecaoPeriodo = lstSelecao);

    this.reload();
  }
  
  private reload() {
    this.gamificacaoService.getRankingsPorPeriodo(this.idPeriodoSelecionado).
    subscribe(rankings => this.rankings = this.parseRanking(rankings));
  }

  /**
   * Retorna o Observable<IEntidade> dos perídos de seleção
   */
  getPeriodosSelecao(): Observable<IEntidade[]> {
        return this.gamificacaoService.getPeriodosSelecao();
  }
  
  // Realiza a consulta após mudar a seleção de períodos
  onSelectedChange(event) {
      this.idPeriodoSelecionado = event.currentTarget.value;
      this.reload();
  }
  
  private obterPeriodo(idPeriodo) {
	  for (let periodo of this.lstSelecaoPeriodo) {
		  if (periodo.id == idPeriodo) {
			  return periodo;
		  }
	  }
	  return null;
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
  
  /*
   * Parse da lista de rankings
   */
  private parseRanking(rankings) {
      let lstRanking = [];
      let indice: number;
      indice = 0;
      for (let ranking of rankings) {
          lstRanking[indice]={'apelido':ranking.apelido, 
                            'idUsuario':ranking.idUsuario,
                            'idRaking':ranking.idRaking, 
                            'pontuacao':ranking.pontuacao,
                            'avatar':ranking.avatar,
                            'nivel': this.getNivel(ranking.avatar),
                            'cor' : this.getCorFaixa(ranking.avatar)};
          indice++;
      }
      return lstRanking;
  }
  
  /*
   * Parse da lista de periodos para o formato de seleção
   */
  private parsePeriodo(periodos) {
      let lstSelecaoPeriodo = [];
      let indice: number;
      indice = 0;
      for (let periodo of periodos) {
    	  lstSelecaoPeriodo[indice]={'id':periodo.idPeriodo, 
                            'descricao':periodo.descricao};
          indice++;
      }
      return lstSelecaoPeriodo;
  }
  
  private getNivel(avatar) {
      let nivel = avatar.replace("faixa", "Faixa ");
      return nivel;
  }
  
  public getCorFaixa(avatar) {
	  if (avatar == 'faixaPreta') {
		  return "#000000";
	  }
	  
	  if (avatar == 'faixaMarrom') {
		  return "#7A6060";
	  }
	  
	  if (avatar == 'faixaRoxa') {
		  return "#9049A2";
	  }
	  
	  if (avatar == 'faixaAzul') {
		  return "#0000FF";
	  }
	  
	  // Default
	  return "#fffff";
   }
 
}