import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SobreComponent } from './sobre.component';
import { SobreRoutingModule } from './sobre-routing.module';
import { PageHeaderModule } from './../../shared';

@NgModule({
    imports: [
        CommonModule,
        SobreRoutingModule,
        PageHeaderModule
    ],
    declarations: [SobreComponent]
})
export class SobreModule { }