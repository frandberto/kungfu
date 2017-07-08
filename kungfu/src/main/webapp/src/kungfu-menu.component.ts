import { Component } from '@angular/core';
import {AuthenticationService, User} from './authentication.service'

@Component({
  selector: 'kungfu-menu',
  template: ` 
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar" *ngIf='!isPerfilUsuario()'></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>  
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                     
      </button>
      <div>
      <img class="navbar-header" src="../images/imagem-kungfu2.png" style="width:30%" alt="Image">
      <a class="navbar-header" href="kungfu">KungFu Leader</a>
      </div>
    </div>
    
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li><a routerLink="/home" routerLinkActive="active">Home</a></li>
        
        <li *ngIf='!isPerfilUsuario()'><a routerLink="/editar" routerLinkActive="active">Editar</a></li>
        <li><a routerLink="/visualizar" routerLinkActive="active">Visualizar</a></li>
        <li><a routerLink="/ranking" routerLinkActive="active">Ranking</a></li>
        <li><a routerLink="/rankingAnual" routerLinkActive="active">Ranking Anual</a></li>
        <li><a routerLink="/sobre" routerLinkActive="active">Sobre</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a (click)="logout()"><span class="glyphicon glyphicon-log-in"></span> Sair</a></li>
      </ul>
    </div>
  </div>  
</nav>
  `
})
export class KungfuMenuComponent {
    constructor(
        private _service:AuthenticationService) {}
        
    public isPerfilUsuario() {
        return this._service.getLoggedUser().perfil === 'user';
    }
    
    logout() {
        this._service.logout();
    }
    
    ngOnInit(){
        this._service.checkCredentials();
    }

}
