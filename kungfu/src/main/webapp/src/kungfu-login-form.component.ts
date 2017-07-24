import {Component, ElementRef} from '@angular/core';
import {AuthenticationService, User} from './authentication.service';
import {Router} from '@angular/router';
 
@Component({
    selector: 'login-form',
    templateUrl: 'login.html',
})
 
export class LoginComponent {
 
    public user = new User('','', '', '', '');
    public errorMsg = {message : ''};

    constructor(
        private _service:AuthenticationService) {}
 
    login() {
        let userLogin = {'codigo': this.user.codigo, 'senha': this.user.password };
        	this._service.login(userLogin, this.errorMsg);
    }
}