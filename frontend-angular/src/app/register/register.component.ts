import { Component } from '@angular/core';
import {AuthService} from "../_services/auth.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  form: any = {
    username: null,
    firstname: null,
    lastname: null,
    password: null
  };
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService) { }

  onSubmit(): void {
    console.log('Value of form.username:', this.form.username);
    const { username, firstname, lastname, password } = this.form;

    this.authService.register(username, firstname, lastname, password).subscribe({
      next: data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      error: err => {
        if (err.status === 409) {
          this.errorMessage = err.error.error;
        }
        this.isSignUpFailed = true;
      }
    });
  }
}
