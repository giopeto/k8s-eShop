import { HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';

// Kubernetes API_HOST
// const API_HOST = 'http://192.168.1.102:30002';
const API_PREFIX = 'api';
const API_VERSION = 'V1';


/* const AUTHENTICATION_SERVICE_PREFIX = 'authentication';
const AUTHENTICATION_SERVICE_VERSION = 'V1';

const STORE_SERVICE_PREFIX = 'store-service/store';
const STORE_SERVICE_VERSION = 'V1';

const FILES_SERVICE_PREFIX = 'zuul/files-service/files';
const FILES_SERVICE_VERSION = 'V1';

export const API_BASE_URL = `${API_HOST}/${API_PREFIX}/${API_VERSION}`;
export const AUTHENTICATION_SERVICE_BASE_URL = `${API_HOST}/${AUTHENTICATION_SERVICE_PREFIX}/${AUTHENTICATION_SERVICE_VERSION}`;
export const STORE_SERVICE_BASE_URL = `${API_HOST}/${STORE_SERVICE_PREFIX}/${STORE_SERVICE_VERSION}`;
export const FILES_SERVICE_BASE_URL = `${API_HOST}/${FILES_SERVICE_PREFIX}/${FILES_SERVICE_VERSION}`; */


export const HTTP_OPTIONS = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
	withCredentials: true
};

export const HTTP_OPTIONS_MULTIPART = {
	withCredentials: true
};

export function getApiBaseUrl(seervicePrefix: string): string {
	return `${environment.apiUrl}/${seervicePrefix}/${seervicePrefix}/${API_VERSION}`;
}