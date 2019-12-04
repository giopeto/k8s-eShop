class SockerIo {
	
	constructor(io) {
		this.io = io;
	}

	listen() {
		const allEvents = ['user-added', 'chat-message'];
		let users = [];
		let checkUsers = {};
		this.io.on('connection', function(socket){
			console.info('SockerIo Connect Event');
			socket.on('disconnect', function(){
				console.info('SockerIo Disconnect Event');
			});

			allEvents.forEach(event => {
				socket.on(event, function(eventData){
					console.info('SockerIo Receive Event: ', eventData);
					if (event === 'user-added' && !checkUsers[eventData.id]) {
						//console.log('1 Event: ', event, ', Data: ', eventData);
						users.push(eventData);
						eventData = users;
						checkUsers[eventData.id] = 1;
					} else if (checkUsers[eventData.id]) {
						console.log('2 Event: ', event, ', Data: ', eventData);
					}

					//console.log('ALL USERS: ', users);

					socket.broadcast.emit(event, eventData);
				});
			});
		});
	}
  


}

module.exports = SockerIo;