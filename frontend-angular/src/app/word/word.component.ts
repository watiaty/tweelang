import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {WordService} from "../_services/word-service.service";
import {of} from "rxjs";
import {Language} from "../language";
import {Word} from "../word";

@Component({
  selector: 'app-word',
  templateUrl: './word.component.html'
})
export class WordComponent implements OnInit {
  wordName: String = "";
  wordLang: String = "";
  word!: Word;
  speechSynthesis = window.speechSynthesis;
  utterance = new SpeechSynthesisUtterance();
  showInput: boolean = false;
  newItem: string = '';
  nativeLanguage!: Language;
  translations: String[] = [];

  constructor(private route: ActivatedRoute, private wordService: WordService) {

  }

  ngOnInit() {
    this.wordName = this.route.snapshot.paramMap.get('word')!;
    this.wordLang = this.route.snapshot.paramMap.get('language')!;

    this.getWord(this.wordName, this.wordLang);
    // this.nativeLanguage = Language.RU;
  }

  getWord(word: String, lang: String) {
    this.wordService.findWord(word, lang).subscribe({
        next: response => {
          this.word = response;
        }
      }
    );
  }

  playWord(word: String) {
    this.utterance.text = word.toString();
    this.speechSynthesis.speak(this.utterance);
  }

  protected readonly of = of;

  delete(id: number) {
    this.wordService.delete(id).subscribe({});
  }

  learnWord(id: number) {
    const req = {
      id: id,
      status: 'LEARNING'
    };
    this.wordService.addUserWordByIdAndStatus(req).subscribe({});
  }
}
