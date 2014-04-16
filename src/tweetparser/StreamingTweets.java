package tweetparser;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import redis.clients.jedis.Jedis;
import util.T2LResource;

import com.google.common.collect.Lists;
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
		System.out.println("shshs");
		BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
		BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(1000);

		//Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		StreamingEndpoint endpoint = new StatusesFilterEndpoint();
		// Optional: set up some followings and track terms
		List<String> terms = Lists.newArrayList("vacation instagram","vacation pic.twitter",
				"vacation imgur", "travel instagram", "travel pic.twitter", "travel imgur",
				"trip instagram", "trip pic.twitter", "trip imgur");
		
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
			  long id = jedis.incr("globalID:tweets");
			  jedis.set("tweet:" + Long.toString(id), msg);
			  jedis.lpush("tweets", Long.toString(id));
			}
		} catch (InterruptedException e) {
			System.out.println("Connection closed!");
			jedis.close();
		}
		hosebirdClient.stop();
	}
}
