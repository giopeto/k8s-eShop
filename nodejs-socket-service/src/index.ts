import express from 'express';
import http from 'http';
import mongoose from 'mongoose';
import bodyParser from 'body-parser';
import cors from 'cors';

import SocketIo from './socket/SockerIo';
import dbConfig from './config/db/config';
import { api, actuator } from './routes';

const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cors())

app.use('/api', api);
app.use('/actuator', actuator);

mongoose.connect(dbConfig.url, { useUnifiedTopology: true, useNewUrlParser: true });
mongoose.set('debug', false);

const server = http.createServer(app);

const io =  require('socket.io')(server);
const socketListener = new SocketIo(io);

const port = process.env.PORT || 3000;

server.listen(port, () => console.info('\n########## Nodejs Socket Service Ready ##########\n'));