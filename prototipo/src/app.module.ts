import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { SignupFormComponent } from './registrar-form.component';

@NgModule({
  imports: [BrowserModule],
  declarations: [AppComponent, SignupFormComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
