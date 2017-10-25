import { Component } from '@angular/core';
import { routerTransition } from '../../router.animations';

@Component({
    selector: 'app-sobre',
    templateUrl: './sobre.component.html',
    animations: [routerTransition()]

})
export class SobreComponent {
    constructor() {

    }
}