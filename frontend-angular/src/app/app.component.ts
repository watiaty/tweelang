import {Component} from '@angular/core';
import {StorageService} from "./_services/storage.service";
import {AuthService} from "./_services/auth.service";
import {User} from "./user";
import {HttpClient, HttpXsrfTokenExtractor} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title: string;
  role: string = '';
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  token: string = '';
  user: User = {
    username: 'ANONYMOUS',
    email: '',
    firstName: '',
    lastName: '',
    nativeLang: '',
    learningLangs: []
  };

  constructor(private storageService: StorageService, private authService: AuthService, private tokenExtractor: HttpXsrfTokenExtractor, private httpClient: HttpClient) {
    this.title = 'Spring Boot - Angular Application';
    this.refresh();
  }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {

      const user = this.storageService.getUser();
      this.user.username = user.username;
      this.role = user.role;

      this.showAdminBoard = this.role.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.role.includes('ROLE_MODERATOR');
    }
  }

  refresh() {
    this.token = this.tokenExtractor.getToken() as string;
    this.httpClient.get<User>('/me', {withCredentials: true}).subscribe({
      next: (response: User) => {
        this.user = response;
        this.isLoggedIn = response.username != 'ANONYMOUS';
      },
      error: (error) => console.info(error),
    });
  }

  logout(): void {
    window.location.href = '/logout';
  }

  login() {
    window.location.href = '/oauth2/authorization/gateway';
  }
}
