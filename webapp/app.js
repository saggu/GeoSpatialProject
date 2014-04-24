
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')

var app = module.exports = express.createServer()
, io = require('socket.io').listen(app);;

var redis = require("redis"),
        redisclient = redis.createClient();

// Configuration

app.configure(function(){
  app.set('views', __dirname + '/views');
  app.set('view engine', 'jade');
  app.set('view options', {
    layout: false
  });
  app.use(express.bodyParser());
  app.use(express.methodOverride());
  app.use(app.router);
  app.use(express.static(__dirname + '/public'));
});

app.configure('development', function(){
  app.use(express.errorHandler({ dumpExceptions: true, showStack: true })); 
});

app.configure('production', function(){
  app.use(express.errorHandler()); 
});

// Routes

app.get('/', routes.index);

app.listen(3000);

io.sockets.on('connection', function (socket) {
  redisclient.on("subscribe", function (channel, count) {
    console.log("subscribe to: " + channel);
  });

  redisclient.on("message", function (channel, message) {
      console.log("client1 channel " + channel + ": " + message);
      socket.emit('news', { hello: message });
  });

  redisclient.subscribe("tweet2location");

  socket.on('my other event', function (data) {
    console.log(data);
  });
});



console.log("Express server listening on port %d in %s mode", app.address().port, app.settings.env);
