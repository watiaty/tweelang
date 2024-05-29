import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {WordService} from "../_services/word-service.service";
import {WordInfo} from "../word-info";
import {StorageService} from "../_services/storage.service";
import {of} from "rxjs";
import {Language} from "../language";

@Component({
  selector: 'app-word',
  templateUrl: './word.component.html'
})
export class WordComponent implements OnInit {
  wordName: String = "";
  wordLang: String = "";
  word!: WordInfo;
  speechSynthesis = window.speechSynthesis;
  utterance = new SpeechSynthesisUtterance();
  showInput: boolean = false;
  newItem: string = '';
  nativeLanguage!: Language;
  translations: String[] = [];

  constructor(private route: ActivatedRoute, private wordService: WordService, private router: Router) {

  }

  ngOnInit() {
    this.wordName = this.route.snapshot.paramMap.get('word')!;
    this.wordLang = this.route.snapshot.paramMap.get('language')?.toUpperCase()!;

    this.getWord(this.wordName, this.wordLang);
    this.nativeLanguage = Language.RU;
  }

  getWord(word: String, lang: String) {
    this.wordService.findWord(word, lang).subscribe({
        next: response => {
          this.word = new WordInfo(response.id, response.word, response.language, response.translations, response.definition, response.synonyms, response.antonyms, response.derived, response.mainWord);
        }
      }
    );
  }

  routeAnotherWord(word: String, lang: String) {
    this.getWord(word, lang);
    const queryParams = {data: word, lang: lang};
    this.router.navigate(['/word/'+ lang], {queryParams});
  }

  deleteTranslation(id: String) {
    this.wordService.deleteTranslation(id).subscribe({
      next: response => {
        this.getWord(this.wordName, this.wordLang);
      },
      error: error => {
        console.error('Ошибка при сохранении', error);
      }
    });
  }

  playWord(word: String) {
    this.utterance.text = word.toString();
    this.speechSynthesis.speak(this.utterance);
  }

  addTranslation() {
    this.translations.push(this.newItem);
    this.wordService.addTranslationAndWord(this.word.id, this.translations, "RU").subscribe({
      next: response => {
        this.getWord(this.wordName, this.wordLang);
      },
      error: error => {
        console.error('Ошибка при сохранении', error);
      }
    });
    this.toggleInput();
  }

  toggleInput() {
    this.showInput = !this.showInput;
    if (!this.showInput) {
      this.newItem = '';
    }
  }

  protected readonly of = of;

  delete(id: String) {
    this.wordService.delete(id).subscribe({});
  }
}
