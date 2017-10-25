import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DesempenhoComponent } from './desempenho.component';

const routes: Routes = [
    { path: '', component: DesempenhoComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DesempenhoRoutingModule { }