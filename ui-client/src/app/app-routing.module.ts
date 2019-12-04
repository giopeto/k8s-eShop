import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './components/login/login.component';
import { UserComponent } from './components/user/user.component';
import { HomeComponent } from './components/home/home.component';
import { ItemsInGroupComponent } from './components/items-in-group/items-in-group.component';
import { ChatComponent } from './components/chat/chat.component';

const routes: Routes = [
	{ path: 'login', component: LoginComponent },
	{ path: 'home', component: HomeComponent },
	{ path: 'user', component: UserComponent },
	{ path: 'items-in-group/:groupId', component: ItemsInGroupComponent },
	{ path: 'chat', component: ChatComponent },
	{ path: '', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
	imports: [ RouterModule.forRoot(routes) ],
	exports: [ RouterModule ]
})
export class AppRoutingModule {}
