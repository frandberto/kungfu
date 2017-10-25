import { NgModule, CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RankingAnualComponent } from './rankingAnual.component';
import { RankingAnualRoutingModule } from './rankingAnual-routing.module';
import { PageHeaderModule } from './../../shared';
import { GamificacaoService } from '../../shared/services/gamificacao/gamificacao.service';
import { FormsModule } from '@angular/forms';

@NgModule({
    imports: [
        CommonModule,
        RankingAnualRoutingModule,
        PageHeaderModule,FormsModule
    ],
    declarations: [RankingAnualComponent],
    providers : [GamificacaoService],
    schemas : [ CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA ]
})
export class RankingAnualModule { }