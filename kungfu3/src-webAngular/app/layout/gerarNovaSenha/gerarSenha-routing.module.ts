import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GerarSenhaComponent } from './gerarSenha.component';

const routes: Routes = [
    { path: '', component: GerarSenhaComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GerarSenhaRoutingModule { }
