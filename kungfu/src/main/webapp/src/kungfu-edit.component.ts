import { Component, EventEmitter, Input, Output } from '@angular/core';
import { KungfuSelectComponent } from './kungfu-select.component';

@Component({
  selector: 'kungfu-edit',
  template: `
    <p>
    <div class="panel panel-primary">
       <div class="panel-heading">Registro de Eventos</div>
       <div class="panel-body">
       <div class="form-group">
        <label for="participante">Participante:</label>
        <kungfu-select [selected]="this.gamificacao.idUsuario" 
             (selectedChange)="onSelectedChangeParticipante($event)"
             [entidades]="this.participantes" ></kungfu-select>
      </div>
      <div class="form-group">
        <label for="data">Data:</label>
        <input type="date" [(ngModel)]="gamificacao.dataRegistro"
          placeholder="Data" id="data">
      </div>
      <div class="form-group">
        <label for="evento">Evento:</label>
        <kungfu-select [selected]="this.gamificacao.idEvento" 
              [entidades]="this.eventos"
              (selectedChange)="onSelectedChangeEvento($event)">
        </kungfu-select>
      </div>
      <div class="form-group">
        <label> Pontuação:</label>
        <label>{{gamificacao.pontuacao}}</label>
      </div>
      <div class="form-group">
        <textarea [(ngModel)]="gamificacao.observacao" rows="2" cols="100"
          placeholder="Observação" id="observacao"></textarea>
      </div>        
       <!-- Botoes de Controle -->
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

  @Input() gamificacao = {
    "idGamificacao":"", 
    "dataRegistro":"",
    "idUsuario":"", 
    "idEvento":"", 
    "pontuacao":"0",
    "observacao":""};
  @Input() @Output() eventos = [];
  @Input() @Output() participantes = [];
  @Output() clear = new EventEmitter();
  @Output() save = new EventEmitter();
  idEventoSelecionado: string;
  idParticipanteSelecionado: string;

  onClear() {
    this.clear.emit();
  }

  onSave() {
    this.save.emit(this.gamificacao);
  }

  onSelectedChangeEvento(idEvento) {
       console.info("Evento selecionado", idEvento);
       this.gamificacao.idEvento = idEvento;
       let evento = this.obterEvento(idEvento);
       if (evento != null) {
         this.gamificacao.pontuacao = evento.pontuacao;
       }    
  }

  onSelectedChangeParticipante(evento) {
       console.info("Participante selecionado", evento);
       this.gamificacao.idUsuario = evento;          
  }

  private obterEvento(idEvento) {
    for (let evento of this.eventos) {
      if (evento.id == idEvento) {
        return evento;
      }
    }
    return null;
  }

}
