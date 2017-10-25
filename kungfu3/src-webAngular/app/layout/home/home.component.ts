import { Component, OnInit } from '@angular/core';
import { routerTransition } from '../../router.animations';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss'],
    animations: [routerTransition()]
})
export class HomeComponent implements OnInit {
    public sliders: Array<any> = [];

    constructor() {
        this.sliders.push({
            imagePath: 'assets/images/slider.png',
            label: '',
            text: 'Programa de gamificação para participantes do CoP'
        },
        {
            imagePath: 'assets/images/slider1.png',
            label: '',
            text: 'Programa de gamificação para participantes do CoP'
        },
        {
            imagePath: 'assets/images/slider2.png',
            label: '',
            text: 'Programa de gamificação para participantes do CoP'
        });        
    }

    ngOnInit() {
    }

}
