package tweetparser;

import java.util.List;

import gazetteer.GeoName;
import gazetteer.PopularLandmarks;
import gazetteer.Tweet;

public class FiguredOutTweet {
	
	Tweet tweet;
	PopularLandmarks matchedLandmark;
	List<GeoName> userLocation;
	
	public PopularLandmarks getMatchedLandmark() {
		return matchedLandmark;
	}
	public void setMatchedLandmark(PopularLandmarks matchedLandmark) {
		this.matchedLandmark = matchedLandmark;
	}

	public Tweet getTweet() {
		return tweet;
	}
	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}
	public List<GeoName> getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(List<GeoName> userLocation) {
		this.userLocation = userLocation;
	}
	
	
	public FiguredOutTweet(Tweet tweet, PopularLandmarks matchedLandark,List<GeoName> userLocation)
	{
		this.tweet = tweet;
		this.matchedLandmark = matchedLandark;
		this.userLocation = userLocation;
	}
	

}
