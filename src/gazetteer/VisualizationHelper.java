package gazetteer;

import java.util.HashMap;
import java.util.List;

public class VisualizationHelper {
	
	PopularLandmarks popularLandmark;
	List<Tweet> tweets;
	List <GeoName> geoNames;
	HashMap<String, Integer> countCountries;
	
	
	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	
	
	public PopularLandmarks getPopularLandmark() {
		return popularLandmark;
	}

	public void setPopularLandmark(PopularLandmarks popularLandmark) {
		this.popularLandmark = popularLandmark;
	}

	public List<GeoName> getGeoNames() {
		return geoNames;
	}

	public void setGeoNames(List<GeoName> geoNames) {
		this.geoNames = geoNames;
	}

	public HashMap<String, Integer> getCountCountries() {
		return countCountries;
	}

	public void setCountCountries(HashMap<String, Integer> countCountries) {
		this.countCountries = countCountries;
	}

	
	
	public VisualizationHelper(PopularLandmarks pl, List<Tweet> tweets, List<GeoName> geoN, HashMap<String, Integer> countC)
	{
		this.popularLandmark = pl;
		this.tweets = tweets;
		this.geoNames = geoN;
		this.countCountries = countC;
	}
	

}
