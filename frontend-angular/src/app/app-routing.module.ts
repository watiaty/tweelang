import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {WordListComponent} from './word-list/word-list.component';
import {WordFormComponent} from './word-form/word-form.component';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ProfileComponent} from "./profile/profile.component";
import {WordComponent} from "./word/word.component";
import {TrainComponent} from "./train/train.component";
import {HomeComponent} from "./home/home.component";
import {AdminComponent} from "./admin/admin.component";
import {RatingComponent} from "./rating/rating.component";
import {WordNewComponent} from "./word-new/word-new.component";

const routes: Routes = [
  {path: 'words', component: WordListComponent},
  {path: 'addword', component: WordFormComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'word/:language/:word', component: WordComponent},
  {path: 'train', component: TrainComponent},
  {path: 'search', component: HomeComponent},
  {path: '', component: HomeComponent},
  {path: 'admin', component: AdminComponent},
  {path: 'rating', component: RatingComponent},
  {path: 'learn', component: WordNewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
