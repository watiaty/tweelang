import {Component, OnInit} from '@angular/core';
import {WordService} from "../_services/word-service.service";
import {WordShort} from "../word-short";

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrl: './rating.component.css'
})
export class RatingComponent implements OnInit  {
  words!: WordShort[];
  language: string = "en";
  constructor(private wordService: WordService) {

  }

  ngOnInit(): void {
    this.getWords();
  }

  getWords() {
    this.wordService.findAllByRating(this.language).subscribe({
      next: response => {
        this.words = response;
      }
    });
  }

  chunkWords(words: any[], size: number): any[][] {
    return Array.from({ length: Math.ceil(words.length / size) }, (_, index) =>
      words.slice(index * size, index * size + size)
    );
  }
}
