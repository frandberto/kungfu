import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { routerTransition } from '../router.animations';
import { AuthenticationService } from 'app/shared/services/authentication/authentication.service';
import { User } from 'app/shared/domain/user';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
    animations: [routerTransition()]
})
export class LoginComponent implements OnInit {
    retorno : any = {'sucesso': false, 'msgErro': ''};
    codigo: string;
    senha: string;
    novaSenha: string = '';
    confirmacaoSenha: string = '';
    public trocarSenha: boolean = true;
    public msgErro: string = '';

    constructor(public router: Router, public authenticationService: AuthenticationService) {
    }

    ngOnInit() {
        this.iniciar();
    }

    /**
     * Inicialização
     */
    iniciar() {
        localStorage.clear;
        this.limparMensagemErro();
    }

    onLoggedin() {
        this.iniciar();
        if (this.validarPrenchimento(this.codigo, this.senha)) {
            let user = new User('', '', '', '', '', '');
            user.codigo = this.codigo;
            user.senha = this.senha;
            // Se preencher novaSenha ou confirmacaoSenha então validaTrocaSenha
            if (this.novaSenha != '' || this.confirmacaoSenha != '') {
                if (this.validarTrocaSenha(this.novaSenha, this.confirmacaoSenha)) {
                    user.novaSenha = this.novaSenha;
                } else { // Ocorreu falha na validação
                    return;
                }
            }
            this.authenticationService.login(user).
                subscribe(response => { 
                    if (response.sucesso === false) {
                       this.msgErro = response.msgErro;
                    }
                } );
        }
    }

    private validarTrocaSenha(novaSenha: string, confirmacaoSenha: string): boolean {
        return this.validarPreenchimentoNovaSenha(novaSenha, confirmacaoSenha) &&
            this.validarSenhaIguais(novaSenha, confirmacaoSenha) &&
            this.validarTamanhoSenha(novaSenha);

    }

    private validarPrenchimento(codigo: string, senha: string): boolean {
        if ((codigo == '' || codigo == null) ||
            (senha == '' || senha == null)) {
            this.msgErro = 'Preencha a matrícula e a senha';
            return false;
        }
        return true;
    }

    public escondeMsgErro() {
        return this.msgErro.length == 0;
    }

    private validarSenhaIguais(senhaNova: string,
        senhaConfirmacao: string) {
        if (senhaNova === senhaConfirmacao) {
            return true;
        } else {
            this.msgErro = 'A senha confirmada está diferente da nova senha!';
            return false;
        }
    }

    private validarTamanhoSenha(senhaNova: string) {
        if (senhaNova.length >= 6) {
            return true;
        } else {
            this.msgErro = 'Tamanho de senha inválido! Deve ter pelo menos 6 caracteres';
            return false;
        }
    }

    validarPreenchimentoNovaSenha(novaSenha: string, confirmacaoSenha: string) {
        if (novaSenha != '' && confirmacaoSenha == '') {
            this.msgErro = "Preencha a senha de confirmação";
            return false;
        }
        if (novaSenha == '' && confirmacaoSenha != '') {
            this.msgErro = "Preencha a nova senha";
            return false;
        }
        return true;
    }

    gerarNovaSenha() {
        let codigoUsuario = localStorage.getItem('authenticatedUser.codigo');
        //let codigoUsuario = '03035328';
        let user = new User('', '', '', '', '', '');
        user.codigo = codigoUsuario;
        this.authenticationService.gerarNovaSenha(user).
        subscribe(response => this.novaSenha = response.novaSenha);
    }

    private isUserLogged() {
        return localStorage.getItem('isLoggedin') === 'true';
    }

    private trocaSenha() {
        this.limparNovaSenha();
        this.trocarSenha = !this.trocarSenha;
    }

    private limparMensagemErro() {
        this.msgErro = '';
    }

    private limparNovaSenha() {
        this.novaSenha = '';
        this.confirmacaoSenha = '';
    }

}
