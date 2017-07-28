import {Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
    selector : 'kungfu-select',
    template : `
            <select [ngModel]="selected" (ngModelChange)="onSelectedChange($event)" style="color: black;">
                   <option *ngFor='let entidade of entidades' [value]='entidade.id' >
                     {{entidade.descricao}}
                   </option>             
            </select>
    `
})
export class KungfuSelectComponent {
    @Input() selected: string;
    @Output() selectedChange = new EventEmitter();
    

    /* Array de entidades genericas com id e descricao */
    @Input()
    entidades = [];

    onSelectedChange(selected) {
        this.selected = selected;
        this.selectedChange.emit(this.selected);
    }
}