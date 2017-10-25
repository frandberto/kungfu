/**
 * Definição de interface para Gamificacao
 */
export class Gamificacao {
    idGamificacao : string;   
    idEvento : string;
    nomeEvento : string;
    idUsuario : string;
    apelido : string;
    dataRegistro : Date; 
    pontuacao : string;
    observacao : string;
    dataCalendario: Object;

    constructor() {
        let dataAtual = new Date();
        this.dataCalendario = { date: { year: dataAtual.getFullYear(), 
                                month: dataAtual.getMonth() + 1, 
                                day: dataAtual.getDate() } };
        this.idGamificacao = "";    
        this.idEvento = "";
        this.idUsuario = ""; 
        this.dataRegistro = new Date();   
        this.pontuacao = "0,0";
        this.observacao = "";
    }
}