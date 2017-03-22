import { Component, EventEmitter, Input, Output } from '@angular/core';
import { KungfuSelectComponent } from './kungfu-select.component';

@Component({
  selector: 'kungfu-edit',
  template: `
    <p>
    <div class="panel panel-primary">
      <div class="panel-body">
      <div class="row">
         <div class="col-sm-2">Participante</div>
         <div class="col-sm-1">Data</div>
         <div class="col-sm-6">Evento</div>
         <div class="col-sm-1">Pontuação</div>
         <div class="col-sm-2">Observação</div>
       </div>
        <div class="row">
          <div class="col-sm-2"><kungfu-select [(entidades)]="this.participantes"></kungfu-select></div>
          <div class="col-sm-1"><input type="text" [(ngModel)]="gamificacao.dataRegistro"
          placeholder="Data"></div>
         <div class="col-sm-6"><kungfu-select [(entidades)]="this.eventos"></kungfu-select></div>
         <div class="col-sm-1"><input type="text" [(ngModel)]="gamificacao.pontuacao"
          placeholder="0" class="disabled"></div> 
         <div class="col-sm-2"><textarea [(ngModel)]="gamificacao.observacao"
          placeholder="Observação"></textarea></div>
       </div>
      
       <div class="row">
         <div class="col-sm-5"></div>
         <div class="col-sm-1">
            <button (click)="onSave()" class="btn btn-primary" title="Salvar">
             <span class="glyphicon glyphicon-ok">Salvar</span>
            </button>
         </div> 
         <div class="col-sm-1">
              <button (click)="onClear()" class="btn btn-warning" title="Limpar">
                 <span class="glyphicon glyphicon-remove">Limpar</span>
              </button>
         </div>
         <div class="col-sm-5"></div>
        </div>           
      </div>
    </div>
  `,
})
export class KungfuEditComponent {

  @Input() gamificacao = {};
  @Input() @Output() eventos = {};
  @Input() @Output() participantes = {};
  @Output() clear = new EventEmitter();
  @Output() save = new EventEmitter();

  onClear() {
    this.clear.emit();
  }

  onSave() {
    this.save.emit(this.gamificacao);
  }

}
