import { EventTypes } from './EventTypes';

export class Events {

	constructor(
		public type: EventTypes,
		public payload: any
	) {}
}