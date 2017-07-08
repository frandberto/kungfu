
import { Component } from '@angular/core';
import { AuthenticationService } from './authentication.service';
import { KungfuMenuComponent } from './kungfu-menu.component';

@Component({
  selector: 'kungfu-home',
  templateUrl: 'principal.html',
})
export class KungfuHomeComponent { 
    constructor(
        private _service:AuthenticationService){}
 
    ngOnInit(){
       // this._service.checkCredentials();
    }
    
}
