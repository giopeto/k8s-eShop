import { EventTypes } from "../models/EventTypes";
import { Events } from "../models/Events";

class SockerIo {

	private io;
	private checkUsers;

	constructor(io) {
		this.io = io;
		this.checkUsers = {};
		this.listen();
	}

	listen() {
		const allEventTypes: EventTypes[] = [EventTypes.USER_ADDED, EventTypes.CHAT_MESSAGE];
		
		this.io.on('connection', socket => {
			allEventTypes.forEach(eventType => 
				socket.on(eventType, (event: Events) => this.eventHandler(socket, event))
			);
		});
	}

	private eventHandler(socket: any, event: Events) {
		switch(event.type) {
			case EventTypes.USER_ADDED:
				if(!this.checkUsers[event.payload.id]) {
					this.checkUsers[event.payload.id] = 1;
					socket.broadcast.emit(event.type, event.payload);
				}
				break;
			case EventTypes.CHAT_MESSAGE:
				socket.broadcast.emit(event.type, event.payload);
				break;
			default:
				throw new Error(`Unknown event: ${event.type}`);
		}	
	}
	
}

export default SockerIo;