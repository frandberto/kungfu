import { NgModule, CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EditarComponent } from './editar.component';
import { EditarRoutingModule } from './editar-routing.module';
import { PageHeaderModule } from './../../shared';
//import { ListComponent } from './../../shared/components/list/list.component';
//import { SelectComponent } from './../../shared/components/select/select.component';
import { GamificacaoService } from '../../shared/services/gamificacao/gamificacao.service';
import { FormsModule } from '@angular/forms';
import { MyDatePickerModule } from 'mydatepicker';
import { MessageBarModule } from '../../shared';

@NgModule({
    imports: [
        CommonModule,
        EditarRoutingModule,
        PageHeaderModule,FormsModule, 
        MyDatePickerModule, MessageBarModule
    ],
    declarations: [EditarComponent],
    providers : [GamificacaoService],
    schemas : [ CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA ]
})
export class EditarModule { }
