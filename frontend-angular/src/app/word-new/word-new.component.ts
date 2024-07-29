import {Component, OnInit} from '@angular/core';
import {Word} from "../word";
import {WordService} from "../_services/word-service.service";

@Component({
  selector: 'app-word-new',
  templateUrl: './word-new.component.html',
  styleUrl: './word-new.component.css'
})
export class WordNewComponent implements OnInit {
  word!: Word;
  translateVisible: boolean = false;

  constructor(private wordService: WordService) {
  }

  ngOnInit() {
    this.loadWord();
  }

  loadWord() {
    this.wordService.findUnstudiedWord("EN").subscribe(response => {
      this.word = response;
    });
  }

  updateStatus(status: string) {
    const req = {
      id: this.word.id,
      status: status
    };
    this.wordService.addUserWordByIdAndStatus(req).subscribe(() => {
      this.translateVisible = false;
      this.loadWord();
    });
  }
}
