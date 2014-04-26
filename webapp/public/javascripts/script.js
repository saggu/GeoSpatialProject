var pinLandmarkImage = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_icon&chld=amusement|0033FF",
  new google.maps.Size(21, 34),
  new google.maps.Point(0,0),
  new google.maps.Point(10, 34));

var pinCountryImage = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_icon&chld=airport|009933",
  new google.maps.Size(21, 34),
  new google.maps.Point(0,0),
  new google.maps.Point(10, 34));

var pinShadow = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_shadow",
  new google.maps.Size(40, 37),
  new google.maps.Point(0, 0),
  new google.maps.Point(12, 35));


var map;
var infowindow;
var landmarkSelected;
var countrySelected;

var markersByLandmarks = {};
var allCountryMarkers = [];

google.maps.event.addDomListener(window, 'load', initialize);

function initialize() {
  var mapOptions = {
    zoom: 3,
    center:new google.maps.LatLng(30.599638, -41.467989),
    mapTypeId: google.maps.MapTypeId.TERRAIN
  };

  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);

  //get landmarks data
  data = {};
  data['landmarks'] = [];
  jQuery.getJSON('/files/landmarks.json', function(db) {
    db.forEach(function(l){

      var dataLandmark = {
        'name': l['name'],
        'total': 0,
        'image': '/images/'+l['name']+'.jpg',
        'top-countries': {}
      };

      var parts = decodeURIComponent(l['latitude']).split(/[^\d\w]+/);
      var lat = ConvertDMSToDD(parseInt(parts[1]), parseInt(parts[2]), parseInt(parts[3]), parts[0]);
      parts = decodeURIComponent(l['longitude']).split(/[^\d\w]+/);
      var lng = ConvertDMSToDD(parseInt(parts[1]), parseInt(parts[2]), parseInt(parts[3]), parts[0]);
      
      marker = createLandmarkMarker(lat,lng,dataLandmark);
      markersByLandmarks[l['name']] = marker;
        
      data['landmarks'].push(l) 
    });
  });


  var socket = io.connect('http://localhost:3000');
  socket.on('tweet', function (tweet) {
    parseGeoTweet(JSON.parse(tweet));
    //parseGeoTweet(tweet);
  });
}

function ConvertDMSToDD(days, minutes, seconds, direction) {
    var dd = days + (minutes/60) + seconds/(60*60);
    dd = parseFloat(dd);
    if (direction == "S" || direction == "W") {
      dd = dd * (-1);
    } // Don't do anything for N or E
    return dd;
}

function parseGeoTweet(tweet){
  removeCountryMarkers();

  Growl.Smoke({
    title: tweet.tweetUser,
    text: tweet.tweetText,
    image: 'images/twitter_icon.png',
    duration: 3
  });
  setTimeout(function(){
    populate(tweet);
  },3000);
  
}

function populate(tweet){
  removeLandmarkMarkers();
  var temp_landmark = tweet.landmarkName;
  var temp_country = tweet.tweetUserCountry;

  //let's edit the data associate to the marker
  var marker = markersByLandmarks[temp_landmark];
  marker.setMap(map);
  marker.dataLandmark.total = marker.dataLandmark.total + 1;
  
  //now we need to check if the country exists
  if(temp_country != ""){
    if(temp_country in marker.dataLandmark["top-countries"]){
      marker.dataLandmark["top-countries"][temp_country].visitors += 1;
    }
    else{
      marker.dataLandmark["top-countries"] = {};
      marker.dataLandmark["top-countries"][temp_country] = {};
      marker.dataLandmark["top-countries"][temp_country].latitude = tweet.tUserCLatitude;
      marker.dataLandmark["top-countries"][temp_country].longitude = tweet.tUserCLongitude;
      marker.dataLandmark["top-countries"][temp_country].visitors = 1;
    }  
  }
  
  countrySelected = temp_country;
  landmarkSelected = marker;
  landmarkSelected.setAnimation(google.maps.Animation.BOUNCE);
  infowindow = createLandmarkInfoWindow(marker.dataLandmark['name'],marker.dataLandmark['total'],marker.dataLandmark['image'],countrySelected,marker.dataLandmark["top-countries"][countrySelected].visitors);
  infowindow.open(map,landmarkSelected);

  createCountryMarkers(marker.dataLandmark);
}

function removeLandmarkMarkers(){
  for(l in markersByLandmarks){
    markersByLandmarks[l].setMap(null);
  }
}

function removeCountryMarkers(){
  if(landmarkSelected){
    infowindow.close();
    landmarkSelected.setAnimation(null);
  }
  allCountryMarkers.forEach(function(m){
    m.setMap(null);
  });
  allCountryMarkers = [];
}
  

function createLandmarkMarker(lat,lon,dataLandmark){
  landmarkMarker = new google.maps.Marker({
    map: map,
    draggable: false,
    position: new google.maps.LatLng(lat, lon),
    icon: pinLandmarkImage,
    shadow: pinShadow
  });
  landmarkMarker.dataLandmark = dataLandmark;
  /*google.maps.event.addListener(landmarkMarker, 'click', function(){
    landmarkSelected = this;
    createCountryMarkers(this.dataLandmark);
  });*/

  return landmarkMarker;
}

function createCountryMarkers(dataLandmark){
  //create markers
  for(country in dataLandmark['top-countries']){
    countryMarker = new google.maps.Marker({
      map: map,
      draggable: false,
      position: new google.maps.LatLng(dataLandmark['top-countries'][country].latitude, dataLandmark['top-countries'][country].longitude),
      icon: pinCountryImage,
      shadow: pinShadow
    });
    /*var countryOptions = {
      strokeColor: '#FF0000',
      strokeOpacity: 0.8,
      strokeWeight: 2,
      fillColor: '#FF0000',
      fillOpacity: 0.35,
      map: map,
      center: countryMarker.position,
      radius: 500000 * (dataLandmark['top-countries'][country].visitors/dataLandmark['total']),
      scale:20
    };
    countryCircle = new google.maps.Circle(countryOptions);*/
    cur = createCurvedLine(landmarkSelected,countryMarker);
    
    if(country == countrySelected){
      countryMarker.setAnimation(google.maps.Animation.BOUNCE);
    }
    //zoom to markers
    var latlngbounds = new google.maps.LatLngBounds();
    latlngbounds.extend(landmarkSelected.position);
    latlngbounds.extend(countryMarker.position);
    
    map.fitBounds(latlngbounds);

    allCountryMarkers.push(countryMarker);
    allCountryMarkers.push(cur);
    //allCountryMarkers.push(countryCircle);
  }
}

function createLandmarkInfoWindow(name,total,image,country,tcountry){
  var ctext = '';
  if(country){
    ctext = '<p><b>From ' + country+ '</b>: ' + tcountry + '</p>';
  }
  var contentString = '<div id="content">'+
      '<h3 id="firstHeading" class="firstHeading">'+ name + '</h3>'+
      '<div id="bodyContent">'+
      '<p><b>Total tweets:</b>' + total + '</p>'+
      ctext +
      '<img src="'+ image +'" width="120px" height="120px" />'+
      '</div>'+
      '</div>';

  var infowindow = new google.maps.InfoWindow({
      content: contentString
  });
  return infowindow;
}

function createCurvedLine(mLandmark,mCountry){
  var lineSymbol = {
    path: google.maps.SymbolPath.CIRCLE,
    scale: 3,
    strokeColor: '#393'
  };

  var geodesicOptions = {
    strokeColor: '#00FF66',
    strokeOpacity: 1.0,
    strokeWeight: 3,
    geodesic: true,
    map: map,
    icons: [{
      icon: lineSymbol,
      offset: '100%'
    }]
  };

  geodesicPoly = new google.maps.Polyline(geodesicOptions);
  var path = [mCountry.getPosition(),mLandmark.getPosition()];
  geodesicPoly.setPath(path);

  animateCircle(geodesicPoly);

  return geodesicPoly;
}



function animateCircle(line) {
    var count = 0;
    window.setInterval(function() {
      count = (count + 1) % 200;

      var icons = line.get('icons');
      icons[0].offset = (count / 2) + '%';
      line.set('icons', icons);
  }, 20);
}


