class SockerIo {
	
	constructor(io) {
		this.io = io;
	}

	listen() {
		const allEvents = ['user-added', 'chat-message'];
		let checkUsers = {};

		this.io.on('connection', function(socket){
			console.info('SockerIo Connect Event');
			socket.on('disconnect', function(){
				console.info('SockerIo Disconnect Event');
			});

			allEvents.forEach(event => {
				socket.on(event, function(eventData){
					console.info('SockerIo Receive Event: ', event, ', with data: ', eventData);

					if (event === 'user-added' && !checkUsers[eventData.id]) {
						checkUsers[eventData.id] = 1;
					}
					socket.broadcast.emit(event, eventData);
				});
			});
		});
	}
	
}

module.exports = SockerIo;