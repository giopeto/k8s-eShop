import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';

import { Groups } from '../models/Groups';
import { HTTP_OPTIONS, STORE_INGRESS_PATH, STORE_SERVICE_PREFIX, getApiBaseUrl } from '../common/ApiConstants';

@Injectable()
export class GroupsService {

	private apiUrl = getApiBaseUrl(STORE_INGRESS_PATH, STORE_SERVICE_PREFIX) + '/groups';
	
	constructor(private http: HttpClient) { }

	get() {
		return this.http.get<Groups[]>(`${this.apiUrl}`, HTTP_OPTIONS);
	}

	getById(id) {
		return this.http.get<Groups>(`${this.apiUrl}/${id}`, HTTP_OPTIONS);
	}

	save(group) {
		return this.http.post<Groups>(`${this.apiUrl}`, group, HTTP_OPTIONS);
	}

	update(group) {
		return this.http.put<Groups>(`${this.apiUrl}/${group.id}`, group, HTTP_OPTIONS);
	}

	delete(id) {
		return this.http.delete(`${this.apiUrl}/${id}`, HTTP_OPTIONS);
	}
}
