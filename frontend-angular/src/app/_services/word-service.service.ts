import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from '@angular/common/http';
import {Observable} from "rxjs";
import {Word} from "../word";
import {environment} from "../../environments/environment";
import {TrainRequest} from "../train-request";
import {WordShort} from "../word-short";
import {WordWithTranslations} from "../word-with-translations";
import {WordPractice} from "../word-practice";


const URLS = `${environment.wordUrl}`;
const URLSw = `${environment.wordsUrl}`;
const TRANSLATION_URL = `${environment.translationUrl}`;

@Injectable({
  providedIn: 'root',
})
export class WordService {

  constructor(private http: HttpClient) {

  }

  public findUserWords(selectedLang: any): Observable<WordWithTranslations[]> {
    return this.http.get<WordWithTranslations[]>(URLSw + '/' + selectedLang);
  }

  public findWordsForTraining(trainRequest: TrainRequest): Observable<WordPractice[]> {
    return this.http.post<WordPractice[]>(URLSw + '/practice', trainRequest);
  }

  public findAll(): Observable<Word[]> {
    return this.http.get<Word[]>(URLS + '/all');
  }

  public findAllByRating(languageCode: String): Observable<Word[]> {
    return this.http.get<Word[]>(URLS + '/rating/' + languageCode);
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
    return this.http.get<Word>(URLS + '/' + wordLang + "/" + wordName);
  }

  searchWords(searchText: String) {
    return this.http.get<WordShort[]>(URLS + '/search?q=' + searchText);
  }

  addTranslationAndWord(id: number, translations: String[], language: String) {
    return this.http.post<Word>(TRANSLATION_URL + '/add', {id, translations, language});
  }

  addUserWordByIdAndStatus(req: { id: number, status: string }) {
    return this.http.post(URLSw + '/add-new-word', req);
  }

  deleteUserWord(id: String) {
    return this.http.delete<Word>(URLSw + '/delete/' + id);
  }

  updateStatus(id: number, status: string) {
    return this.http.patch<Word>(URLSw + '/update/' + id, status);
  }

  delete(id: number) {
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

  findUnstudiedWord(language: string) {
    return this.http.get<Word>(URLSw + '/learn/' + language);
  }
}
