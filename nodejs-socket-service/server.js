// Get dependencies
const express = require('express');
const path = require('path');
const app = express();
const http = require('http');

const bodyParser = require('body-parser');

// Get our API routes
const api = require('./server/routes/api');

// Parsers for POST data
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

// Set our api routes
app.use('/api', api);

/**
 * Get port from environment and store in Express.
 */
const port = process.env.PORT || '3000';
app.set('port', port);

/**
 * Create HTTP server.
 */
const server = http.createServer(app);
const io = require('socket.io')(server);

const allEvents = ['user-added', 'chat-message'];
let users = [];
let checkUsers = {};
io.on('connection', function(socket){
	
	socket.on('disconnect', function(){
	});

	allEvents.forEach(event => {
		socket.on(event, function(eventData){
			
			if (event === 'user-added' && !checkUsers[eventData.id]) {
				console.log('1 Event: ', event, ', Data: ', eventData);
				users.push(eventData);
				eventData = users;
				checkUsers[eventData.id] = 1;
			} else if (checkUsers[eventData.id]) {
				console.log('2 Event: ', event, ', Data: ', eventData);
			}

			console.log('ALL USERS: ', users);

			socket.broadcast.emit(event, eventData);
		});
	});
});

/**
 * Listen on provided port, on all network interfaces.
 */
server.listen(port, () => console.log(`API running on localhost:${port}`));