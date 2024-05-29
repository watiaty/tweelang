import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  constructor() {}

  clean(): void {
    if (typeof window !== 'undefined') {
      localStorage.removeItem(`${environment.userString}`);
      localStorage.removeItem(`${environment.accessTokenString}`);
      localStorage.removeItem(`${environment.refreshTokenString}`);
      localStorage.clear();
    }
  }

  public getUser(): any {
    if (typeof window !== 'undefined') {
      const user = localStorage.getItem(`${environment.userString}`);
      if (user) {
        return JSON.parse(user);
      }
      return {};
    }
  }

  public isLoggedIn(): boolean {
    if (typeof window !== 'undefined') {
      const user = localStorage.getItem(`${environment.userString}`);
      return !!user;
    }
    return false;
  }


  public setUser(response: string) {
    if (typeof window !== 'undefined') {
      localStorage.setItem(`${environment.userString}`, response)
    }
  }


  public getAccessToken() {
    if (typeof window !== 'undefined') {
      return localStorage.getItem('access-token')
    }
    return null;
  }

  setAccessToken(access_token: any) {
    if (typeof window !== 'undefined') {
      localStorage.setItem(`${environment.accessTokenString}`, access_token);
    }
  }

  setRefreshToken(refresh_token: any) {
    if (typeof window !== 'undefined') {
      localStorage.setItem(`${environment.refreshTokenString}`, refresh_token);
    }
  }
}
