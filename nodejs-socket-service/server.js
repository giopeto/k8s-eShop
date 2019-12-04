const express = require('express');
const path = require('path');
const app = express();
const http = require('http');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
var cors = require('cors')

// Config
const SocketIo = require('./server/socket/SockerIo');
const dbConfig = require('./server/config/db/config');
// Routes
const api = require('./server/routes/api');
const actuator = require('./server/routes/actuator');

// configuration ===============================================================
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cors())

app.use('/api', api);
app.use('/actuator', actuator);

mongoose.connect(dbConfig.url, { useUnifiedTopology: true, useNewUrlParser: true });
mongoose.set('debug', false);

/**
 * Create HTTP server.
 */
const server = http.createServer(app);

const io = require('socket.io')(server);
const socketListener = new SocketIo(io).listen();

server.listen(3000, () => console.info('\n########## Nodejs Socket Service Ready ##########\n'));