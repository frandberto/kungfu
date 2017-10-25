import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GerarSenhaRoutingModule } from './gerarSenha-routing.module';
import { GerarSenhaComponent } from './gerarSenha.component';
import { StatModule } from '../../shared';
import { GamificacaoService } from '../../shared/services/gamificacao/gamificacao.service';
import { AuthenticationService } from 'app/shared/services/authentication/authentication.service';
import { MessageBarModule } from '../../shared';
import { PageHeaderModule } from './../../shared';

@NgModule({
    imports: [
        CommonModule,
        GerarSenhaRoutingModule,
        StatModule, MessageBarModule, PageHeaderModule
    ],
    declarations: [
        GerarSenhaComponent
    ],
    providers: [AuthenticationService, GamificacaoService]
})
export class GerarSenhaModule { }
