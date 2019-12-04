import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/observable/of';

import { Groups } from '../models/Groups';
import { HTTP_OPTIONS, HTTP_OPTIONS_MULTIPART, FILES_INGRESS_PATH, FILES_SERVICE_PREFIX, getApiBaseUrl } from '../common/ApiConstants';

@Injectable()
export class FilesService {

	private apiUrl = getApiBaseUrl(FILES_INGRESS_PATH, FILES_SERVICE_PREFIX) + '/files';

	formData: FormData = new FormData();
	files = [];

	constructor(private http: HttpClient) {}

	getFilesUrl() {
		return this.apiUrl;
	}	

	save(domainId) {
		if (this.files.length) {
			this.formData.append('domainId', domainId);
			this.files.forEach(file => this.formData.append('files', file));
			this.files = [];
			return this.http.post<any>(`${this.apiUrl}`, this.formData, HTTP_OPTIONS_MULTIPART);
		} else {
			this.files = [];
			return Observable.of([]);
		}
	}

	getByDomainId(domainId) {
		return this.http.get<any>(`${this.apiUrl}/${domainId}`, HTTP_OPTIONS);
	}

	remove({domainId, id}) {
		return this.http.delete(`${this.apiUrl}/${id}/domainId/${domainId}`, HTTP_OPTIONS);
	}

	removeLocalFile(fileForRemove) {
		this.files.forEach((file, index) => {
			if (file === fileForRemove) {
				this.files.splice(index, 1);
			}
		});
	}

	resetFormData() {
		this.formData = new FormData();
	}

	updateFormData(name, payload) {
		this.files.push(payload);
	}
}
