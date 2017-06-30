import { Component } from '@angular/core';
import { GamificacaoService } from './gamificacao.service';

@Component({
  selector: 'kungfu-ranking',
  template: `
    <p>
    <div class="panel panel-primary">
       <div class="panel-heading">Ranking - Período: {{this.periodo}}</div>
       <div class="panel-body">
    <div class="container-fluid">
      <table class="table table-striped">
        <tr>
         <th>Participante</th>
         <th><div align="center">Pontuação</div></th>
         <th><div align="center">Nível</div></th>
        </tr>
        <tr *ngFor="let ranking of rankings">
          <td>    
            {{ranking.apelido}}            
          </td>
           <td align="center">
            {{ranking.pontuacao}}
          </td>
           <td>
            <div align="center">
            <img src="./images/{{ranking.avatar}}.png" alt="{{ranking.avatar}}" height="50" width="50">
            <br><b>{{ranking.nivel}}</b>
            </div>
          </td>          
        </tr>
      </table>
    </div>
    </div>
    </div>
  `,
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
