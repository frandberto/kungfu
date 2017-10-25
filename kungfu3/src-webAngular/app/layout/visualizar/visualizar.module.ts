import { NgModule, CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VisualizarComponent } from './visualizar.component';
import { VisualizarRoutingModule } from './visualizar-routing.module';
import { PageHeaderModule } from './../../shared';
//import { ListComponent } from './../../shared/components/list/list.component';
//import { SelectComponent } from './../../shared/components/select/select.component';
import { GamificacaoService } from '../../shared/services/gamificacao/gamificacao.service';
import { FormsModule } from '@angular/forms';

@NgModule({
    imports: [
        CommonModule,
        VisualizarRoutingModule,
        PageHeaderModule,FormsModule
    ],
    declarations: [VisualizarComponent],
    providers : [GamificacaoService],
    schemas : [ CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA ]
})
export class VisualizarModule { }
