var data = {
  'landmarks':[
    { 'name': 'Statue of Liberty',
      'latitude': 40.689757,
      'longitude': -74.0451453,
      'total': 20,
      'image': 'http://upload.wikimedia.org/wikipedia/commons/d/d3/Statue_of_Liberty,_NY.jpg',
      'top-countries':[
        {
          'name': 'Italy',
          'latitude': 41.29246,
          'longitude': 12.5736108,
          'visitors':10
        },
        {
          'name': 'Australia',
          'latitude': -26.4425664,
          'longitude': 133.281323,
          'visitors':5
        },
        {
          'name': 'Argentina',
          'latitude': -38.4192641,
          'longitude': -63.5989206,
          'visitors':5
        }
      ]
     },
     { 'name': 'Taj Mahal',
      'latitude': 27.175015,
      'longitude': 78.042155,
      'total': 50,
      'image': 'http://images.boomsbeat.com/data/images/full/2648/32-jpg.jpg',
      'top-countries':[
        {
          'name': 'Italy',
          'latitude': 41.29246,
          'longitude': 12.5736108,
          'visitors':10
        },
        {
          'name': 'Australia',
          'latitude': -26.4425664,
          'longitude': 133.281323,
          'visitors':5
        },
        {
          'name': 'Argentina',
          'latitude': -38.4192641,
          'longitude': -63.5989206,
          'visitors':5
        }
      ]
     }
  ]
}

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

google.maps.event.addDomListener(window, 'load', initialize);

function initialize() {
  var mapOptions = {
    zoom: 3,
    center: new google.maps.LatLng(34, -40.605)
  };

  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);

  
  //parse the landmarks
  data['landmarks'].forEach(function(landmark){
    var dataLandmark = {
      'name': landmark['name'],
      'total': landmark['total'],
      'image': landmark['image'],
      'top-countries': landmark['top-countries']
    };
    createLandmarkMarker(landmark['latitude'],landmark['longitude'],dataLandmark);
    
    //markersByLandmarks[landmark['name']] = marker;

  });

  var socket = io.connect('http://localhost:3000');
  socket.on('news', function (data) {
    console.log(data);
    socket.emit('my other event', { my: 'data' });
  });
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
  google.maps.event.addListener(landmarkMarker, 'click', createCountryMarker);
}

function createCountryMarker(event){
  map.setZoom(3);
  map.panTo(this.position);

  landmarkSelected = this;
  var ds = this.dataLandmark;

  //create infowindow
  infowindow = createLandmarkInfoWindow(ds['name'],ds['total'],ds['image']);
  infowindow.open(map,this);

  //create markers
  ds['top-countries'].forEach(function(country){
    countryMarker = new google.maps.Marker({
      map: map,
      draggable: false,
      position: new google.maps.LatLng(country['latitude'], country['longitude']),
      icon: pinCountryImage,
      shadow: pinShadow
    });
    var countryOptions = {
      strokeColor: '#FF0000',
      strokeOpacity: 0.8,
      strokeWeight: 2,
      fillColor: '#FF0000',
      fillOpacity: 0.35,
      map: map,
      center: countryMarker.position,
      radius: 2000000
    };
    countryCircle = new google.maps.Circle(countryOptions);

    createCurvedLine(landmarkSelected,countryMarker);
  });
}

function createLandmarkInfoWindow(name,total,image){
  var contentString = '<div id="content">'+
      '<div id="siteNotice">'+
      '</div>'+
      '<h2 id="firstHeading" class="firstHeading">'+ name + '</h2>'+
      '<div id="bodyContent">'+
      '<p><b>Total tweets:</b>' + total + '</p>'+
      '<img src="'+ image +'" width="200px" height="200px" />'+
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


