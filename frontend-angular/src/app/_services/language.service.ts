import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

const URLS = `${environment.languageUrl}`;

@Injectable({
  providedIn: 'root'
})
export class LanguageService {

  constructor(private http: HttpClient) { }

  public findAll(): Observable<String[]> {
    return this.http.get<String[]>(URLS);
  }
}
