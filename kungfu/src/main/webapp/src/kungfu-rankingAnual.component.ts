import { Component, EventEmitter, Input, Output } from '@angular/core';
import { GamificacaoService } from './gamificacao.service';

@Component({
  selector: 'kungfu-rankingAnual',
  templateUrl: 'rankingAnual.html',
})
export class KungfuRankingAnualComponent {

 rankings = [];
 idPeriodo = "";
 @Input() @Output() lstExercicios = [{'id':'2016', 'descricao':'2016'},
	                                 {'id':'2017', 'descricao':'2017'}];
 private idExercicioSelecionado: string;
 
 constructor(private gamificacaoService: GamificacaoService) {
	this.idExercicioSelecionado = "2016";
    gamificacaoService.errorHandler = error =>
      window.alert('Falha na requisição do serviço');
    this.reload();
  }
  
  private reload() {
    this.gamificacaoService.getRankingAnual(this.idExercicioSelecionado)
      .then(rankings => this.rankings = this.parseRankingAnual(rankings));
      
//    this.gamificacaoService.getPeriodoAtual()
//      .then(periodoSelecao => this.idPeriodo = periodoSelecao.idPeriodo);
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
                            'nivel3oPeriodo': this.getNivel(ranking.avatar3oPeriodo)};
          indice++;
      }
      return lstRanking;
  }
  
  private getNivel(avatar) {
      let nivel = avatar.replace("faixa", "Faixa ");
      return nivel;
  }
  
  onSelectedChangeExercicio(idExercicio) {
      this.idExercicioSelecionado = idExercicio;
      this.reload();
  }
  
 
}
