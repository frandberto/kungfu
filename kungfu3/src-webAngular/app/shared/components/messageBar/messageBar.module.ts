import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MessageBar } from './messageBar.component';
import { NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        NgbAlertModule.forRoot()
    ],
    declarations: [MessageBar],
    exports: [MessageBar]
})
export class MessageBarModule { }
