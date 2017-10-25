import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DesempenhoComponent } from './desempenho.component';
import { DesempenhoRoutingModule } from './desempenho-routing.module';
import { PageHeaderModule } from './../../shared';
//import { ChartsModule as Ng2Charts } from 'ng2-charts';
import {PolarChartModule} from '@swimlane/ngx-charts';
import { NgxChartsModule} from '@swimlane/ngx-charts';
import { FormsModule } from '@angular/forms';
import { GamificacaoService} from '../../shared/services/gamificacao/gamificacao.service';

@NgModule({
    imports: [
        CommonModule,
        DesempenhoRoutingModule,
        PageHeaderModule, 
        NgxChartsModule, FormsModule
    ],
    declarations: [DesempenhoComponent],
    providers: [GamificacaoService]
})
export class DesempenhoModule { 

       constructor() {
           
       }


}