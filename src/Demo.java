import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import locationparser.ExtractLocation;

import gazetteer.GeoName;
import gazetteer.PopularLandmarks;
import gazetteer.Tweet;
import gazetteer.VisualizationHelper;

import com.google.gson.Gson;

import tweetparser.ParseATweet;

import tweetparser.FiguredOutTweet;

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
			if(counter > 0)
			{
				PopularLandmarks landmark = parser.FindLandmark(twet.getTweetText(), PopularLandmarks.CreateLandmarks());
				
				if(landmark != null)
				{
					geoNames = el.ResolveLocationList(twet.getLocation());
					
					if(geoNames.size() > 0)
					{
						fot.add(new FiguredOutTweet(twet, landmark, geoNames));
						//geoNames.clear();
					}
				}
			}
			
			counter--;
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
		String json = gson.toJson(vhList);
		
		System.out.print(json);
		
		
	}
	
	public static void main(String args[])
	{
		Demo dm = new Demo();
		List<FiguredOutTweet> fot = dm.FigureThemOut();
		
		dm.GenerateJsonForVisualization(fot);		
		
			
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
