import {Component, inject, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Word} from "../word";
import {WordService} from "../_services/word-service.service";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {MatChipInputEvent} from "@angular/material/chips";
import {MatSelectChange} from "@angular/material/select";
import {Language} from "../language";


@Component({
  selector: 'app-user-form',
  templateUrl: './word-form.component.html',
})
export class WordFormComponent implements OnInit {
  word: Word;
  addOnBlur = true;
  langs: Language[] = [];
  currentUser: any;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;

  announcer = inject(LiveAnnouncer);
  isWordAdded: any = false;

  constructor(private router: Router, private wordService: WordService) {
    this.word = new Word();
    if (this.langs.length > 0) {
      this.word.language = "en";
    }
  }

  ngOnInit(): void {
    // this.word.translations = [];
  }

  onSubmit() {
    this.wordService.save(this.word).subscribe({
      next: response => {
        if (response) {
          console.log('Сохранено успешно', response);
          // this.gotoUserList();
          this.isWordAdded = true;
          this.word = new Word();
        }
      },
      error: err => {
        console.error('Ошибка при сохранении' + err);
      }
    });
  }

  gotoUserList() {
    this.router.navigate(['/words']);
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    if (value) {
      // this.word.translations.push(value);
    }
    event.chipInput!.clear();
  }

  remove(translation: String): void {
    // const index = this.word.translations.indexOf(translation);
    // if (index >= 0) {
    //   this.word.translations.splice(index, 1);
    //   this.announcer.announce(`Removed ${translation}`);
    // }
  }

  changeLanguage($event: MatSelectChange) {
    this.word.language = $event.value;
  }
}
