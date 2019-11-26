import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { catchError } from 'rxjs/operators';

import { Users } from '../models/Users';
import { HTTP_OPTIONS, getApiBaseUrl } from '../common/ApiConstants';

@Injectable()
export class LoginService {

	public userIsSignedInSubject: Subject<any> = new Subject<any>();
	
	private apiUrl = getApiBaseUrl('users') + `/users`;
	
	constructor(private http: HttpClient) { }

	signIn(user: Users) {
		return this.http.post<Users>(`${this.apiUrl}/signIn`, user, HTTP_OPTIONS);
	}

	signUp(user: Users) {
		return this.http.post<Users>(`${this.apiUrl}/signUp`, user, HTTP_OPTIONS);
	}

	signOut() {
		return this.http.get(`${this.apiUrl}/signOut`, HTTP_OPTIONS);
	}

	geCurrentUser() {
		return this.http.get<Users>(this.apiUrl, HTTP_OPTIONS);
	}

	userIsSignedIn(isUserIsSignedIn) {
		this.userIsSignedInSubject.next(isUserIsSignedIn);
	}
}
