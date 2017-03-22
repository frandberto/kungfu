import { Component } from '@angular/core';

@Component({
  selector: 'registrar-form',
  template: `
    <div class=".container-fluid">
       <!-- Cabeçalho -->
       <div class="row">
         <div class="col-md-8"></div>
         <div class="col-md-1"><strong>Período: </strong></div>
         <div class="col-md-3">
           <select class="form-control" >
              <option title="Teste">1º Quadrimestre (Jan a Abr/2017)</option>
              <option>2º Quadrimestre (Mai a Ago/2017)</option>
           </select>
         </div>
       </div>
       <div class="row">
         <div class="col-md-2"><strong>Participante</strong></div>
         <div class="col-md-2"><strong>Data</strong></div>
         <div class="col-md-2"><strong>Evento</strong></div>
         <div class="col-md-2"><strong>Pontuação</strong></div>
         <div class="col-md-3"><strong>Observação</strong></div>
       </div>
       <div class="row">
         <div class="col-md-2">
           <select class="form-control">
              <option>João</option>
              <option>Roberto</option>
              <option>Zeca</option>
           </select>
         </div>
         <div class="col-md-2">
           <input type="date" >
         </div>
         <div class="col-md-2">
           <select class="form-control">
              <option>Ministrar uma palestra na Cop</option>
              <option>ApresFazer as tarefas de casa e compartilhar com o facilitadorZeca</option>
              <option>Participar dos encontros em dupla, ou seja, além de você, levar outra pessoa (podendo ser um convidado ou um membro da própria CoP)</option>
              <option>Participação dos encontros da Cop</option>
              <option>Divulgar a Cop e engajar uma pessoa como novo participante</option>
              <option>Contribuir com vídeos, artigos, mensagens e outros materiais relacionados à liderança e/ou Coaching</option>
           </select>
         </div>
         <div class="col-md-2"><strong>0,00</strong></div>
         <div class="col-md-3"><textarea rows="4" cols="50" placeholder="Observação..." ></textarea></div>
       </div>
       <!-- Botões -->
        <div class="row">
           <div class="col-md-5"></div>
           <div class="col-md-1"><button type="submit" class="btn btn-primary">Salvar</button></div>
           <div class="col-md-1"><button type="submit" class="btn btn-primary">Limpar</button></div>
           <div class="col-md-5"></div>
        </div>
    </div>
    <p>
    <div class=".container-fluid">
    <table class="table table-striped">
      <tr>
         <th>Participante</th>
         <th>Date</th>
         <th>Evento</th>
         <th>Pontuação</th>
         <th>Observação</th>
         <th>Ações</th>
      </tr>
      <tr>
         <td>Roberto</td>
         <td>01/03/2017</td>
         <td>Ministrar uma palestra na Cop</td>
         <td>15.00</td>
         <td>Uma observação qualquer...</td>
         <td><button type="button" class="close" aria-label="Close">
         <span aria-hidden="true">&times;</span></button>
         </td>
      </tr>
      <tr>
         <td>Roberto</td>
         <td>02/03/2017</td>
         <td>Divulgar a Cop e engajar uma pessoa como novo participante</td>
         <td>2.00</td>
         <td>Uma observação qualquer...</td>
         <td><button type="button" class="close" aria-label="Close">
         <span aria-hidden="true">&times;</span></button>
         </td>
      </tr>
      <tr>
         <td>João</td>
         <td>03/03/2017</td>
         <td>Ministrar uma palestra na Cop</td>
         <td>15.00</td>
         <td>Uma observação qualquer...</td>
         <td><button type="button" class="close" aria-label="Close">
         <span aria-hidden="true">&times;</span></button>
         </td>
      </tr>     
    </table>
    </div>
  `
})
export class SignupFormComponent {

}
