import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {Router} from '@angular/router';

import { LoginService } from '../../services/login.service';
import { Users } from '../../models/Users';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrls: ['./login.component.css']
})
export class LoginComponent {

	loginForm: FormGroup;
	userCredentials: any;
	user: Users;
	titleAlert = 'This field is required';
	errorMessage: string;
	
	constructor(private formBuilder: FormBuilder, private loginService: LoginService, private router: Router) {
		this.loginForm = formBuilder.group({
			'email' : [null, Validators.required],
			'password' : [null, Validators.required],
			'validate' : ''
		});
	}

	observer = {
		next: response => {
			this.loginService.userIsSignedIn(true);
			this.navigateToGroupsRoute();
		},
		error: response => this.errorMessage = response.error.message
	};

	signIn(userCredentials) {
		this.errorMessage = '';
		this.loginService.signIn(this.createUser(userCredentials)).subscribe(this.observer);
	}

	signUp(userCredentials) {
		this.errorMessage = '';
		this.loginService.signUp(this.createUser(userCredentials)).subscribe(this.observer);
	}

	private navigateToGroupsRoute() {
		this.router.navigate(['/groups']);
	}

	private createUser(userCredentials): Users {
		const user: Users = {email: userCredentials.email, password: userCredentials.password, role: 'ROLE_ADMIN'};
		return user; 
	}
}
