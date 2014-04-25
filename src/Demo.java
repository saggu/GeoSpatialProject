import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import locationparser.ExtractLocation;

import gazetteer.GeoName;
import gazetteer.LandmarksJson;
import gazetteer.PopularLandmarks;
import gazetteer.Tweet;
import gazetteer.VisualizationHelper;
import gazetteer.VisualizationJSON;

import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import tweetparser.FiguredOutTweet;
import tweetparser.ParseATweet;


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
		
		int counter = 100;//do it for first 100 tweets
		
		for (Tweet twet : al)
		{
			//if(counter > 0)
			//{
				PopularLandmarks landmark = parser.FindLandmark(twet.getTweetText(), PopularLandmarks.CreateLandmarks());
				
				if(landmark != null)
				{
					geoNames = el.ResolveLocationList(twet.getLocation());
					
					//if(geoNames.size() > 0)
					//{
						fot.add(new FiguredOutTweet(twet, landmark, geoNames));
						//geoNames.clear();
					//}
				}
			//}
			
			//counter--;
		}
		
		return fot;
	}
	
	public void GenerateJsonForVisualization(List<FiguredOutTweet> figuredOutTweet)
	{
		HashMap<String, VisualizationHelper> helper = new HashMap<String, VisualizationHelper>();
		List<VisualizationHelper> vhList = new ArrayList<VisualizationHelper>();
		
		List<FiguredOutTweet> fot  = figuredOutTweet;
		
		for(FiguredOutTweet ft : fot)
		{
			System.out.println(ft.getTweet().getTweetText());
			System.out.println(ft.getMatchedLandmark().getName());
			System.out.println(ft.getUserLocation().get(0));
			
			String landmarkName = ft.getMatchedLandmark().getName().toUpperCase();
			
			//if we donot have the VisualizationHelper object created for this landmark
			if(helper.get(landmarkName) == null)
			{
				List<Tweet> twList = new ArrayList<Tweet>();
				twList.add(ft.getTweet());
				List<GeoName> gnList = new ArrayList<GeoName>();
				GeoName gnCountry = ft.getUserLocation().get(0); 
				gnList.add(gnCountry); //just get the first location
				HashMap<String, Integer> countCountries = new HashMap<String, Integer>();
				countCountries.put(gnCountry.getPrimaryCountryName().toUpperCase(), 1);
				
				
				VisualizationHelper  vh = new VisualizationHelper(ft.getMatchedLandmark(), twList, gnList, countCountries);
				
				vhList.add(vh);
				helper.put(landmarkName, vh);
			}
			else
			{
				VisualizationHelper vh = helper.get(landmarkName);
				 boolean isCountryFound = false;
				 List<Tweet> twList = vh.getTweets();
				
				twList.add(ft.getTweet());
				
				List<GeoName> gnList =vh.getGeoNames();
				GeoName gnCountry = ft.getUserLocation().get(0); 
				String country = gnCountry.getPrimaryCountryName().toUpperCase();
				
				HashMap<String, Integer> countCountries = vh.getCountCountries();
				
				for(GeoName gn : gnList)
				{
					if(gnCountry.getPrimaryCountryName() == gn.getPrimaryCountryName())
					{
						isCountryFound = true;
					}
				}
				
				if(isCountryFound)
				{
					countCountries.put(country, countCountries.get(country) + 1);
				}
				else
				{
					gnList.add(gnCountry);
					countCountries.put(country, 1);
				}
				
				
			}
		}
		
		Gson gson = new Gson();
		HashMap<String,Object> finalres = new HashMap<String, Object>();
		finalres.put("landmarkName", "statue of liberty");
		
		HashMap<String,Object> countries = new HashMap<String, Object>();
		countries.put("Spain", 3);
		
		finalres.put("countries", countries);
		
		String json = gson.toJson(vhList);
		
		System.out.print(json);
		
		
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
		Demo dm = new Demo();
		List<FiguredOutTweet> fot = dm.FigureThemOut();
		
		List<LandmarksJson> lsj = new ArrayList<LandmarksJson>();
		
		dm.GenerateJSON(fot);
		
		System.out.print("Done!");
		
		
		
		
		
		//dm.GenerateJsonForVisualization(fot);
		
		
		
			
	}

}
