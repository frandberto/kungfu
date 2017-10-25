import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Gamificacao } from '../../shared/domain/gamificacao';
import { GamificacaoService } from '../../shared/services/gamificacao/gamificacao.service';
import { routerTransition } from '../../router.animations';
import { IEntidade } from '../../shared/domain/ientidade';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-rankingAnual',
    templateUrl: './rankingAnual.component.html',
    animations: [routerTransition()]
})
export class RankingAnualComponent {

    rankings = [];
    idPeriodo = "";
    @Input() @Output() lstExercicios = [{ 'id': '2016', 'descricao': '2016' },
    { 'id': '2017', 'descricao': '2017' }];
    private idExercicioSelecionado: string;

    constructor(private gamificacaoService: GamificacaoService) {
        this.idExercicioSelecionado = "2016";
        this.reload();
    }

    private reload() {
        this.gamificacaoService.getHistoricoRanking()
            .subscribe(rankings => this.rankings = this.parseHistoricoRanking(rankings));
    }

    parseHistoricoRanking(rankings): any[] {
        let lstRanking = [];
        let indice;
        indice = 0;
        for (let ranking of rankings) {
            lstRanking[indice] = {
                'apelido': ranking.apelido,
                'idUsuario': ranking.idUsuario,
                'qtdFaixaBranca': ranking.qtdFaixaBranca,
                'qtdFaixaAzul': ranking.qtdFaixaAzul,
                'qtdFaixaRoxa': ranking.qtdFaixaRoxa,
                'qtdFaixaMarrom': ranking.qtdFaixaMarrom,
                'qtdFaixaPreta': ranking.qtdFaixaPreta
            };
            indice++;
        }
        return lstRanking;
    };

    /**
     * Verifica se a entidade está selecionada
     * @param entidade
     */
    isSelected(entidade: IEntidade) {
        if (entidade.id === this.idExercicioSelecionado) {
            return 'selected';
        }
        return '';
    }

    private getNivel(avatar) {
        let nivel = avatar.replace("faixa", "Faixa ");
        return nivel;
    }

    onSelectedChangeExercicio(event) {
        this.idExercicioSelecionado = event.currentTarget.value;
        this.reload();
    }
}