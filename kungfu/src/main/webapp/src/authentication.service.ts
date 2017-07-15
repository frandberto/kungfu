import {Injectable} from '@angular/core';
import { Http, Headers, Response, RequestOptions } from '@angular/http';
import {Router} from '@angular/router';
import {Observable} from 'rxjs/Rx';

// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
 
export class User {
  constructor(public apelido: string,
              public codigo: string,
              public password: string, 
              public perfil:string,
              public token :string) { }
}
 
 
@Injectable()
export class AuthenticationService {

  errorHandler = error => console.error('AuthenticationService error', error);
  private baseUrl = 'http://localhost:8080/kungfu/api/kungfu-leader';

  loggedUser : User;
 
  constructor(
    private http: Http, 
    private _router: Router){
        this.loggedUser = null;
    }
 
  logout() {
    //localStorage.removeItem("user");
    this.loggedUser = null;
    this._router.navigate(['/login']);
  }
 
  login(user){
    let loginUser = { 'codigo' : user.codigo, 'senha' : user.senha };
    let body = JSON.stringify(loginUser);    
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    
    let authenticatedUserJSON = this.http.post(`${this.baseUrl}/login`, body, options)
    .toPromise()
    .then(response => this.criarAuthenticatedUser(response.json()))
    .catch(this.errorHandler);
      
  }
  
  getLoggedUser() {
      return this.loggedUser;
  }
 
  checkCredentials(){
       if (!this.loggedUser.apelido){
           this._router.navigate(['/login']);
       }
  }
  
  criarAuthenticatedUser(authenticatedUserJSON) {
       let authenticatedUser : User;
       authenticatedUser = new User('','','','','');
       authenticatedUser.apelido = authenticatedUserJSON.apelido;
       authenticatedUser.codigo = authenticatedUserJSON.codigo;
       authenticatedUser.token = authenticatedUserJSON.token;
       authenticatedUser.perfil = authenticatedUserJSON.perfil;
       
       this.loggedUser = authenticatedUser;
  }
}



