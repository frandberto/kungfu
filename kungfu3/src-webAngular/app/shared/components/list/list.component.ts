import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'list-app',
  templateUrl : './list.component.html',
  styleUrls : ['list.component.scss']
})
export class ListComponent {
  @Input() gamificacoes = [];
  @Input() periodo = '';
  @Input() isButtonEnabled = false;
  @Output() edit = new EventEmitter();
  @Output() remove = new EventEmitter();
  @Input() title = '';

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
