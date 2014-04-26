
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
      if(channel == "tweet2location"){
        socket.emit('tweet', message);
      }else if(channel == "test_tweet2location"){
        var testjson = '{"tweetText":"Sagrada Familia. #Barcelona #Bcn #Easter #Semana #Santa http://t.co/Le63VqWQ0l","tweetUser":"Naroa22794","tweetUserCountry":"","tUserCLongitude":"","tUserCLatitude":"","landmarkName":"Sagrada Familia","landmarkLongitute":"E 2°10?28\u0027\u0027","landmarkLatitude":"N 41°24?13\u0027\u0027? "}';
        /*var testjson = '{"tweetText":"On the road, in the middle of the mountain, back home! #cloud #road #travel #morning #holidays http://t.co/F71XfU1i87","tweetUser":"Marie_lef_nor","tweetUserCountry":"Spain","tUserCLongitude":"-3.69063","tUserCLatitude":"40.42526","landmarkName":"Middle of the Earth","landmarkLongitute":"W 78° 27\u0027 21.28\u0027\u0027","landmarkLatitude":"S  0° 0\u0027 7.79\u0027\u0027"}';*/
        socket.emit('tweet',testjson);
      }
  });

  redisclient.subscribe("tweet2location");
  redisclient.subscribe("test_tweet2location");
});



console.log("Express server listening on port %d in %s mode", app.address().port, app.settings.env);
