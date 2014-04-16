package gazetteer;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class PopularLandmarks {
	
	private String name;
	private String country;
	private String city;
	private String latitude;
	private String longitude;
	
	public PopularLandmarks(String name, String city, String country,String latitude, String longitude)
	{
		this.setName(name);
		this.setCity(city);
		this.setCountry(country);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}
	
	public static Multimap<String, PopularLandmarks> CreateLandmarks()
	{
		Multimap<String, PopularLandmarks> landmarks = ArrayListMultimap.create();
		landmarks.put("S",new PopularLandmarks("Statue of Liberty","New York County","United States","N 40° 41' 20''","W 74° 2' 39''"));	
		landmarks.put("E",new PopularLandmarks("Eiffel Tower","Paris","France","N 48° 51' 30''","E 2° 17' 40''"));	
		landmarks.put("S",new PopularLandmarks("St. Basil’s Cathedral","Moscow","Russia","N 55° 45′ 9″","E 37° 37′ 23″"));	
		landmarks.put("B",new PopularLandmarks("Blue Domed Church","Santorini","Greece","",""));	
		landmarks.put("G",new PopularLandmarks("Great Sphinx of Giza","Giza","Egypt","N 29° 58' 31''","E 31° 8' 15''"));	
		landmarks.put("T",new PopularLandmarks("The Pyramids","Giza","Egypt","N 29° 58' 44''","E 31° 8' 1''"));	
		landmarks.put("L",new PopularLandmarks("Little Mermaid","Copenhagen","Denmark","N 55° 41' 34''","E 12° 35' 56''"));	
		landmarks.put("P",new PopularLandmarks("Palace of Versailles","Versailles","France","N 48° 48′ 15.85″ ","E 2° 7′ 23.38″"));	
		landmarks.put("G",new PopularLandmarks("Great Wall of China","","China","N 39° 58' 0''","E 119° 47' 43''"));	
		landmarks.put("T",new PopularLandmarks("Taj Mahal","Agra","India","N 27° 10' 30''","E 78° 2' 31''"));	
		landmarks.put("M",new PopularLandmarks("Machu Picchu","Cusco","Peru","S 13° 9' 48''","W 72° 32' 44''"));	
		landmarks.put("B",new PopularLandmarks("Big Ben","London","United Kingdom","N 51° 30' 2''","W 0° 7' 28''"));	
		landmarks.put("B",new PopularLandmarks("Burj Al Arab","Dubai","United Arab Emirates","N 25° 8' 29''","E 55° 11' 7''"));	
		landmarks.put("T",new PopularLandmarks("Tower of Pisa","Pisa","Italy","N 43° 43' 22''","E 10° 23' 47''"));	
		landmarks.put("C",new PopularLandmarks("Christ The Redeemer","Rio de Janeiro","Brazil","S 22° 57' 6''","W 43° 12' 39''"));	
		landmarks.put("L",new PopularLandmarks("Lascaux ","Aquitaine","France","N 45° 3' 0''","E 1° 11' 0''"));	
		landmarks.put("M",new PopularLandmarks("Mecca","Makkah","Saudi Arabia","N 21° 25' 35''","E 39° 49' 32''"));	
		landmarks.put("L",new PopularLandmarks("Loch Ness","Inverness","Scotland","N 57° 18' 0''","W 4° 27' 0''"));	
		landmarks.put("M",new PopularLandmarks("Mont Saint-Michel","Normany","France","N 48° 38' 9''","W 1° 30' 41''"));	
		landmarks.put("B",new PopularLandmarks("Bran Castle","Bran ","Romania","N 45°30′54″ ","E 25°22′02″"));	
		landmarks.put("H",new PopularLandmarks("Hagia Sophia","Istanbul","Turkey","N 41° 0' 30''","E 28° 58' 48''"));	
		landmarks.put("B",new PopularLandmarks("Brandenburg Gate","Berlin","Germany","N 52° 30' 58''","E 13° 22' 39''"));	
		landmarks.put("A",new PopularLandmarks("Acropolis","Attica","Greece","N 37° 58' 18''","E 23° 43' 32''"));	
		landmarks.put("S",new PopularLandmarks("Sagrada Familia","Barcelona","Spain","N 41°24′13″ ","E 2°10′28″"));	
		landmarks.put("U",new PopularLandmarks("Uluru","Northern Territory","Australia","S 25° 20' 39''","E 131° 1' 58''"));	
		landmarks.put("N",new PopularLandmarks("Neuschwanstein Castle","Bavaria","Germany","N 47° 33' 25''","E 10° 44' 57''"));	
		landmarks.put("M",new PopularLandmarks("Mount Fuji","Honshu Island","Japan","N 35° 22' 0''","E 138° 44' 0''"));	
		landmarks.put("S",new PopularLandmarks("Stonehenge ","Wiltshire","United Kingdom","N 51° 10' 44''","W 1° 49' 34''"));	
		landmarks.put("M",new PopularLandmarks("Mount Eden crater ","Auckland","New Zealand","S   36° 52′ 37.2″","E 174° 45′ 50.4″"));	
		landmarks.put("E",new PopularLandmarks("Easter Island","Valparaíso","Chile","S 27° 7' 0''","W 109° 22' 0''"));	
		landmarks.put("C",new PopularLandmarks("Capitol Hill","Washington","United States","N 38° 53' 20''","W 77° 0' 0''"));
		landmarks.put("N",new PopularLandmarks("Niagara Falls ","Ontario","Canada","N 43° 5' 0''","W 79° 3' 58''"));
		landmarks.put("A",new PopularLandmarks("Angkor Wat"," Siem Reap","Cambodia","N 13° 24' 44''","E 103° 51' 59''"));
		landmarks.put("M",new PopularLandmarks("Manneken Pis","Brussels","Belgium","N 50° 50′ 41.96″"," E 4° 20′ 59.93″"));
		landmarks.put("M",new PopularLandmarks("Mount Everest","","Nepal","N 27° 59' 16''","E 86° 55' 31''"));
		landmarks.put("S",new PopularLandmarks("St. Peter’s  Basilica","Vatican City","Vatican City","N 41° 54' 8''","E 12° 27' 11''"));
		landmarks.put("M",new PopularLandmarks("Mount Rushmore","Pennington","United States","N 43° 52' 48''","W 103° 27' 31''"));
		landmarks.put("V",new PopularLandmarks("Victoria Falls","Matabeleland","Zimbabwe","S 17° 55' 0''","E 25° 51' 0''"));
		landmarks.put("G",new PopularLandmarks("Grand Canyon","Coconino","United States","N 36° 6' 12''","W 112° 6' 16''"));
		landmarks.put("N",new PopularLandmarks("Nevado Mismi","Cusco","Peru","S 15° 31' 31''","W 71° 41' 27''"));
		landmarks.put("K",new PopularLandmarks("Kotuku in","Kamakura","Japan","N 35° 19′ 0.62″","E 139° 32′ 8.63″"));
		landmarks.put("P",new PopularLandmarks("Petra","Ma'an","Jordan","N 30° 19' 47''","E 35° 26' 29''"));
		landmarks.put("T",new PopularLandmarks("Trevi Fountain","Latium","Italy","N 41° 54' 3''","E 12° 29' 0''"));
		landmarks.put("A",new PopularLandmarks("Auschwitz ","Oświęcim","Poland","N 50° 2' 13''","E 19° 10' 34''"));
		landmarks.put("C",new PopularLandmarks("Cape of Good Hope","Western Cape","South Africa","S 34° 21' 30''","E 18° 28' 21''"));
		landmarks.put("N",new PopularLandmarks("North Cape","Nordkapp","Norway","N 71° 10' 15''","E 25° 47' 3''"));
		landmarks.put("C",new PopularLandmarks("Chichen Itza","Tinum","Mexico","N 20° 41' 0''","W 88° 34' 8''"));
		landmarks.put("I",new PopularLandmarks("Inukshuk ","Foxe Peninsula","Canada","",""));
		landmarks.put("T",new PopularLandmarks("Table Mountain","Cape Town","South Africa","S 33° 57' 43''","E 18° 24' 48''"));
		landmarks.put("G",new PopularLandmarks("Golden Gate Bridge","San Francisco","United States","N 37° 49' 10''","W 122° 28' 43''"));
		landmarks.put("S",new PopularLandmarks("Sydney Opera House","Sydney","Australia","S 33° 51' 24''","E 151° 12' 54''"));
		landmarks.put("P",new PopularLandmarks("Parc Guell ","Barcelona","Spain","N 41° 24' 48''","E 2° 9' 10''"));
		landmarks.put("M",new PopularLandmarks("Mount Kilimanjaro ","Kilimanjaro ","Tanzania","S 3° 4' 33''","E 37° 21' 12''"));
		landmarks.put("F",new PopularLandmarks("Forbidden City","Beijing","China","N 39° 55' 0''","E 116° 23' 26''"));
		landmarks.put("I",new PopularLandmarks("Iguazu Falls","","Argentina","S 25° 41' 23''","W 54° 26' 37''"));
		landmarks.put("C",new PopularLandmarks("Colosseum","Rome","Italy","N 41° 53' 24''","E 12° 29' 32''"));
		landmarks.put("T",new PopularLandmarks("Twyfelfontein ","Kunene","Namibia","S 20° 34' 0''","E 14° 22' 0''"));
		landmarks.put("T",new PopularLandmarks("Tower Bridge","London","United Kingdom","N 51° 30' 18''","W 0° 4' 26''"));
		landmarks.put("B",new PopularLandmarks("Blue Mosque","Istanbul","Turkey","N 41° 0′ 19.74″","E 28° 58′ 38.59″"));
		landmarks.put("M",new PopularLandmarks("Millau Viaduct","Millau","France","N 44° 4' 58''","E 3° 1' 18''"));
		landmarks.put("L",new PopularLandmarks("Luxor Temple","Qina","Egypt","N 25° 42' 0''","E 32° 38' 21''"));
		landmarks.put("B",new PopularLandmarks("Berlin Cathedral","Berlin","Germany","N 52° 31' 8''","E 13° 24' 3''"));
		landmarks.put("F",new PopularLandmarks("Faisal Mosque","Punjab","Pakistan","N 33° 43' 44''","E 73° 2' 16''"));
		landmarks.put("K",new PopularLandmarks("Kremlin ","Moscow","Russia","N 55° 45' 6''","E 37° 37' 4''"));
		landmarks.put("E",new PopularLandmarks("Empire State Building","New York","New York","N 40° 44' 55''","W 73° 59' 9''"));
		landmarks.put("H",new PopularLandmarks("Hermitage ","Leningrad","Russia","N 59° 56' 26''","E 30° 18' 46''"));
		landmarks.put("N",new PopularLandmarks("Newgrange ","County Meath","Ireland","N 53° 41' 0''","W 6° 28' 0''"));
		landmarks.put("W",new PopularLandmarks("Waterloo ","Walloon Brabant","Belgium","N 50° 42' 52''","E 4° 23' 56''"));
		landmarks.put("C",new PopularLandmarks("Carnac ","Brittany","France","N 47° 35' 0''","W 3° 4' 43''"));
		landmarks.put("T",new PopularLandmarks("Tilicho Lake","Gandaki","Nepal","N 28° 41' 37''","E 83° 51' 4''"));
		landmarks.put("P",new PopularLandmarks("Pompeii ","Campania","Italy","N 40° 45' 0''","E 14° 29' 0''"));
		landmarks.put("W",new PopularLandmarks("Wailing Wall","Jerusalem","Israel","N 31° 46' 36''","E 35° 14' 3''"));
		landmarks.put("S",new PopularLandmarks("Sun Temple ","Konark","India","N 19° 53′ 14.8″","E 86° 5′ 40.55″"));
		landmarks.put("A",new PopularLandmarks("Abu Simbel ","Aswan","Egypt","N 22° 20' 0''","E 31° 37' 0''"));
		landmarks.put("M",new PopularLandmarks("Middle of the Earth","Quito","Ecuador","S  0° 0' 7.79''","W 78° 27' 21.28''"));
		landmarks.put("P",new PopularLandmarks("Prophet’s Mosque","Medina","Saudi Arabia","N   24° 28′ 6″","E 39° 36′ 39''"));
		landmarks.put("S",new PopularLandmarks("Sacré Coeur","Paris","France","N 48° 53' 11''","E 2° 20' 34''"));
		landmarks.put("P",new PopularLandmarks("Potala Palace","Lhasa","Tibet","N 29° 39' 32''","E 91° 7' 0''"));
		landmarks.put("S",new PopularLandmarks("Skellig Michael ","County Kerry","Ireland","N 51° 46' 0''","W 10° 32' 0''"));
		landmarks.put("A",new PopularLandmarks("Angel Falls","Bolivar","Venenzuela","N 5° 57' 0''","W 62° 30' 0''"));
		landmarks.put("T",new PopularLandmarks("The Louvre ","Paris","France","N 48° 51' 39''","E 2° 20' 9''"));
		landmarks.put("A",new PopularLandmarks("Atomium ","Bruxelles","Belgium","N 50° 53' 41''","E 4° 20' 28''"));
		landmarks.put("W",new PopularLandmarks("White Cliffs of Dover","Dover","United Kingdom","N 51° 8′ 24″ ","E 1° 22′ 12″"));
		landmarks.put("M",new PopularLandmarks("Minaret of Jam","Shahrak","Afghanistan","N 34° 23′ 48″","E 64° 30′ 58″ "));
		landmarks.put("G",new PopularLandmarks("Golden Temple","Amritsar","India","N 31° 37' 11''","E 74° 52' 34''"));
		landmarks.put("R",new PopularLandmarks("Rock of Gibraltar","Gibraltar","United Kingdom","N 36° 7′ 33″ ","W 5° 20′ 35″"));
		landmarks.put("L",new PopularLandmarks("Lotus Temple","New Delhi","India","N 28° 33' 12''","E 77° 15' 30''"));
		landmarks.put("H",new PopularLandmarks("Half Dome","Mariposa","United States","N 37° 44' 45''","W 119° 31' 58''"));
		landmarks.put("C",new PopularLandmarks("CN Tower","Toronto","Canada","N 43° 38' 31''","W 79° 23' 13''"));
		landmarks.put("H",new PopularLandmarks("Hollywood Sign","Los Angeles","United States","N 34° 8' 2''","W 118° 19' 19''"));
		landmarks.put("E",new PopularLandmarks("Ephesus ","Izmir","Turkey","N 37° 56' 23''","E 27° 20' 27''"));
		landmarks.put("T",new PopularLandmarks("Twelve Apostles","Victoria","Australia","S 38° 39' 45''","E 143° 5' 35''"));
		landmarks.put("P",new PopularLandmarks("Piazza San Marco ","Venice","Italy","N 45° 26' 2''","E 12° 20' 18''"));
		landmarks.put("V",new PopularLandmarks("Vinson Massif","","Antartica","S 78° 35' 0''","W 85° 25' 0''"));
		landmarks.put("P",new PopularLandmarks("Parc Guell","Barcelona","Spain","N 41° 24' 48''","E 2° 9' 10''"));
		landmarks.put("A",new PopularLandmarks("Al Aqsa Mosque","Jerusalem","Israel","N 31° 46′ 34.21''","E 35° 14′ 8.99''"));	

		
		return landmarks;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

}
