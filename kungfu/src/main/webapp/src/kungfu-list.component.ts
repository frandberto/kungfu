import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'kungfu-list',
  template: `
    <p>
    <div class="panel panel-primary">
       <div class="panel-heading"></div>
       <div class="panel-body">
    <div class="container-fluid">
      <table class="table table-striped">
        <tr>
         <th>Participante</th>
         <th>Data</th>
         <th>Evento</th>
         <th>Pontuação</th>
         <th>Observação</th>
         <th *ngIf="this.isButtonEnabled">Ações</th>
        </tr>
        <tr *ngFor="let gamificacao of gamificacoes">
          <input type="hidden" value="{{gamificacao.idGamificacao}}">
          <td>
            {{gamificacao.apelido}}
          </td>
           <td>
            {{ this.getDataFormatada(gamificacao.dataRegistro) }}
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
          <td *ngIf="this.isButtonEnabled">
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
    </div>
    </div>
  `,
})
export class KungfuListComponent {

  @Input() gamificacoes = [];
  @Input() isButtonEnabled = false;
  @Output() edit = new EventEmitter();
  @Output() remove = new EventEmitter();

  onEdit(gamificacao) {
    this.edit.emit(gamificacao);
  }

  onRemove(gamificacao) {
    this.remove.emit(gamificacao);
  }

  private getDataAtual() {
      let dataAtual = new Date();
      return this.getDataFormatada(dataAtual);
  }

 /**
 * Retorna a data formatada em dd/mm/aaaa
 */
  private getDataFormatada(data: Date) {
      let dia = data.getDate();
      let mes = data.getMonth() + 1;
      let ano = data.getFullYear();
      let dataAtualFormatada : string;
      dataAtualFormatada = dia + '/' + mes + '/' + ano;      
      return dataAtualFormatada;
  }

}
