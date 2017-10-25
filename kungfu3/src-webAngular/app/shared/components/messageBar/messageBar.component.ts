import {Component, Input} from '@angular/core';

@Component({
   selector: 'app-messageBar',
   templateUrl: './messageBar.component.html'
})
export class MessageBar {
    /**
     * Barra de mensagens que deve ser localizada
     * no topo do formulário.
     * @param typeMessage Recebe alguns os valores:
     * "success", "info", "warning", "danger",
     * "primary", "secondary", "light", "dark"
     * @param textMessage texto da mensagem
     * @param show exibe ou não
     */

    constructor() {}
    
    @Input() textMessage: string = "";
    @Input() typeMessage: string = "";
    @Input() show: boolean = false;

}
