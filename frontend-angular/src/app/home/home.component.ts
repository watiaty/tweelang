import {Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {WordService} from "../_services/word-service.service";
import {ActivatedRoute, Router} from "@angular/router";
import {WordShort} from "../word-short";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {
  searchText = new FormControl();
  words: WordShort[] = [];

  constructor(
    private wordService: WordService,
    private route: ActivatedRoute,
    private router: Router) {

  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const {q} = params;
      this.searchText.setValue(q);
      this.search();
    });
  }

  search() {
    const q = this.searchText.value;
    this.wordService.searchWords(q).subscribe({
      next: response => {
        this.words = response;
      }
    });
  }

  navigateToSearch(): void {
    if (this.searchText.getRawValue() != "") {
      const queryParams = {q: this.searchText.getRawValue()};
      this.router.navigate(['/search'], {queryParams});
    }
  }
}
