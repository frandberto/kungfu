import { Component } from '@angular/core';
import { GamificacaoService } from './gamificacao.service';

@Component({
  selector: 'kungfu-rankingAnual',
  templateUrl: 'rankingAnual.html',
})
export class KungfuRankingAnualComponent {

 rankings = [];
 idPeriodo = "";
 
 constructor(private gamificacaoService: GamificacaoService) {
    gamificacaoService.errorHandler = error =>
      window.alert('Falha na requisição do serviço');
    this.reload();
  }
  
  private reload() {
    this.gamificacaoService.getRankingAnual()
      .then(rankings => this.rankings = this.parseRankingAnual(rankings));
      
    this.gamificacaoService.getPeriodoAtual()
      .then(periodoSelecao => this.idPeriodo = periodoSelecao.idPeriodo);
  }
  
  /*
   * Parse da lista de rankings
   */
  private parseRankingAnual(rankings) {
      let lstRanking = [];
      let indice: number;
      indice = 0;
      for (let ranking of rankings) {
          lstRanking[indice]={'apelido':ranking.apelido, 
                            'idUsuario':ranking.idUsuario,
                            'pontuacao1Periodo':ranking.pontuacao1oPeriodo,
                            'avatar1oPeriodo':ranking.avatar1oPeriodo,
                            'nivel1oPeriodo': this.getNivel(ranking.avatar1oPeriodo),
                            'pontuacao2oPeriodo':ranking.pontuacao2oPeriodo,
                            'avatar2oPeriodo':ranking.avatar2oPeriodo,
                            'nivel2oPeriodo': this.getNivel(ranking.avatar2oPeriodo),
                            'pontuacao3oPeriodo':ranking.pontuacao3oPeriodo,
                            'avatar3oPeriodo':ranking.avatar3oPeriodo,
                            'nivel3oPeriodo': this.getNivel(ranking.avatar3oPeriodo),
                            'pontuacao4Periodo':ranking.pontuacao4oPeriodo,
                            'avatar4oPeriodo':ranking.avatar4oPeriodo,
                            'nivel4oPeriodo': this.getNivel(ranking.avatar4oPeriodo)};
          indice++;
      }
      return lstRanking;
  }
  
  private getNivel(avatar) {
      let nivel = avatar.replace("faixa", "Faixa ");
      return nivel;
  }
  
 
}
