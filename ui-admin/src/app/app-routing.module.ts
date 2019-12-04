import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './components/login/login.component';
import { AdminComponent } from './components/admin/admin.component';
import { GroupsComponent } from './components/groups/groups.component';
import { GroupsAddEditComponent } from './components/groups-add-edit/groups-add-edit.component';
import { UserComponent } from './components/user/user.component';
import { ItemsComponent } from './components/items/items.component';
import { ItemsAddEditComponent } from './components/items-add-edit/items-add-edit.component';

const routes: Routes = [
	{ path: 'login', component: LoginComponent },
	{ path: 'admin', component: AdminComponent },
	{ path: 'user', component: UserComponent },
	{ path: 'groups', component: GroupsComponent },
	{ path: 'groups_edit/:id', component: GroupsAddEditComponent },
	{ path: 'groups_add', component: GroupsAddEditComponent },
	{ path: 'items', component: ItemsComponent },
	{ path: 'items_edit/:id', component: ItemsAddEditComponent },
	{ path: 'items_add', component: ItemsAddEditComponent },
	{ path: '', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
	imports: [ RouterModule.forRoot(routes) ],
	exports: [ RouterModule ]
})
export class AppRoutingModule {}
