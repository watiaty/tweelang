import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {StorageService} from "./storage.service";

const URLS = `${environment.authUrl}`;

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private storageService: StorageService, private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post(
      URLS + '/signin',
      {
        username,
        password,
      }
    );
  }

  register(username: string, firstname: string, lastname: string, password: string): Observable<any> {
    return this.http.post(
      URLS + '/signup',
      {
        username,
        firstname,
        lastname,
        password,
      }
    );
  }

  logout(): Observable<any> {
    this.storageService.clean();
    return this.http.post(URLS + '/logout', { });
  }
}
