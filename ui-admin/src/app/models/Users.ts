export class Users {

	constructor(
		public email: string,
		public password: string,
		public role: string,
		public id?: string
	) {}
}