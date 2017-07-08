import { Component } from '@angular/core';
import { GamificacaoService } from './gamificacao.service';

@Component({
  selector: 'kungfu-ranking',
  templateUrl: 'rankingPeriodo.html',
})
export class KungfuRankingComponent {

 rankings = [];
 periodo = "";
 
 constructor(private gamificacaoService: GamificacaoService) {
    gamificacaoService.errorHandler = error =>
      window.alert('Falha na requisição do serviço');
    this.reload();
  }
  
  private reload() {
    this.gamificacaoService.getRankings()
      .then(rankings => this.rankings = this.parseRanking(rankings));
      
    this.gamificacaoService.getPeriodoAtual()
      .then(periodoSelecao => this.periodo = periodoSelecao.descricao);
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
                            'nivel': this.getNivel(ranking.avatar)};
          indice++;
      }
      return lstRanking;
  }
  
  private getNivel(avatar) {
      let nivel = avatar.replace("faixa", "Faixa ");
      return nivel;
  }
  
 
}
