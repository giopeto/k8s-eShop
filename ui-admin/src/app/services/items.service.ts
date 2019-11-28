import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';

import { Items } from '../models/Items';
import { HTTP_OPTIONS, STORE_INGRESS_PATH, STORE_SERVICE_PREFIX, getApiBaseUrl } from '../common/ApiConstants';

@Injectable()
export class ItemsService {
	
	private apiUrl = getApiBaseUrl(STORE_INGRESS_PATH, STORE_SERVICE_PREFIX) + '/items';

	constructor(private http: HttpClient) { }

	get() {
		return this.http.get<Items[]>(`${this.apiUrl}`, HTTP_OPTIONS);
	}

	getById(id) {
		return this.http.get<Items>(`${this.apiUrl}/${id}`, HTTP_OPTIONS);
	}

	save(item) {
		return this.http.post<Items>(`${this.apiUrl}`, item, HTTP_OPTIONS);
	}

	update(item) {
		return this.http.put<Items>(`${this.apiUrl}/${item.id}`, item, HTTP_OPTIONS);
	}

	delete(id) {
		return this.http.delete(`${this.apiUrl}/${id}`, HTTP_OPTIONS);
	}
}
