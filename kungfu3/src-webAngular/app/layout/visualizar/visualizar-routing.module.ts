import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VisualizarComponent } from './visualizar.component';

const routes: Routes = [
    { path: '', component: VisualizarComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class VisualizarRoutingModule { }
