import { Users } from './Users';

export class Messages {

	constructor(
		public user: Users,
		public content: string
	) {}
}