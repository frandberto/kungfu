import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'kungfu-list',
  template: `
    <div class="container-fluid">
      <table class="table table-striped">
        <tr>
         <th>Participante</th>
         <th>Data</th>
         <th>Evento</th>
         <th>Pontuação</th>
         <th>Observação</th>
         <th>Ações</th>
        </tr>
        <tr *ngFor="let gamificacao of gamificacoes">
          <input type="hidden" value="{{gamificacao.idGamificacao}}">
          <td>
            {{gamificacao.apelido}}
          </td>
           <td>
            {{gamificacao.dataRegistro}}
          </td>
           <td>
            {{gamificacao.nomeEvento}}
          </td>
           <td>
            {{gamificacao.pontuacao}}
          </td>
           <td>
            {{gamificacao.observacao}}
          </td>
          <td>
            <button (click)="onEdit(gamificacao)" class="btn btn-primary" title="Editar">
              <span class="glyphicon glyphicon-pencil"></span>
            </button>
            <button (click)="onRemove(gamificacao)" class="btn btn-danger" title="Remover">
              <span class="glyphicon glyphicon-trash"></span>
            </button>
          </td>
        </tr>
      </table>
    </div>
  `,
})
export class KungfuListComponent {

  @Input() gamificacoes = [];
  @Output() edit = new EventEmitter();
  @Output() remove = new EventEmitter();

  onEdit(gamificacao) {
    this.edit.emit(gamificacao);
  }

  onRemove(gamificacao) {
    this.remove.emit(gamificacao);
  }

}
