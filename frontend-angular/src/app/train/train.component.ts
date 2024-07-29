import {Component} from '@angular/core';
import {WordService} from "../_services/word-service.service";
import {TrainRequest} from "../train-request";
import {MatSelectChange} from "@angular/material/select";
import {Language} from "../language";
import {WordPractice} from "../word-practice";

@Component({
  selector: 'app-train',
  templateUrl: './train.component.html',
  styleUrl: './train.component.css'
})
export class TrainComponent {
  rangeValue: number;
  visible: boolean = true;
  words: WordPractice[] = [];
  id: number = 0;
  langs!: Language[];
  mistakes: number = 0;
  streak: number = 0;
  inputValue: string = '';
  alertVisible: boolean = false;
  translateVisible: boolean = false;
  translationsVisible: boolean = false;
  definitionVisible: boolean = false;
  trainRequest: TrainRequest;
  checked = false;

  constructor(private wordService: WordService) {
    this.rangeValue = 10;
    this.trainRequest = new TrainRequest();
    // if (this.langs.length > 0) {
      this.trainRequest.language = "English";
    // }
  }

  startTrain() {
    this.visible = false;
    if (this.checked) this.trainRequest.quantity = 0;
    this.wordService.findWordsForTraining(this.trainRequest).subscribe({
        next: response => {
          this.words = response;
        }
      }
    );
  }

  onInputKeyUp() {
    if (this.words[this.id].word === this.inputValue) {
      if (!this.translateVisible) {
        this.wordService.updateStatus(this.words[this.id].id, "learned").subscribe();
      } else {
        this.wordService.updateStatus(this.words[this.id].id, "learning").subscribe();
        this.words.push(this.words[this.id]);
      }
      this.alertVisible = false;
      this.translateVisible = false;
      this.id++;
      this.inputValue = "";
      this.streak++;
    } else {
      this.alertVisible = true;
      this.mistakes++;
      this.streak = 0;
    }
  }

  changeLanguage($event: MatSelectChange) {
    this.trainRequest.language = $event.value;
  }

  toggleDefinition() {
    this.definitionVisible = !this.definitionVisible;
  }

  toggleTranslations() {
    this.translationsVisible = !this.translationsVisible;
  }
}
