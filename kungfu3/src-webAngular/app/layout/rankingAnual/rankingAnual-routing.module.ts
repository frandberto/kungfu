import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RankingAnualComponent } from './rankingAnual.component';

const routes: Routes = [
    { path: '', component: RankingAnualComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RankingAnualRoutingModule { }