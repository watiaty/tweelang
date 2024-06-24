import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from '@angular/common/http';
import {Observable} from "rxjs";
import {Word} from "../word";
import {environment} from "../../environments/environment";
import {WordInfo} from "../word-info";
import {TrainRequest} from "../train-request";


const URLS = `${environment.wordUrl}`;
const URLSw = `${environment.wordsUrl}`;
const TRANSLATION_URL = `${environment.translationUrl}`;

@Injectable({
  providedIn: 'root',
})
export class WordService {

  constructor(private http: HttpClient) {

  }

  public findUserWords(selectedLang: any): Observable<WordInfo[]> {
    return this.http.get<WordInfo[]>(URLSw + '/' + selectedLang);
  }

  public findWordsForTraining(trainRequest: TrainRequest): Observable<Word[]> {
    return this.http.post<Word[]>(URLSw + '/practice', trainRequest);
  }

  public findAll(): Observable<Word[]> {
    return this.http.get<Word[]>(URLS + '/all');
  }

  public findAllByRating(): Observable<Word[]> {
    return this.http.get<Word[]>(URLS + '/rating' + '/EN');
  }

  public save(word: Word): Observable<any> {
    return this.http.post<Word>(URLSw + '/add', word);
  }

  public csv(): Observable<any> {
    return this.http.post<Word>(URLSw + '/csv', "");
  }

  public deleteTranslation(id: String): Observable<Word> {
    return this.http.delete<Word>(TRANSLATION_URL + '/' + id);
  }

  findWord(wordName: String, wordLang: String) {
    return this.http.get<WordInfo>(URLS + '/' + wordLang + "/" + wordName);
  }

  searchWords(searchText: String) {
    return this.http.get<WordInfo[]>(URLS + '/search?q=' + searchText);
  }

  addTranslationAndWord(id: String, translations: String[], language: String) {
    return this.http.post<Word>(TRANSLATION_URL + '/add', {id, translations, language});
  }

  deleteUserWord(id: String) {
    return this.http.delete<Word>(URLSw + '/delete/' + id);
  }

  updateStatus(id: string, status: string) {
    return this.http.patch<Word>(URLSw + '/update/' + id, status);
  }

  delete(id: String) {
    return this.http.delete<Word>(URLS + '/' + id);
  }

  upload(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);

    const req = new HttpRequest('POST', URLS + `/upload`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }

  findUnstudiedWord(lang: string) {
    return this.http.get<Word>(URLSw + '/learn' + '/EN');
  }
}
