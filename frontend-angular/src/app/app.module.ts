import {NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import {AppRoutingModule} from "./app-routing.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {BrowserModule} from "@angular/platform-browser";
import {WordListComponent} from "./word-list/word-list.component";
import {WordFormComponent} from "./word-form/word-form.component";
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {ProfileComponent} from './profile/profile.component';
import {HttpRequestInterceptor} from "./_helpers/http.interceptor";
import {WordComponent} from './word/word.component';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {TrainComponent} from "./train/train.component";
import { HomeComponent } from './home/home.component';
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatOption, MatSelect} from "@angular/material/select";
import {MatButtonToggle, MatButtonToggleGroup} from "@angular/material/button-toggle";
import {MatInput, MatInputModule} from "@angular/material/input";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef, MatNoDataRow, MatRow, MatRowDef,
  MatTable
} from "@angular/material/table";
import {MatAnchor, MatButton, MatFabAnchor, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatPaginator} from "@angular/material/paginator";
import {NgForOf, NgIf, NgOptimizedImage, NgSwitch} from "@angular/common";
import {MatChip, MatChipGrid, MatChipInput, MatChipSet} from "@angular/material/chips";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatSlider, MatSliderThumb} from "@angular/material/slider";
import {MatAccordion, MatExpansionPanel, MatExpansionPanelTitle, MatExpansionModule} from "@angular/material/expansion";
import {MatList, MatListItem} from "@angular/material/list";
import {MatDivider} from "@angular/material/divider";
import {RouterLink, RouterOutlet} from "@angular/router";
import {MatChipsModule} from '@angular/material/chips';
import {MatCheckbox} from "@angular/material/checkbox";
import { AdminComponent } from './admin/admin.component';
import {MatToolbar} from "@angular/material/toolbar";
import {MatCard, MatCardContent, MatCardHeader} from "@angular/material/card";
import {MatCardModule} from "@angular/material/card";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import { RatingComponent } from './rating/rating.component';
import {MatTooltip} from "@angular/material/tooltip";


@NgModule({
  declarations: [
    AppComponent,
    WordListComponent,
    WordFormComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    WordComponent,
    TrainComponent,
    HomeComponent,
    AdminComponent,
    RatingComponent
  ],
    imports: [
        MatChipsModule,
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        MatFormField,
        MatInputModule,
        MatExpansionModule,
        MatSelect,
        MatOption,
        MatButtonToggleGroup,
        MatButtonToggle,
        MatInput,
        MatTable,
        MatColumnDef,
        MatHeaderCell,
        MatCell,
        MatHeaderCellDef,
        MatCellDef,
        MatIconButton,
        MatIcon,
        MatMenuTrigger,
        MatMenu,
        MatMenuItem,
        MatHeaderRow,
        MatRow,
        MatPaginator,
        MatHeaderRowDef,
        MatRowDef,
        MatNoDataRow,
        NgForOf,
        MatChipGrid,
        MatChip,
        FormsModule,
        MatChipInput,
        MatButton,
        NgSwitch,
        MatAnchor,
        MatFabAnchor,
        MatLabel,
        MatSliderThumb,
        MatSlider,
        NgIf,
        MatChipSet,
        ReactiveFormsModule,
        MatExpansionPanel,
        MatExpansionPanelTitle,
        MatAccordion,
        MatListItem,
        MatList,
        MatDivider,
        RouterOutlet,
        RouterLink,
        MatCheckbox,
        NgOptimizedImage,
        MatToolbar,
        MatCard,
        MatCardHeader,
        MatCardContent,
        MatCardModule,
        MatProgressBarModule,
        MatTooltip
    ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpRequestInterceptor,
      multi: true
    },
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
