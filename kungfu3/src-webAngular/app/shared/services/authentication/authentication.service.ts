import {Injectable} from '@angular/core';
import { Http, Headers, Response, RequestOptions } from '@angular/http';
import {Router} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import {User} from 'app/shared/domain/user';

// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
  
@Injectable()
export class AuthenticationService {
  // TODO colocar em arquivo de configuracao ou propriedades
  private server = 'localhost:8080';
  private baseUrl = `http://${this.server}/kungfuService/api/kungfu-leader`;

  loggedUser : User;
 
  constructor(
    private http: Http, 
    private router: Router){
        this.loggedUser = null;
    }
 
  logout() {
    //localStorage.removeItem("user");
    this.loggedUser = null;
    this.router.navigate(['/kungfu']);
  }
  
  login(user: User) : Observable <any>{
	    let loginUser = { 'codigo' : user.codigo, 'senha' : user.senha, 'novaSenha' : user.novaSenha };
	    let body = JSON.stringify(loginUser);    
	    let headers = new Headers({ 'Content-Type': 'application/json' });
	    let options = new RequestOptions({ headers: headers });
	    return this.http.post(`${this.baseUrl}/login`, body, options)
	    .map(response => { return <any> this.criarAuthenticatedUser(response.json());});
	    //.catch(error => this.tratarLoginError(error, errorMsgObject));
        //this.criarAuthenticatedUser({'apelido': 'Frand', 'codigo': '03035328', 'token': 'XPTO', 'perfil': '1'})
  }
  
  getLoggedUser() {
      return this.loggedUser;
  }
 
  criarAuthenticatedUser(authenticatedUserJSON): any {
      let authenticatedUser : User;
      authenticatedUser = new User('','','','','', '');
      authenticatedUser.apelido = authenticatedUserJSON.apelido;
      authenticatedUser.codigo = authenticatedUserJSON.codigo;
      authenticatedUser.token = authenticatedUserJSON.token;
      authenticatedUser.perfil = authenticatedUserJSON.perfil;
      if (authenticatedUser.isValid()) {
        localStorage.setItem('isLoggedin', 'true');
        localStorage.setItem('authenticatedUser.apelido', authenticatedUser.apelido);
        localStorage.setItem('authenticatedUser.codigo', authenticatedUser.codigo);
        localStorage.setItem('authenticatedUser.token', authenticatedUser.token);
        localStorage.setItem('authenticatedUser.perfil', authenticatedUser.perfil);          
        this.router.navigate(['/home']);
        return { 'sucesso': true, 'msgErro': ''};
      } else {          
          localStorage.setItem('isLoggedin', 'false');
          return { 'sucesso': false, 'msgErro': 'Falha do Login! Verifique matrícula e senha.'};
      }
 }

  gerarNovaSenha(user: User) : Observable <User> {
    let loginUser = { 'codigo' : user.codigo, 'senha' : '', 'novaSenha' : '' };
    let body = JSON.stringify(loginUser);    
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${this.baseUrl}/gerarNovaSenha`, body, options).
           map(response => {return <User> response.json();});
  }

  tratarLoginError(error) {	 
      alert(error);
      localStorage.setItem('isLoggedin', 'false');
  }

  private getNovaSenha(user: User) : string {
      return user.novaSenha;
  }
  
}