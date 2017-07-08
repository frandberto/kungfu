import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';
import { GamificacaoService } from './gamificacao.service';
import { AuthenticationService } from './authentication.service';
import { AppComponent } from './app.component';
import { KungfuMainPage } from './kungfu-mainPage.component';
import { KungfuEditComponent } from './kungfu-edit.component';
import { KungfuListComponent } from './kungfu-list.component';
import { KungfuSelectComponent } from './kungfu-select.component';
import { KungfuVisualizacaoComponent } from './kungfu-visualizacao.component';
import { KungfuLogoComponent } from './kungfu-logo.component';
import { KungfuRankingComponent } from './kungfu-ranking.component';
import { KungfuRankingAnualComponent } from './kungfu-rankingAnual.component';
import { KungfuSobreComponent } from './kungfu-sobre.component';
import { MyDatePickerModule } from 'mydatepicker';
import { LoginComponent } from './kungfu-login-form.component';
import { KungfuHomeComponent } from './kungfu-home.component';
import { KungfuMenuComponent } from './kungfu-menu.component';



const appRoutes: Routes = [
  { path: 'home', component: KungfuHomeComponent },
  { path: 'logo', component: KungfuLogoComponent },
  { path: 'kungfu', component: LoginComponent },
  { path: 'editar', component: KungfuMainPage },
  { path: 'visualizar', component: KungfuVisualizacaoComponent },
  { path: 'rankingAnual', component: KungfuRankingAnualComponent },
  { path: 'ranking', component: KungfuRankingComponent },
  { path: 'sobre', component: KungfuSobreComponent },
  { path: 'login', component: LoginComponent },
  ];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes), BrowserModule, FormsModule, HttpModule, MyDatePickerModule],
  declarations: [AppComponent, KungfuMainPage, KungfuEditComponent, KungfuListComponent, 
      KungfuVisualizacaoComponent, KungfuSelectComponent, KungfuLogoComponent, KungfuRankingComponent,
      KungfuRankingAnualComponent, KungfuSobreComponent, LoginComponent, KungfuHomeComponent,
      KungfuMenuComponent],
  providers: [GamificacaoService, AuthenticationService],
  bootstrap: [AppComponent]
})
export class AppModule { }
