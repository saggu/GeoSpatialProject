package gazetteer;

import java.util.List;

public class VisualizeBulk {
	
	String landmark;
	int noTweets;
	String lmLongitude;
	String lmLatitude;
	List<String> userLocationData;
	
	
	public VisualizeBulk(String landmark, int noTweets, String lmLongitude,
			String lmLatitude, List<String> userLocationData) {
		this.landmark = landmark;
		this.noTweets = noTweets;
		this.lmLongitude = lmLongitude;
		this.lmLatitude = lmLatitude;
		this.userLocationData = userLocationData;
	}


	public String getLandmark() {
		return landmark;
	}


	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}


	public int getNoTweets() {
		return noTweets;
	}


	public void setNoTweets(int noTweets) {
		this.noTweets = noTweets;
	}


	public String getLmLongitude() {
		return lmLongitude;
	}


	public void setLmLongitude(String lmLongitude) {
		this.lmLongitude = lmLongitude;
	}


	public String getLmLatitude() {
		return lmLatitude;
	}


	public void setLmLatitude(String lmLatitude) {
		this.lmLatitude = lmLatitude;
	}


	public List<String> getUserLocationData() {
		return userLocationData;
	}


	public void setUserLocationData(List<String> userLocationData) {
		this.userLocationData = userLocationData;
	}
	
	
	

}
