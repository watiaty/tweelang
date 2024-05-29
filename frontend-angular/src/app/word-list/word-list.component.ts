import {Component, OnInit, ViewChild} from '@angular/core';
import {WordService} from "../_services/word-service.service";
import {MatSelectChange} from "@angular/material/select";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {StorageService} from "../_services/storage.service";
import {WordInfo} from "../word-info";
import {Language, LanguageHelper} from "../language";

@Component({
  selector: 'app-user-list',
  templateUrl: './word-list.component.html'
})
export class WordListComponent implements OnInit {
  words: WordInfo[] = [];
  selectedLang!: Language;
  selectedStatus!: boolean;
  langs: Language[] = [];
  currentUser: any;
  dataSource!: MatTableDataSource<any>;
  displayedColumns: string[] = ['word', 'translations', 'star'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private wordService: WordService) {
    this.langs.push(Language.EN);
    this.langs.push(Language.PL);
    this.selectedLang = this.langs[0];
  }

  ngOnInit() {
    this.loadWords();
  }

  changeLanguage($event: MatSelectChange) {
    this.selectedLang = $event.value;
    this.updateDataSource();
  }

  changeStatus(status: boolean) {
    this.selectedStatus = status;
    this.updateDataSource();
  }

  delete(id: String) {
    this.wordService.deleteUserWord(id).subscribe({
      next: response => {
        this.loadWords();
      },
      error: error => {
        console.error('Ошибка при сохранении', error);
      }
    });
  }

  updateDataSource() {
    this.dataSource = new MatTableDataSource(this.words);
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  loadWords() {
    this.wordService.findUserWords("EN").subscribe(response => {
      this.words = response;
      this.updateDataSource();
    });
  }
}
