
import { Component } from '@angular/core';
import { AuthenticationService, User } from './authentication.service';
import { KungfuMenuComponent } from './kungfu-menu.component';

@Component({
  selector: 'kungfu-home',
  templateUrl: 'home.html',
})
export class KungfuHomeComponent { 
    constructor(
        private _service:AuthenticationService){}
        
    private userLogged : User;
 
    ngOnInit(){
       this.userLogged = this._service.getLoggedUser();
    }
    
}
