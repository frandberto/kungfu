import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from './login.component';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from 'app/shared/services/authentication/authentication.service';

@NgModule({
    imports: [
        CommonModule,
        LoginRoutingModule, FormsModule
    ],
    declarations: [LoginComponent],
    providers: [AuthenticationService]
})
export class LoginModule {
}
