import {Component, OnInit} from '@angular/core';
import {StorageService} from "../_services/storage.service";
import {UserService} from "../_services/user.service";
import {User} from "../user";
import {Router} from "@angular/router";
import {FormControl} from "@angular/forms";
import {ThemePalette} from "@angular/material/core";
import {LanguageService} from "../_services/language.service";
import {MatSelectChange} from "@angular/material/select";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html'
})
export class ProfileComponent implements OnInit {
  currentUser: any;
  user: User;
  colorControl = new FormControl('native' as ThemePalette);
  languages!: String[];
  learningLanguages!: String[];

  constructor(private storageService: StorageService, private userService: UserService, private router: Router, private languageService: LanguageService) {
    this.user = new User();
  }

  ngOnInit(): void {
    this.currentUser = this.storageService.getUser();
    this.user.firstName = this.currentUser.firstName;
    this.user.lastName = this.currentUser.lastName;
    this.user.email = this.currentUser.email;
    this.user.nativeLang = this.currentUser.nativeLang;
    this.user.learningLangs = this.currentUser.learningLang;
    this.languageService.findAll().subscribe(
      response => {
        this.languages = response;
        this.learningLanguages = this.languages.filter(lang => !this.user.learningLangs.includes(lang.toString()));
      }
    )
  }

  onSubmit() {
    this.userService.save(this.user).subscribe(
      response => {
        console.log('Сохранено успешно', response);
        this.storageService.setUser(JSON.stringify(response));
        // localStorage.setItem(`${environment.userString}`, JSON.stringify(response))
        this.gotoProfile();
      },
      error => {
        console.error('Ошибка при сохранении', error);
      }
    );
  }

  gotoProfile() {
    this.router.navigate(['/profile']);
  }

  add(event: MatSelectChange): void {
    this.user.learningLangs.push(event.value);
    this.learningLanguages.splice(this.learningLanguages.indexOf(event.value), 1);
  }

  remove(lang: string): void {
    this.user.learningLangs.splice(this.user.learningLangs.indexOf(lang), 1);
    this.learningLanguages.push(lang);
  }
}
