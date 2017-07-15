import {Component, ElementRef} from '@angular/core';
import {AuthenticationService, User} from './authentication.service';
import {Router} from '@angular/router';
 
@Component({
    selector: 'login-form',
    templateUrl: 'login.html',
})
 
export class LoginComponent {
 
    public user = new User('','', '', '', '');
    public errorMsg = '';
 
    constructor(
        private _service:AuthenticationService, private _router: Router) {}
 
    login() {
        let userLogin = {'codigo': this.user.codigo, 'senha': this.user.password };
        this._service.login(userLogin);
        let loggedUser = this._service.getLoggedUser();
        if(!loggedUser.apelido){
            this.errorMsg = 'Falha no Login! Verifique o cpf ou a senha.';
        } else {
          this._router.navigate(['/home']);
        }
    }
}