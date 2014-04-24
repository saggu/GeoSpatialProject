import java.io.PrintWriter;
import java.util.ArrayList;

import gazetteer.PopularLandmarks;
import gazetteer.Tweet;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;

import tweetparser.ParseATweet;
import tweetparser.StreamingTweets;
import tweetparser.TweetSearcher;
import twitter4j.TwitterException;
import util.QGramDistance;


public class Demo {
	
	public static void main(String args[]) throws InterruptedException, TwitterException
	{
		StreamingTweets sttw = new StreamingTweets();
		Multimap<String,PopularLandmarks> pop = PopularLandmarks.CreateLandmarks();
		
		//ParseATweet parser = new ParseATweet();
    	//sttw.findTweets(pop.values());
    	
		//List<String> temp = pat.TokenizeTweet("this is a    tweet @hero    @zzero   @herooo");
		//for(int i=0;i<temp.size();i++)System.out.print(temp.get(i) + " ");
		//List<String> tr = new ArrayList<String>();
		//tr = pat.RemoveTwitterHandles(temp);
		
		//System.out.println("Total Items:" + tr.size());
		//for(String t:tr)
		//{
			//System.out.print(t.toString() + " ");
		//}
		
		
		//System.out.println(DamerauLevenshtein.damerauLevenshteinDistanceCaseInsensitive("Statu o Libert", "Statue of Liberty"));
		//System.out.println(DamerauLevenshtein.damerauLevenshteinDistanceCaseInsensitive("Statue of Liberty", "Statue of Liberty"));

		//System.out.println(QGramDistance.distance("statu of", "Statue of Liberty"));
		
		//System.out.println(QGramDistance.distance("Stat of libe", "Statue of Liberty"));
		
		//
		
		/*TweetSearcher searcher = new TweetSearcher();
    	ArrayList<String> queries = new ArrayList<String>();
    	
    	//general queries
    	queries.add("spring break");
    	queries.add("vacation");
    	queries.add("travel");
    	queries.add("holiday");
    	queries.add("landmark");
    	
    	ArrayList<Tweet> resp1 = searcher.SearchForTweets(queries);
    	
    	queries.clear();
    	//landmark queries
    	queries.add("loch ness");
    	queries.add("Trevi Fountain");
    	queries.add("Tower of Pisa");
    	queries.add("Mount Eden crater");
    	queries.add("St. Peter’s  Cathedral");
    	
    	ArrayList<Tweet> resp2 = searcher.SearchForTweets(queries);
    	
    	queries.clear();
    	//landmark + general
    	queries.add("eiffel tower vacation");
    	queries.add("statue of liberty travel");
    	queries.add("niagara falls vacation");
    	queries.add("taj mahal travel");
    	queries.add("Capitol Hill vacation");
    	
    	ArrayList<Tweet> resp3 = searcher.SearchForTweets(queries);
    	
    	queries.clear();*/
    	
		ArrayList<Tweet> resp = sttw.generateTweetsOutput();
		WriteTweetsToFile(resp);
    	//String fullTweet = "I love statue of liberty";//resp3.get(0).getTweetText();
    	
    	
		//List<TweetNLP> t = parser.TagTheTweet("I love the Eiffel Tower cc @twittingsaggu");
		
		//List<String> candidates = parser.GenerateCandidateSubStringList(t);
		/*int cont = 0;
		System.out.println(resp.size());
		for(int i = 0; i < 70; i++){
			ArrayList<String> founds = new ArrayList<String>();
			for(int j = 0; j < 100; j++){
				Tweet status = resp.get(cont);
				PopularLandmarks landmark = parser.FindLandmark(status.getTweetText(), PopularLandmarks.CreateLandmarks());
				if(landmark != null)
				{
					String f = status.getUsername() + " - " + status.getLocation() + " - " + status.getTweetText()  + " - " + landmark.getName() + " " + landmark.getCountry();
					founds.add(f);
				}
				cont++;
			}
			if(founds.size() == 0)
				System.out.println("NOT FOUND");
			for(int k=0; k < founds.size(); k++)
				System.out.println(founds.get(k));
		}*/
		
		//for (String candiate : candidates)
		//{
		
		//}
		
		
		//System.out.print(q.getSimilarity("statue of liberty", "statue of liberty", 2));
		
		
		

				
	}
	
	
	public static void WriteTweetsToFile(ArrayList<Tweet> tweets){
		String filename = "tweets.json";
		
		try {
			PrintWriter out = new PrintWriter(filename);
			for(Tweet tw : tweets){
				Gson gson = new Gson();
				String json = gson.toJson(tw);
				out.println(json);
				//out.println(tw.getTweetText());
			}
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
