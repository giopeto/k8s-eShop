import { Component, OnInit } from '@angular/core';

import { Users } from '../../models/Users';
import { LoginService } from '../../services/login.service';
import { FilesService } from '../../services/files.service';

@Component({
	selector: 'app-user',
	templateUrl: './user.component.html',
	styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

	user: Users;
	edit: boolean = false;

	constructor(private loginService: LoginService, private filesService: FilesService) { }

	ngOnInit() {
		this.loginService.geCurrentUser().subscribe(user => this.user = user);
	}

	signOut() {
		this.loginService.signOut().subscribe(()=> this.loginService.userIsSignedIn(false));
	}

	editUser(){
		this.edit = !this.edit;
	}

	saveUserImg(id) {
		this.filesService.save(id).subscribe();
	}
}
