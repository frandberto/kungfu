import { Component, EventEmitter, Input, Output } from '@angular/core';
import { GamificacaoService } from './gamificacao.service';

@Component({
  selector: 'kungfu-ranking',
  templateUrl: 'rankingPeriodo.html',
})
export class KungfuRankingComponent {

 private rankings = [];
 @Input() @Output() lstPeriodo = [];
 private idPeriodoSelecionado: string;
 
 constructor(private gamificacaoService: GamificacaoService) {
    gamificacaoService.errorHandler = error =>
      window.alert('Falha na requisição do serviço');
    this.reload();
  }
  
  private reload() {
    this.gamificacaoService.getRankingsPorPeriodo(this.idPeriodoSelecionado)
      .then(rankings => this.rankings = this.parseRanking(rankings));
    
    this.gamificacaoService.getPeriodos()
    .then(periodos => this.lstPeriodo = this.parsePeriodo(periodos));
      
//    this.gamificacaoService.getPeriodoAtual()
//      .then(periodoSelecao => this.periodo = periodoSelecao.descricao);
  }
  
  onSelectedChangePeriodo(idPeriodo) {
      console.info("Periodo selecionado", idPeriodo);
      this.idPeriodoSelecionado = idPeriodo;
      this.gamificacaoService.getRankingsPorPeriodo(this.idPeriodoSelecionado)
      .then(rankings => this.rankings = this.parseRanking(rankings));
  }
  
  private obterPeriodo(idPeriodo) {
	  for (let periodo of this.lstPeriodo) {
		  if (periodo.id == idPeriodo) {
			  return periodo;
		  }
	  }
	  return null;
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
      let lstPeriodo = [];
      let indice: number;
      indice = 0;
      for (let periodo of periodos) {
    	  lstPeriodo[indice]={'id':periodo.idPeriodo, 
                            'descricao':periodo.descricao};
          indice++;
      }
      return lstPeriodo;
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
