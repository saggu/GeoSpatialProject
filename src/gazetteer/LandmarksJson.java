package gazetteer;

public class LandmarksJson {
	
	private String name;
	private String longitude;
	private String latitude;
	
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public LandmarksJson(String name, String longitude, String latitude) {
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	

}
