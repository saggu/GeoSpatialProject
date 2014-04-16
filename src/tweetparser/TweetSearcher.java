package tweetparser;

import gazetteer.Tweet;

import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import util.T2LResource;

public class TweetSearcher {
	public TweetSearcher(){
		
	}
	
	public ArrayList<Tweet> SearchForTweets(ArrayList<String> queries){
		Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(T2LResource.getProperty("org.t2l.twitter.oauth.key"), T2LResource.getProperty("org.t2l.twitter.oauth.secret"));
        twitter.setOAuthAccessToken(new AccessToken(T2LResource.getProperty("org.t2l.twitter.token.key"),T2LResource.getProperty("org.t2l.twitter.token.secret")));
        ArrayList<Tweet> tweets = null;
        try {
        	tweets = new ArrayList<Tweet>();
        	for(String q : queries){
                Query query = new Query(q);
                query.setCount(100);
                QueryResult result = twitter.search(query);
    			
                
                for (Status status : result.getTweets()) {
                	String username = status.getUser().getScreenName();
                	String location = status.getUser().getLocation();
                	String tweetText = status.getText();
                	String tweetDate = status.getCreatedAt().toString();
                	//String finalTweet = Joiner.on(" $ ").join(username,location,tweetText,tweetDate);
                	Tweet tw = new Tweet(username, location, tweetText, tweetDate);
                	tweets.add(tw);
                }
                
            }
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return tweets;
	}
	
	/*private void WriteTweetsToFile(ArrayList<String> tweets, String q){
		String filename = q + ".txt";
		if(q.charAt(0) == '#'){
			filename = filename.substring(1);
		}
		
		try {
			PrintWriter out = new PrintWriter("tweets/"+filename);
			for(String tw : tweets){
				out.println(tw);
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

}