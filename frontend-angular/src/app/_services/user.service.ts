import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {User} from "../user";
import {Observable} from "rxjs";

const URLS = `${environment.userUrl}`;

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {
  }

  public save(user: User): Observable<User> {
    return this.http.post<User>(URLS + '/save', user);
  }
}
