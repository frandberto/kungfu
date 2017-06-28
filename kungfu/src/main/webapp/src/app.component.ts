import { Component } from '@angular/core';

@Component({
  selector: 'kungfu-app',
  template: `
  
  <nav class="navbar navbar-inverse">  
       <a routerLink="/editar" routerLinkActive="active">Editar Eventos</a>
       <a routerLink="/visualizar" routerLinkActive="active">Visualizar Eventos</a>
</nav>
 <router-outlet></router-outlet>   
  `,
  styleUrls: ['../css/kungfu.css'],
})
export class AppComponent {

} 