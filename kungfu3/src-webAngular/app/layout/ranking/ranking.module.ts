import { NgModule, CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RankingComponent } from './ranking.component';
import { RankingRoutingModule } from './ranking-routing.module';
import { PageHeaderModule } from './../../shared';
import { GamificacaoService } from '../../shared/services/gamificacao/gamificacao.service';
import { FormsModule } from '@angular/forms';

@NgModule({
    imports: [
        CommonModule,
        RankingRoutingModule,
        PageHeaderModule,FormsModule
    ],
    declarations: [RankingComponent],
    providers : [GamificacaoService],
    schemas : [ CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA ]
})
export class RankingModule { }