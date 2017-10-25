import { Component, Input, Output } from '@angular/core';
import { routerTransition } from '../../router.animations';
import { Gamificacao } from '../../shared/domain/gamificacao';
import { GamificacaoService } from '../../shared/services/gamificacao/gamificacao.service';
import { AuthenticationService } from 'app/shared/services/authentication/authentication.service';
import { IEntidade } from '../../shared/domain/ientidade';
import { User } from '../../shared/domain/user';
import { Message } from '../../shared/domain/message';


@Component({
    selector: 'app-gerarSenha',
    templateUrl: './gerarSenha.component.html',
    animations: [routerTransition()]
})
export class GerarSenhaComponent {

    @Input() @Output() participantes = [];
    private lstParticipante = [];
    @Input() @Output() lstSelecaoParticipantes: Array<IEntidade>;
    private idParticipante: string = '';
    private novaSenha: string = '';
    private message: Message = new Message();

    constructor(private gamificacaoService: GamificacaoService,
        private authenticationService: AuthenticationService) {
        // Lista de participantes
        this.gamificacaoService.getParticipantes().
            subscribe((lstSelecaoParticipantes: IEntidade[]) => this.lstSelecaoParticipantes = lstSelecaoParticipantes);
    }

    isParticipanteSelected(entidade: IEntidade) {
        if (entidade.id === this.idParticipante) {
            return 'selected';
        }
        return '';
    }

    onSelectedChangeParticipante(event) {
        this.idParticipante = event.currentTarget.value;
    }

    gerarNovaSenha() {
        if (!this.validar()) {
            return;
        }
        let user = new User('', '', '', '', '', '');
        // Passa o id como código
        user.codigo = this.idParticipante;        
        this.authenticationService.gerarNovaSenha(user).
            subscribe(response => this.novaSenha = response.novaSenha);
        this.message.setInfoMessage("Senha gerada com sucesso!");
    }

    private validar(): boolean {
        if (this.idParticipante === '') {
            this.message.setWarningMessage("Escolha um participante!");
            return false;
        }
        return true;
    }

}
