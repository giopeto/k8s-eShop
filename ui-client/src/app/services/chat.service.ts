import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs/Subject';
import * as io from 'socket.io-client';

import { Users } from '../models/Users';
import { Messages } from '../models/Messages';
import { EventTypes } from '../models/EventTypes';
import { Events } from '../models/Events';
import { environment } from './../../environments/environment';


@Injectable()
export class ChatService {

	public socketSubject: Subject<Events> = new Subject<Events>();
	private socketIOPath = '/api/nodejs-socket-svc/socket.io';
	private user: Users; 
	
	//private socket: any = io('http://localhost:3000'); // Test url
	private socket: any = io(environment.apiUrl, {path: this.socketIOPath});

	constructor(private http: HttpClient) {
		this.listenOnChatMessage();
		this.listenOnUserAdded();
	}

	emitToServer(event: Events): void {
		this.socket.emit(event.type, event);
	}

	listenOnChatMessage(): void {
		this.socket.on(EventTypes.CHAT_MESSAGE, payload => {
			let event: Events = {type: EventTypes.CHAT_MESSAGE, payload};
			this.socketSubject.next(event)
		});
	}

	listenOnUserAdded(): void {
		this.socket.on(EventTypes.USER_ADDED, payload => {
			let event: Events = {type: EventTypes.USER_ADDED, payload};
			this.socketSubject.next(event)
		});
	}

	setUser(user: Users): void {
		this.user = user;
		let event: Events = {type: EventTypes.USER_ADDED, payload:  this.user};
		this.emitToServer(event);
	}

	getUser(): Users {
		return this.user;
	}

	createMessage(content: string): Messages {
		let message: Messages = {user: this.user, content};
		return message;
	}

}
