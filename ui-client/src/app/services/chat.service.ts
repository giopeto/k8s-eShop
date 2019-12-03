import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import * as io from 'socket.io-client';

import { Users } from '../models/Users';
import { Messages } from '../models/Messages';
import { EventTypes } from '../models/EventTypes';
import { Events } from '../models/Events';

@Injectable()
export class ChatService {

	public user: Users;
	public socketSubject: Subject<any> = new Subject<any>();
	private apiUrl: string = 'http://localhost:3000';  // URL to web api
	private socket: any = io(this.apiUrl);
	private message: Subject<string> = new Subject<string>();

	constructor(private http: HttpClient) {
		this.listenOnChatMessage();
		this.listenOnUserAdded();
	}

	setUser(user: Users): void {
		this.user = user;
		let event: Events = {type: EventTypes.USER_ADDED, payload:  this.user};
		this.emitToServer(event);
	}

	getUser(): Users {
		return this.user;
	}

	emitToServer(event: Events): void {
		this.socket.emit(event.type, event.payload);
	}

	listenOnChatMessage(): void {
		this.socket.on(EventTypes.CHAT_MESSAGE, data => {
			let event: Events = {type: EventTypes.CHAT_MESSAGE, payload:  data};
			this.socketSubject.next(event)
		});
	}

	listenOnUserAdded(): void {
		this.socket.on(EventTypes.USER_ADDED, data => {
			let event: Events = {type: EventTypes.USER_ADDED, payload: data};
			this.socketSubject.next(event)
		});
	}

	createMessage(content: string): Messages {
		let message: Messages = {user: this.user, content};
		return message;
	}

}
