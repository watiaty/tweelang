import {Component} from '@angular/core';
import {WordService} from "../_services/word-service.service";
import {Word} from "../word";
import {TrainRequest} from "../train-request";
import {MatSelectChange} from "@angular/material/select";
import {Language} from "../language";

@Component({
  selector: 'app-train',
  templateUrl: './train.component.html',
  styleUrl: './train.component.css'
})
export class TrainComponent {
  rangeValue: number;
  visible: boolean = true;
  words: Word[] = [];
  id: number = 0;
  langs!: Language[];
  currentUser: any;
  inputValue: string = '';
  alertVisible: boolean = false;
  translateVisible: boolean = false;
  trainRequest: TrainRequest;
  checked = false;

  constructor(private wordService: WordService) {
    // this.currentUser = this.storageService.getUser();
    // this.langs = this.currentUser.learningLang;
    // this.langs.push(Language.EN);
    this.rangeValue = 10;
    this.trainRequest = new TrainRequest();
    // if (this.langs.length > 0) {
      this.trainRequest.language = Language.EN;
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
      }
      this.alertVisible = false;
      this.translateVisible = false;
      this.id++;
      this.inputValue = "";
    } else {
      this.alertVisible = true;
    }
  }

  changeLanguage($event: MatSelectChange) {
    this.trainRequest.language = $event.value;
  }
}
