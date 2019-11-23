import { HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';

const API_VERSION = 'v1';

export const HTTP_OPTIONS = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
	withCredentials: true
};

export const HTTP_OPTIONS_MULTIPART = {
	withCredentials: true
};

export function getApiBaseUrl(servicePrefix: string, apiVersion: string = API_VERSION): string {
	return `${environment.apiUrl}/${servicePrefix}/${servicePrefix}/${apiVersion}`;
}