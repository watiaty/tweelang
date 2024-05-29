import {Component, OnInit} from '@angular/core';
import {AuthService} from "../_services/auth.service";
import {StorageService} from "../_services/storage.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  form: any = {
    username: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  currentUser: any;

  constructor(private authService: AuthService, private storageService: StorageService) {
  }

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.isLoggedIn = true;
      this.currentUser = this.storageService.getUser();
    }
  }

  onSubmit(): void {
    const {username, password} = this.form;
    this.authService.login(username, password).subscribe({
      next: response => {
        if (response) {
          this.storageService.setUser(JSON.stringify(response.user));
          this.storageService.setAccessToken(response.access_token);
          this.storageService.setRefreshToken(response.refresh_token);

          this.isLoginFailed = false;
          this.isLoggedIn = true;
        }
      },
      error: err => {
        if (err.status === 401) {
          this.errorMessage = err.error.error;
        } else {
          this.errorMessage = err.error;
        }
        this.isLoginFailed = true;
      }
    });
  }
}
