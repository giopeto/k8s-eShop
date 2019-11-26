import { Component, OnInit } from '@angular/core';

import { LoginService } from '../../services/login.service';

@Component({
	selector: 'app-user',
	templateUrl: './user.component.html',
	styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

	user: object;
	constructor(private loginService: LoginService) { }

	ngOnInit() {
		this.loginService.geCurrentUser().subscribe(user => this.user = user);
	}

	signOut() {
		this.loginService.signOut().subscribe(()=> this.loginService.userIsSignedIn(false));
	}
}
