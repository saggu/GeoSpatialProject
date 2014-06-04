package tweetparser;

import gazetteer.GeoName;
import gazetteer.PopularLandmarks;
import gazetteer.Tweet;
import gazetteer.VisualizationJSON;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import locationparser.ExtractLocation;

import redis.clients.jedis.Jedis;
import twitter4j.Status;
import twitter4j.TwitterObjectFactory;
import util.T2LResource;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.endpoint.StreamingEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class StreamingTweets {
	Jedis jedis;
	public StreamingTweets(){
		jedis = new Jedis("localhost");
	}
	public void findTweets() {
		//System.out.println("shshs");
		BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
		BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(1000);
		
		Tweet tw;
		
		

		//Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		StreamingEndpoint endpoint = new StatusesFilterEndpoint();
		// Optional: set up some followings and track terms
		//List<String> terms = Lists.newArrayList("liberty", "eiffel", "taj vacation","machu", "picchu", "table mountain", "potala",
				//"hagia", "pyramids","louvre","skellig","atomium", "lotus","hollywood","kremlin","chichen" );
		List<String> terms = Lists.newArrayList("#geospatial" );
				
		//((StatusesFilterEndpoint) endpoint).followings(followings);
		((StatusesFilterEndpoint) endpoint).trackTerms(terms);

		// These secrets should be read from a config file
		Authentication hosebirdAuth = new OAuth1(T2LResource.getProperty("org.t2l.twitter.oauth.key"), T2LResource.getProperty("org.t2l.twitter.oauth.secret"), T2LResource.getProperty("org.t2l.twitter.token.key"), T2LResource.getProperty("org.t2l.twitter.token.secret"));
		
		ClientBuilder builder = new ClientBuilder()
		  .name("Hosebird-Client-01")                              // optional: mainly for the logs
		  .hosts(hosebirdHosts)
		  .authentication(hosebirdAuth)
		  .endpoint(endpoint)
		  .processor(new StringDelimitedProcessor(msgQueue))
		  .eventMessageQueue(eventQueue);                          // optional: use this if you want to process client events

		Client hosebirdClient = builder.build();
		// Attempts to establish a connection.
		hosebirdClient.connect();
		
		try {
			// on a different thread, or multiple different threads....
			while (!hosebirdClient.isDone()) {
				String msg = msgQueue.take();
				//long id = jedis.incr("globalID:tweets");
			  //jedis.set("tweet:" + Long.toString(id), msg);
			  //jedis.lpush("tweets", Long.toString(id));
			  //Thread.sleep(10000);
				System.out.println("Tweet Received!");
				
			  System.out.println("Tweet:" + msg);
			  
				Status status = TwitterObjectFactory.createStatus(msg);
				tw = new Tweet(status.getUser().getScreenName(),status.getUser().getLocation(),status.getText(),status.getCreatedAt().toString());
				
				//find the landmark if the tweet has one
				List<GeoName> geoNames = new ArrayList<GeoName>();
				ExtractLocation el = new ExtractLocation();
				ParseATweet parser = new ParseATweet();
				PopularLandmarks landmarks = parser.FindLandmark(tw.getTweetText(), PopularLandmarks.CreateLandmarks());
				GeoName userLocation = null;
				VisualizationJSON vsj = null;
				Gson gson = new Gson();
				
				if(landmarks != null)
				{
					if(!tw.getLocation().trim().equals(""))
					{
						geoNames = el.ResolveLocationList(tw.getLocation());
						if(geoNames.size() > 0)
						{
							userLocation = geoNames.get(0); //get the first location
						}
					}
					
					if(userLocation != null)
					{
						vsj = new VisualizationJSON(tw.getTweetText(), tw.getUsername(), userLocation.getPrimaryCountryName(),
								Double.toString(userLocation.longitude), Double.toString(userLocation.latitude),
								landmarks.getName(), landmarks.getLongitude(), landmarks.getLatitude());
					}
					else
					{
						vsj = new VisualizationJSON(tw.getTweetText(), tw.getUsername(), "",
								"", "",	landmarks.getName(), landmarks.getLongitude(), landmarks.getLatitude());
					
					}
					
					String json = gson.toJson(vsj);
					System.out.print(60000);
					jedis.publish("tweet2location", json);
					
					//System.out.print(json);
				}
				
				
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			jedis.close();
		}
		hosebirdClient.stop();
	}
}
