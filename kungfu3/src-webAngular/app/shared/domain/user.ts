// Declaração da  entidade User
export class User {
  constructor(public apelido: string,
              public codigo: string,
              public senha: string, 
              public perfil:string,
              public token :string,
              public novaSenha: string) { }

  public isValid() {
      return this.codigo != undefined && this.token != undefined;
  }
}