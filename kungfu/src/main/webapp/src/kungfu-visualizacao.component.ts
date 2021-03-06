import { Component, EventEmitter, Input, Output } from '@angular/core';
import { GamificacaoService } from './gamificacao.service';
import { Gamificacao } from './gamificacao.entidade';

@Component({
  selector: 'kungfu-visualizacao-app',
  templateUrl: 'visualizacaoEventos.html',
})
export class KungfuVisualizacaoComponent {

  gamificacoes : Gamificacao[];
  periodo = '';
  @Input() @Output() lstPeriodo = [];
 private idPeriodoSelecionado: string;

  constructor(private gamificacaoService: GamificacaoService) {
    gamificacaoService.errorHandler = error =>
      window.alert('Falha na requisição do serviço');
    this.reload();
  }
  
  private reload() {   
      
    this.gamificacaoService.getPeriodos()
     .then(periodos => this.lstPeriodo = this.parsePeriodo(periodos));
     
     this.gamificacaoService.getPeriodoAtual()
      .then(periodoSelecao => this.idPeriodoSelecionado = periodoSelecao.id);
     
     this.gamificacaoService.getGamificacoesPorPerido(this.idPeriodoSelecionado)
      .then(gamificacoes => this.gamificacoes = this.parseGamificacao(gamificacoes));
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
           gamificacao.pontuacao = Number.parseFloat(gamificacaoJson.pontuacao); 
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
  
  onSelectedChangePeriodo(idPeriodo) {
      console.info("Periodo selecionado", idPeriodo);
      this.idPeriodoSelecionado = idPeriodo;
      this.gamificacaoService.getGamificacoesPorPerido(this.idPeriodoSelecionado)
      .then(gamificacoes => this.gamificacoes = this.parseGamificacao(gamificacoes));
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
  
  
}