import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
 
export class User {
  constructor(public email: string, 
              public password: string, 
              public perfil:string) { }

}
 
var users = [
  new User('frandberto@hotmail.com','1234', 'admin'),
  new User('rosa@hotmail.com','1234', 'user')
];
 
@Injectable()
export class AuthenticationService {

  loggedUser : User;
 
  constructor(
    private _router: Router){
        this.loggedUser = null;
    }
 
  logout() {
    //localStorage.removeItem("user");
    this.loggedUser = null;
    this._router.navigate(['/login']);
  }
 
  login(user){
    var authenticatedUser = users.find(u => u.email === user.email);
    if (authenticatedUser && authenticatedUser.password === user.password){
      //localStorage.setItem("user", authenticatedUser);
      this.loggedUser = authenticatedUser;
      this._router.navigate(['/home']);      
      return true;
    }
    return false;
 
  }
  
  getLoggedUser() {
    return this.loggedUser;
  }
 
  checkCredentials(){
    //if (localStorage.getItem("user") === null){
    if (this.loggedUser === null){
        this._router.navigate(['/login']);
    }
  } 
}