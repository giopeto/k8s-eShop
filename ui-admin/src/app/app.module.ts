import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { AdminComponent } from './components/admin/admin.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { GroupsComponent } from './components/groups/groups.component';
import { GroupsAddEditComponent } from './components/groups-add-edit/groups-add-edit.component';
import { ItemsComponent } from './components/items/items.component';
import { ItemsAddEditComponent } from './components/items-add-edit/items-add-edit.component';
import { UserComponent } from './components/user/user.component';
import { FilesComponent } from './components/files/files.component';

import { LoginService } from './services/login.service';
import { GroupsService } from './services/groups.service';
import { ItemsService } from './services/items.service';
import { FilesService } from './services/files.service';
import { SafePipe } from './pipes/safe.pipe';

@NgModule({
	declarations: [
		AppComponent,
		LoginComponent,
		AdminComponent,
		NavigationComponent,
		GroupsComponent,
		GroupsAddEditComponent,
		UserComponent,
		ItemsComponent,
		ItemsAddEditComponent,
		FilesComponent,
		SafePipe
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
