import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import locationparser.ExtractLocation;

import gazetteer.GeoName;
import gazetteer.PopularLandmarks;
import gazetteer.Tweet;
import gazetteer.VisualizationJSON;
import com.google.gson.Gson;

import tweetparser.FiguredOutTweet;
import tweetparser.ParseATweet;
import util.QGramDistance;


public class Demo {
	
	public static ArrayList<Tweet> readFile(){
        ArrayList<Tweet> sentences = new ArrayList<Tweet>();
       
        BufferedReader br = null;
        
         
        try {
 
            String sCurrentLine;
 
            br = new BufferedReader(new FileReader("tweets.json"));
 
            while ((sCurrentLine = br.readLine()) != null) {
            	
            	//String[] test = sCurrentLine.split("\\$");
            	//System.out.println(test[1]);
            	Gson gs = new Gson();
            	Tweet t = gs.fromJson(sCurrentLine, Tweet.class);
                sentences.add(t);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           
        }
   
       
        return sentences;
    }
	
	public List<FiguredOutTweet> FigureThemOut()
	{
		ArrayList<Tweet> al = readFile();
		List<FiguredOutTweet> fot = new ArrayList<FiguredOutTweet>();
		List<GeoName> geoNames = new ArrayList<GeoName>();
		ExtractLocation el = new ExtractLocation();
		ParseATweet parser = new ParseATweet();
		
		int counter = 1000;//do it for first 100 tweets
		
		for (Tweet twet : al)
		{
			if(counter > 0)
			{
				PopularLandmarks landmark = parser.FindLandmark(twet.getTweetText(), PopularLandmarks.CreateLandmarks());
				
				if(landmark != null)
				{
					geoNames = el.ResolveLocationList(twet.getLocation());
					
					if(geoNames.size() > 0)
					{
						fot.add(new FiguredOutTweet(twet, landmark, geoNames));
						geoNames.clear();
					}
				}
			}
			
			counter--;
		}
		
		return fot;
	}
	
	
	public void GenerateJSON(List<FiguredOutTweet> figuredOutTweet)
	{
		List<VisualizationJSON> vs = new ArrayList<VisualizationJSON>();
		Gson gson = new Gson();
		int counter=0;
		
		
		
		for(FiguredOutTweet fot : figuredOutTweet)
		{
			VisualizationJSON vsObj;
			if(fot.getUserLocation().size() > 0)
			{
				GeoName gn = fot.getUserLocation().get(0);
				PopularLandmarks pl = fot.getMatchedLandmark();
				vsObj = new VisualizationJSON(fot.getTweet().getTweetText(), fot.getTweet().getUsername(), gn.getPrimaryCountryName(), Double.toString(gn.longitude), Double.toString(gn.latitude), pl.getName(), pl.getLongitude(), pl.getLatitude());
				vs.add(vsObj);
			}
			else
			{
				PopularLandmarks pl = fot.getMatchedLandmark();
				vsObj = new VisualizationJSON(fot.getTweet().getTweetText(), fot.getTweet().getUsername(), "","","", pl.getName(), pl.getLongitude(), pl.getLatitude());
				vs.add(vsObj);
			}
			
			String json = gson.toJson(vsObj);
			
			//Jedis jds = new Jedis("localhost");
			
			//jds.publish("tweet2location", json);
			
			PrintWriter out;
			try {
				String filename = "json/tweet_" + counter + ".json";
				out = new PrintWriter(filename);
				out.println(json);
				out.close();
				counter++;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//System.out.print(json);
			
		}
		
		
				
		
	}
	
	
	
	public static void main(String args[])
	{
		/*
		ParseATweet ps = new ParseATweet();
		
		//ps.FindLandmark("Celebrating #Holi , mesmerised by the #TajMahal and avoiding conflict: http://ow.ly/wcJ9T  #travel #India #Delhi #Agra #festivals", PopularLandmarks.CreateLandmarks());
		
		
		System.out.println("Looking for tweets...");
		//StreamingTweets sttw = new StreamingTweets();
	    //sttw.findTweets();
		
		BufferedReader br = null;
        
        try {
 
            //String sCurrentLine;
            Jedis jedis = new Jedis("localhost");
            //Gson gs = new Gson();
            //StringBuilder sb = new StringBuilder();
            
           // PrintWriter out;
			
			
 
           // for(int i=0;i<3506;i++)
            //{
            	//System.out.println("File number:" + i);
            	//sb.append("File number:" + i + "\n");
            	//br = new BufferedReader(new FileReader("json/tweet_19.json"));
            	//Tweet t = gs.fromJson("", Tweet.class);
            	PopularLandmarks pl = ps.FindLandmark("So today i met The Taj Mahal #breathless #beauty #nofilter #agra #india ", PopularLandmarks.CreateLandmarks());
            	//br.close();
            	//System.out.println("Tweet:" + t.getTweetText());
            	//sb.append("Tweet:" + t.getTweetText() + "\n");
            	//if(pl != null)
            	//{
            		//System.out.println("Landmark:" + pl.getName());
            		//sb.append("Landmark:" + pl.getName()+ "\n");
            	//}
            	//else
            	//{
            		//System.out.println("Landmark:Not Found");
            		//sb.append("Landmark:not found \n");
            	//}
            	br = new BufferedReader(new FileReader("json/tweet_19.json"));
            	Thread.sleep(60000);
            	jedis.publish("tweet2location", br.readLine());
            	
            	
            	br.close();
            	//Thread.sleep(10000);
            	
            	
            //}
        	//String filename = "json/tweet_rsult.txt";
			//out = new PrintWriter(filename);
			//out.println(sb.toString());
			//out.close();
    	
            //System.out.print(sb.toString());
       
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           
        }
   
       
		
		
		
		
		
		
		
		
		
		
		*/
		
		
		
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*
		Demo dm = new Demo();
		List<FiguredOutTweet> fot = dm.FigureThemOut();
		List<VisualizationHelper> vhList = dm.GenerateJsonForVisualization(fot);
		List<VisualizeBulk> vb = new ArrayList<VisualizeBulk>();
		
		
		for(VisualizationHelper vh : vhList)
		{
			List<String> userData = new ArrayList<String>();
			List<GeoName> gnList = vh.getGeoNames();
			
			for(GeoName gn : gnList)
			{
				String country = gn.getPrimaryCountryName();
				String cLong = Double.toString(gn.longitude);
				String cLat = Double.toString(gn.latitude);
				int count = vh.getCountCountries().get(country.toUpperCase());
				
				userData.add(country  + "," + cLong + "," + cLat + "," + count);
			}
			vb.add(new VisualizeBulk(vh.getPopularLandmark().getName(), 
						vh.getTweets().size(), vh.getPopularLandmark().getLongitude(), 
						vh.getPopularLandmark().getLatitude(), userData));
		}
		
			Gson gs = new Gson();
		
			String json = gs.toJson(vb);
			
			PrintWriter out;
			try {
				String filename = "json/BULKTWEET.json";
				out = new PrintWriter(filename);
				out.println(json);
				out.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		
		///////////////////////////////////////////////////
		//System.out.print(json);
		
		/*
		List<LandmarksJson> lsj = new ArrayList<LandmarksJson>();
		
		String[] alpha = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		Multimap<String, PopularLandmarks> allOfThem = PopularLandmarks.CreateLandmarks();
		
		char f = 65;
		
		Collection<PopularLandmarks> col;
		
		for(int i=0;i < alpha.length;i++)
		{
			col = allOfThem.get(alpha[i]);
			
			for(PopularLandmarks p : col)
			{
				//lsj.add(new LandmarksJson(p.getName(), p.getLongitude(), p.getLatitude()));
				
				System.out.println(p.getName() + ",");
			}
		}
		
		*/
		//Gson gs = new Gson();
		
		//String json = gs.toJson(lsj);
		
		//System.out.print(json);
		
		//for(LandmarksJson l: lsj)
			
		
		//dm.GenerateJSON(fot);
		
		//System.out.print("Done!");
		
		
		
		
		
		//dm.GenerateJsonForVisualization(fot);
		
		
		QGramDistance q = new QGramDistance();
		System.out.print(q.getSimilarity("niagra falls", "niagara falls", 2));
			
	}

}
