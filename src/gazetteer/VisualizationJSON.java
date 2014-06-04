package gazetteer;

public class VisualizationJSON {
	
	String tweetText;
	String tweetUser;
	String tweetUserCountry;
	String tUserCLongitude;
	String tUserCLatitude;
	String landmarkName;
	String landmarkLongitute;
	String landmarkLatitude;
	
	
	
	

	public String getTweetText() {
		return tweetText;
	}
	public void setTweetText(String tweetText) {
		this.tweetText = tweetText;
	}
	public String getTweetUser() {
		return tweetUser;
	}
	public void setTweetUser(String tweetUser) {
		this.tweetUser = tweetUser;
	}
	public String getTweetUserCountry() {
		return tweetUserCountry;
	}
	public void setTweetUserCountry(String tweetUserCountry) {
		this.tweetUserCountry = tweetUserCountry;
	}
	public String gettUserCLongitude() {
		return tUserCLongitude;
	}
	public void settUserCLongitude(String tUserCLongitude) {
		this.tUserCLongitude = tUserCLongitude;
	}
	public String gettUserCLatitude() {
		return tUserCLatitude;
	}
	public void settUserCLatitude(String tUserCLatitude) {
		this.tUserCLatitude = tUserCLatitude;
	}
	public String getLandmarkName() {
		return landmarkName;
	}
	public void setLandmarkName(String landmarkName) {
		this.landmarkName = landmarkName;
	}
	public String getLandmarkLongitute() {
		return landmarkLongitute;
	}
	public void setLandmarkLongitute(String landmarkLongitute) {
		this.landmarkLongitute = landmarkLongitute;
	}
	public String getLandmarkLatitude() {
		return landmarkLatitude;
	}
	public void setLandmarkLatitude(String landmarkLatitude) {
		this.landmarkLatitude = landmarkLatitude;
	}

	
	public VisualizationJSON(String tweetText, String tweetUser,
			String tweetUserCountry, String tUserCLongitude,
			String tUserCLatitude, String landmarkName,
			String landmarkLongitute, String landmarkLatitude) {
		this.tweetText = tweetText;
		this.tweetUser = tweetUser;
		this.tweetUserCountry = tweetUserCountry;
		this.tUserCLongitude = tUserCLongitude;
		this.tUserCLatitude = tUserCLatitude;
		this.landmarkName = landmarkName;
		this.landmarkLongitute = landmarkLongitute;
		this.landmarkLatitude = landmarkLatitude;
		
	}
	

}
