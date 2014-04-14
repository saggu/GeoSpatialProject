import gazetteer.PopularLandmarks;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import tweetparser.ParseATweet;
import tweetparser.TweetNLP;
import util.QGramDistance;


public class Demo {
	
	public static void main(String args[])
	{
		ParseATweet pat = new ParseATweet();
		
		//List<String> temp = pat.TokenizeTweet("this is a    tweet @hero    @zzero   @herooo");
		//for(int i=0;i<temp.size();i++)System.out.print(temp.get(i) + " ");
		//List<String> tr = new ArrayList<String>();
		//tr = pat.RemoveTwitterHandles(temp);
		
		//System.out.println("Total Items:" + tr.size());
		//for(String t:tr)
		//{
			//System.out.print(t.toString() + " ");
		//}
		
		Multimap<String, PopularLandmarks> landmarks = ArrayListMultimap.create();
		
		landmarks.put("S",PopularLandmarks.CreateLandmark("Statue of Liberty", "New York", "US"));
		landmarks.put("E",PopularLandmarks.CreateLandmark("Eiffel Tower", "Paris", "France"));
		landmarks.put("T",PopularLandmarks.CreateLandmark("Taj Mahal", "Agra", "India"));
		landmarks.put("M",PopularLandmarks.CreateLandmark("Machu Picchu", "Cuzco", "Peru"));
		landmarks.put("B",PopularLandmarks.CreateLandmark("Blue Domed Church", "Santorini", "Greece"));
		landmarks.put("B",PopularLandmarks.CreateLandmark("Big Ben", "London", "United Kingdom"));
		
		//System.out.println(DamerauLevenshtein.damerauLevenshteinDistanceCaseInsensitive("Statu o Libert", "Statue of Liberty"));
		//System.out.println(DamerauLevenshtein.damerauLevenshteinDistanceCaseInsensitive("Statue of Liberty", "Statue of Liberty"));

		//System.out.println(QGramDistance.distance("statu of", "Statue of Liberty"));
		
		//System.out.println(QGramDistance.distance("Stat of libe", "Statue of Liberty"));
		
		ParseATweet parser = new ParseATweet();
		String fullTweet = "I love the machu picchu cc @twittingsaggu";
		//List<TweetNLP> t = parser.TagTheTweet("I love the Eiffel Tower cc @twittingsaggu");
		
		//List<String> candidates = parser.GenerateCandidateSubStringList(t);
		
		PopularLandmarks landmark = parser.FindLandmark(fullTweet, landmarks);
		
		//for (String candiate : candidates)
		//{
		if(landmark != null)
		{
			System.out.println(landmark.getName() + " " + landmark.getCountry());
		}
		else
		{
			System.out.print("NULL");
		}
		//}
		
		
		
		QGramDistance q = new QGramDistance();
		//System.out.print(q.getSimilarity("statue of liberty", "statue of liberty", 2));
		
		
		

				
	}

}
