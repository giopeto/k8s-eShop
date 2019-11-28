import { HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';

const API_VERSION = 'v1';
const API_PREFIX = 'api';

export const AUTHENTICATION_INGRESS_PATH = 'authentication-svc';
export const AUTHENTICATION_SERVICE_PREFIX = 'authentication';

export const STORE_INGRESS_PATH = 'store-svc';
export const STORE_SERVICE_PREFIX = 'store';

export const FILES_INGRESS_PATH = 'files-svc';
export const FILES_SERVICE_PREFIX = 'files';

export const HTTP_OPTIONS = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
	withCredentials: true
};

export const HTTP_OPTIONS_MULTIPART = {
	withCredentials: true
};

export function getApiBaseUrl(ingressPath: string, servicePrefix: string, apiVersion: string = API_VERSION): string {
	return `${environment.apiUrl}/${API_PREFIX}/${ingressPath}/${servicePrefix}/${apiVersion}`;
}