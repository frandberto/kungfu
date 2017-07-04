import { Component } from '@angular/core';
import { GamificacaoService } from './gamificacao.service';

@Component({
  selector: 'kungfu-rankingAnual',
  template: `
    <p>
    <div class="panel panel-primary">
       <div class="panel-heading">Ranking Anual</div>
       <div class="panel-body">
    <div class="container-fluid">
      <table class="table table-striped" style="border: 1px solid black; border-collapse: collapse;">
        <tr>
         <th rowspan=2><div align="center"><b>Participante</b></div></th>
         <th colspan=2><div align="center"><b>1º Trimestre</b></div></th>
         <th colspan=2><div align="center"><b>2º Trimestre</b></div></th>  
         <th colspan=2><div align="center"><b>3º Trimestre</b></div></th>
         <th colspan=2><div align="center"><b>4º Trimestre</b></div></th>
         </tr>
         <tr>
         <td><div align="center"><b>Pontuação</b></div></td>
         <td><div align="center"><b>Nível</b></div></td>
         
         <td><div align="center"><b>Pontuação</b></div></td>
         <td><div align="center"><b>Nível</b></div></td>
        
         <td><div align="center"><b>Pontuação</b></div></td>
         <td><div align="center"><b>Nível</b></div></td>

         <td><div align="center"><b>Pontuação</b></div></td>
         <td><div align="center"><b>Nível</b></div></td>
        </tr>
        <tr *ngFor="let ranking of rankings">
          <td>    
            {{ranking.apelido}}            
          </td>
           <td align="center">
            {{ranking.pontuacao1oPeriodo}}
          </td>
           <td>
            <div align="center">
            <img src="./images/{{ranking.avatar1oPeriodo}}.png" alt="{{ranking.avatar1oPeriodo}}" height="50" width="50">
            <br><b>{{ranking.nivel1oPeriodo}}</b>
            </div>
          </td>  
          
          <td align="center">
            {{ranking.pontuacao2oPeriodo}}
          </td>
           <td>
            <div align="center">
            <img src="./images/{{ranking.avatar2oPeriodo}}.png" alt="{{ranking.avatar2oPeriodo}}" height="50" width="50">
            <br><b>{{ranking.nivel2oPeriodo}}</b>
            </div>
          </td>   
          
          <td align="center">
            {{ranking.pontuacao3oPeriodo}}
          </td>
           <td>
            <div align="center">
            <img src="./images/{{ranking.avatar3oPeriodo}}.png" alt="{{ranking.avatar3oPeriodo}}" height="50" width="50">
            <br><b>{{ranking.nivel3oPeriodo}}</b>
            </div>
          </td>
          
          <td align="center">
            {{ranking.pontuacao4oPeriodo}}
          </td>
           <td>
            <div align="center">
            <img src="./images/{{ranking.avatar4oPeriodo}}.png" alt="{{ranking.avatar4oPeriodo}}" height="50" width="50">
            <br><b>{{ranking.nivel4oPeriodo}}</b>
            </div>
          </td>              
        </tr>
      </table>
    </div>
    </div>
    </div>
  `,
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
