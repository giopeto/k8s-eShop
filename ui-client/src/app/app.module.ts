import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { UserComponent } from './components/user/user.component';
import { FilesComponent } from './components/files/files.component';
import { HomeComponent } from './components/home/home.component';

import { LoginService } from './services/login.service';
import { GroupsService } from './services/groups.service';
import { ItemsService } from './services/items.service';
import { FilesService } from './services/files.service';
import { SafePipe } from './pipes/safe.pipe';
import { ItemsInGroupComponent } from './components/items-in-group/items-in-group.component';

@NgModule({
	declarations: [
		AppComponent,
		LoginComponent,
		NavigationComponent,
		UserComponent,
		FilesComponent,
		SafePipe,
		HomeComponent,
		ItemsInGroupComponent
	],
	imports: [
		BrowserModule,
		ReactiveFormsModule,
		HttpClientModule,
		AppRoutingModule
	],
	providers: [
		LoginService,
		GroupsService,
		ItemsService,
		FilesService
	],
	bootstrap: [AppComponent]
})
export class AppModule { }
