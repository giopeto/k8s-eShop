import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { LoginService } from './services/login.service';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
	
	userIsSignedIn: boolean = false;

	constructor(private loginService: LoginService, private router: Router) { }

	ngOnInit() {
		this.loginService.geCurrentUser().subscribe(
			user => this.userIsSignedIn = true,
			error => this.redirectToLogn()
		);
		this.loginService.userIsSignedInSubject.subscribe(isUserIsSignedIn => {
			this.userIsSignedIn = isUserIsSignedIn;
			if (!this.userIsSignedIn) {
				this.redirectToLogn();
			}
		});
	}

	redirectToLogn() {
		this.router.navigate(['/login']);
	}
}
