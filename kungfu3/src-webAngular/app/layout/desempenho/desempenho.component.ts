import { Component, Input, Output, ViewChild } from '@angular/core';
import { routerTransition } from '../../router.animations';
import { IEntidade } from '../../shared/domain/ientidade';
import { IFrequencia } from '../../shared/domain/ifrequencia';
import { IPontuacao } from '../../shared/domain/ipontuacao';
import { GamificacaoService } from '../../shared/services/gamificacao/gamificacao.service';
import { Observable } from 'rxjs';
import { GaugeComponent } from '@swimlane/ngx-charts';
import { LineChartComponent } from '@swimlane/ngx-charts';

@Component({
    selector: 'app-desempenho',
    templateUrl: './desempenho.component.html',
    animations: [routerTransition()]
})
export class DesempenhoComponent {
    @Input() @Output() lstSelecaoPeriodo: Array<IEntidade>;
    @Input() @Output() lstSelecaoAno: Array<IEntidade>;
    private idPeriodoSelecionado: string;
    private idAnoSelecionado: string;
    public isPolarGenerated: boolean = false;
    public isLineGenerated: boolean = false;
    @ViewChild(LineChartComponent)
    private _lineChart: LineChartComponent;
    @ViewChild(GaugeComponent)
    private _gaugeChart: GaugeComponent;
    // Dados temporários de gráfico de linhas
    private _lineChartData: Array<any> = new Array(2);

    // Gauge
    public gaugeChartData: any[] = this.getDataCharInitial();
    view: any[] = [700, 400];

    // lineChart
    public lineChartData: any[] = this.getDataCharLineInitial();

    // options
    showXAxis = true;
    showYAxis = true;
    gradient = false;
    showLegend = true;
    showXAxisLabel = true;    
    showYAxisLabel = true;

    colorScheme = {
        domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
    };

    // line, area
    autoScale = true;

    constructor(private gamificacaoService: GamificacaoService) {
        this.idPeriodoSelecionado = '';
        this.idAnoSelecionado = '';
        this.gamificacaoService.getPeriodosSelecao().
            subscribe((lstSelecao: IEntidade[]) => this.lstSelecaoPeriodo = lstSelecao);
        this.gamificacaoService.getAnosSelecao().
            subscribe((lstSelecao: IEntidade[]) => this.lstSelecaoAno = lstSelecao);
        
    }

    private obterFrequenciaEventos() {
        if (this.idPeriodoSelecionado != '') {
        let codigoUsuario: string = localStorage.getItem('authenticatedUser.codigo');
        this.gamificacaoService.getFrequenciaEventos(this.idPeriodoSelecionado, codigoUsuario).
            subscribe((lstFrequencias: IFrequencia[]) => this.setDadosGraficoGauge(lstFrequencias));
        }
    }

    private obterPontuacoesExercicio() {
        this._lineChartData[0] = { "name": "Média", "series": new Array<any>() };
        this._lineChartData[1] = { "name": "Minha", "series": new Array<any>() };
        // Atualiza com valores iniciais
        this._lineChart.results = this._lineChartData;

        // Obtém a pontuação média e atualiza o gráfico
        if (this.idAnoSelecionado != '') {
            this.gamificacaoService.getPontuacaoMedia(this.idAnoSelecionado).subscribe((lstPontuacao: IPontuacao[]) =>
                this.setPontuacaoMediaGrafico(lstPontuacao, this._lineChartData));
            // Pontuação do usuário
            this.gamificacaoService.getPontuacaoUsuario(this.getCodigoUsuario(), this.idAnoSelecionado).subscribe((lstPontuacao: IPontuacao[]) =>
            this.setPontuacaoUsuarioGrafico(lstPontuacao, this._lineChartData));
        }
    }

    public setPontuacaoMediaGrafico(lstPontuacao: IPontuacao[], _lineChartData: Array<any>) {
        _lineChartData[0] = { "name": "Média", "series": new Array<any>(lstPontuacao.length) };

        if (lstPontuacao.length > 0) {
            let indice = 0;
            lstPontuacao.forEach(frequencia => this.preencherDadosDetalhePontuacao(frequencia,
                _lineChartData[0].series, indice++));
        }
        // Atualiza gráfico
        this.lineChartData = _lineChartData;
        this._lineChart.results = this.getDataCharLineInitial();
        this._lineChart.results = this.lineChartData;
    }

    public setPontuacaoUsuarioGrafico(lstPontuacao: IPontuacao[], _lineChartData: Array<any>) {
        _lineChartData[1] = { "name": "Minha", "series": new Array<any>(lstPontuacao.length) };

        if (lstPontuacao.length > 0) {
            let indice = 0;
            lstPontuacao.forEach(frequencia => this.preencherDadosDetalhePontuacao(frequencia,
                _lineChartData[1].series, indice++));
        }
        // Atualiza gráfico
        this.lineChartData = _lineChartData;
        this._lineChart.results = this.getDataCharLineInitial();
        this._lineChart.results = this.lineChartData;
    }

    getDataCharLineInitial() : any[] {
        return [{
            "name": "Média", "series": [
                { "name": "", "value": 0 }
            ]
        },
        {
            "name": "Minha", "series": [
                { "name": "", "value": 0 }
            ]
        }];
    }

    getDataCharInitial() : any[] {
        return [{ "name": "", "value": 0 }];
    }

    preencherDadosDetalhePontuacao(pontuacao: IPontuacao, serie: any[], indice: number) {
        let valor = Number.parseInt(pontuacao.pontuacao);
        serie[indice] = this.getDataCharInitial();
        // Seta o valor
        serie[indice].value = valor;
        // Seta o label
        serie[indice].name = pontuacao.descricao;
    }

    preencherLabels(pontuacao: IPontuacao, _lineChartLabels: Array<any>, indice: number) {
        _lineChartLabels[indice] = pontuacao.descricao;
    }

    public setDadosGraficoGauge(lstFrequencias: IFrequencia[]) {
        let indice = 0;
        if (lstFrequencias.length > 0) {
            let _gaugeChartData : any[] = new Array<any>(lstFrequencias.length);
            // Preenche os dados temporários
            lstFrequencias.forEach(frequencia => this.preencherDadosGauge(frequencia, _gaugeChartData, indice++));
            this.gaugeChartData = _gaugeChartData;
            // Atualiza o gráfico
            this._gaugeChart.results = this.gaugeChartData;            
        }
    }

    public preencherDadosGauge(frequencia : IFrequencia, _gaugeChartData : any[], 
        indice: number) {
        let valor = Number.parseInt(frequencia.quantidade);     
        _gaugeChartData[indice] = this.getDataCharInitial();   
        // Seta o valor
        _gaugeChartData[indice].value = valor;
        // Seta o label
        _gaugeChartData[indice].name = frequencia.nomeEvento;        
    }

    public polarHidden() {
        return this.isPolarGenerated === false;
    }

    public showLineChart() {
        return this.isLineGenerated === true;
    }

    /**
   * Verifica se a entidade está selecionada
   * @param entidade
   */
    public isSelected(entidade: IEntidade) {
        if (entidade.id === this.idPeriodoSelecionado) {
            return 'selected';
        }
        return '';
    }

    public isSelectedAno(entidade: IEntidade) {
        if (entidade.id === this.idAnoSelecionado) {
            return 'selected';
        }
        return '';
    }

    /**
     * Trata o evento de mudança da seleção do período, com a 
     * atualização do idPeriodoSelecionado com o novo valor da seleção e
     * recarrega a listagem com base na nova seleção.
     * @param event evento de mudança
     */
    public onSelectedChange(event) {
        this.idPeriodoSelecionado = event.currentTarget.value;
        this.obterFrequenciaEventos();
    }

    /**
     * Trata o evento de mudança da seleção do período, com a 
     * atualização do idPeriodoSelecionado com o novo valor da seleção e
     * recarrega a listagem com base na nova seleção.
     * @param event evento de mudança
     */
    public onSelectedAnoChange(event) {
        this.idAnoSelecionado = event.currentTarget.value;
        this.obterPontuacoesExercicio();
    }

    /**
     * Retorna o Observable<IEntidade> dos perídos de seleção
     */
    public getPeriodosSelecao(): Observable<IEntidade[]> {
        return this.gamificacaoService.getPeriodosSelecao();
    }

    // events
    public chartClicked(e: any): void {
        // console.log(e);
    }

    public chartHovered(e: any): void {
        // console.log(e);
    }

    public getApelidoUsuario() {
        return localStorage.getItem('authenticatedUser.apelido');
    }

    public getCodigoUsuario() {
        return localStorage.getItem('authenticatedUser.codigo');
    }
}