package tweetparser;

import gazetteer.PopularLandmarks;
import com.google.common.collect.Multimap;

public interface TweetParser {
	
	public PopularLandmarks FindLandmark(String fullTweet, Multimap<String, PopularLandmarks> landmarks);

}
