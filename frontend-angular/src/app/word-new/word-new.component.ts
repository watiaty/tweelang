import { Component } from '@angular/core';
import {Word} from "../word";
import {WordService} from "../_services/word-service.service";
import {Language} from "../language";

@Component({
  selector: 'app-word-new',
  templateUrl: './word-new.component.html',
  styleUrl: './word-new.component.css'
})
export class WordNewComponent {
  word!: Word;
  translateVisible: boolean = false;

  constructor(private wordService: WordService) {}

  ngOnInit() {
    this.loadWord();
  }

  loadWord() {
    this.wordService.findUnstudiedWord("EN").subscribe(response => {
      this.word = response;
    });
  }

  updateStatus(learned: string) {
    this.loadWord();
    this.translateVisible = false;
  }
}
