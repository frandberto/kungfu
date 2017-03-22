import {Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
    selector : 'kungfu-select',
    template : `
            <select class="selectpicker" [ngModel]="selected" (ngModel)="selectedOnChange">                
                   <option *ngFor='let entidade of entidades' [value]='entidade.id'>
                     {{entidade.descricao}}
                   </option>             
            </select>
    `
})
export class KungfuSelectComponent {
    @Input() selected: boolean;
    @Output() selectedChange = new EventEmitter(this.selected);

    /* Array de entidades genericas com id e descricao */
    @Input()
    entidades = [];
    
}