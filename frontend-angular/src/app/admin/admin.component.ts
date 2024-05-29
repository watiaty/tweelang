import {Component} from '@angular/core';
import {WordService} from "../_services/word-service.service";
import {HttpClient, HttpEventType, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {MatCardModule} from "@angular/material/card";

@Component({
  selector: 'app-admin',
  styleUrl: './admin.component.css',
  templateUrl: './admin.component.html'
})
export class AdminComponent {
  currentFile?: File;
  progress = 0;
  message = '';

  fileName = 'Select File';
  fileInfos?: Observable<any>;

  constructor(private http: HttpClient, private wordService: WordService) {
  }

  selectFile(event: any): void {
    this.progress = 0;
    this.message = "";

    if (event.target.files && event.target.files[0]) {
      const file: File = event.target.files[0];
      this.currentFile = file;
      this.fileName = this.currentFile.name;
    } else {
      this.fileName = 'Select File';
    }
  }

  upload(): void {
    if (this.currentFile) {
      this.wordService.upload(this.currentFile).subscribe({
        next: (event: any) => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            this.message = event.body.message;
          }
        },
        error: (err: any) => {
          console.log(err);
          this.progress = 0;

          if (err.error && err.error.message) {
            this.message = err.error.message;
          } else {
            this.message = 'Could not upload the file!';
          }
        },
        complete: () => {
          this.currentFile = undefined;
        }
      });
    }
  }

  addWordsFromCsv() {
    this.wordService.csv().subscribe({
      next: response => {
        if (response) {
          console.log('Сохранено успешно', response);
        }
      },
      error: err => {
        console.error('Ошибка при сохранении' + err);
      }
    });
  }
}
