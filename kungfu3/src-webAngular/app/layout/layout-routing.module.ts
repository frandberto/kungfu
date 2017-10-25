import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './layout.component';

const routes: Routes = [
    {
        path: '', component: LayoutComponent,
        children: [
            { path: 'home', loadChildren: './home/home.module#HomeModule' },            
            { path: 'editar', loadChildren: './editar/editar.module#EditarModule' },
            { path: 'gerarNovaSenha', loadChildren: './gerarNovaSenha/gerarSenha.module#GerarSenhaModule' },
            { path: 'visualizar', loadChildren: './visualizar/visualizar.module#VisualizarModule' },
            { path: 'ranking', loadChildren: './ranking/ranking.module#RankingModule' },
            { path: 'rankingAnual', loadChildren: './rankingAnual/rankingAnual.module#RankingAnualModule' },
            { path: 'desempenho', loadChildren: './desempenho/desempenho.module#DesempenhoModule' },
            { path: 'sobre', loadChildren: './sobre/sobre.module#SobreModule' },
            { path: 'dashboard', loadChildren: './dashboard/dashboard.module#DashboardModule' },
            { path: 'tables', loadChildren: './tables/tables.module#TablesModule' },
            { path: 'forms', loadChildren: './form/form.module#FormModule' },
            { path: 'bs-element', loadChildren: './bs-element/bs-element.module#BsElementModule' },
            { path: 'grid', loadChildren: './grid/grid.module#GridModule' },
            { path: 'components', loadChildren: './bs-component/bs-component.module#BsComponentModule' },
            { path: 'blank-page', loadChildren: './blank-page/blank-page.module#BlankPageModule' },
            { path: 'charts', loadChildren: './charts/charts.module#ChartsModule' },
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LayoutRoutingModule { }
