import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { GamificacaoService } from './gamificacao.service';
import { AppComponent } from './app.component';
import { KungfuEditComponent } from './kungfu-edit.component';
import { KungfuListComponent } from './kungfu-list.component';
import { KungfuSelectComponent } from './kungfu-select.component';
import { MyDatePickerModule } from 'mydatepicker';
import { KungfuVisualizacaoComponent } from './kungfu-visualizacao.component';

@NgModule({
  imports: [BrowserModule, FormsModule, HttpModule, MyDatePickerModule],
  declarations: [AppComponent, KungfuEditComponent, KungfuListComponent, 
      KungfuSelectComponent, KungfuVisualizacaoComponent],
  providers: [GamificacaoService],
  bootstrap: [AppComponent, KungfuVisualizacaoComponent]
})
export class AppModule { }
