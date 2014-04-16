import java.util.ArrayList;

import gazetteer.PopularLandmarks;
import gazetteer.Tweet;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import tweetparser.ParseATweet;
import tweetparser.StreamingTweets;
import tweetparser.TweetSearcher;
import util.QGramDistance;


public class Demo {
	
	public static void main(String args[]) throws InterruptedException
	{
		StreamingTweets sttw = new StreamingTweets();
    	sttw.findTweets();
    	
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
		
		//ParseATweet parser = new ParseATweet();
		
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
    	
    	/*String fullTweet = "I love statue of liberty";//resp3.get(0).getTweetText();
    	
    	
		//List<TweetNLP> t = parser.TagTheTweet("I love the Eiffel Tower cc @twittingsaggu");
		
		//List<String> candidates = parser.GenerateCandidateSubStringList(t);
		
		PopularLandmarks landmark = parser.FindLandmark(fullTweet, PopularLandmarks.CreateLandmarks());
		
		//for (String candiate : candidates)
		//{
		if(landmark != null)
		{
			System.out.println(fullTweet);
			System.out.println(landmark.getName() + " " + landmark.getCountry());
		}
		else
		{
			System.out.print("NULL");
		}
		//}
		
		
		
		QGramDistance q = new QGramDistance();*/
		//System.out.print(q.getSimilarity("statue of liberty", "statue of liberty", 2));
		
		
		

				
	}

}
