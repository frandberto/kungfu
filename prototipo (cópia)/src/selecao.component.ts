import { Component, Input } from '@angular/core';

@Component({
  selector: 'selecao-component',
  template: `
    <div class="container">
      <selection *ngFor="let entidade of entidades">
         <option value="entidade.id">{{entidade.descricao}}</option>
      </selection>
    </div>
  `
})
export class SelecaoComponent {

  @Input()
  entidades = [];

  entidadeSelecionada;

  carregar(entidades)  {
    this.entidades = entidades;
  }

}
