package locationparser;

import gazetteer.GeoName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;

import resolver.ResolvedLocation;

public class ExtractLocation {
	
	
	public List<GeoName> ResolveLocationList(String locationName)
	{
		 // Instantiate the CLAVIN GeoParser
        GeoParser parser;
        List<GeoName> geoNames = new ArrayList<GeoName>();
		
        try 
        {
			parser = GeoParserFactory.getDefault("./IndexDirectory");
		} catch (IOException e) 
		{
			return null;
		} catch (ParseException e) 
		{
			return null;
		}
		
		//StringBuilder sb = new StringBuilder();
		
        // Parse location names in the text into geographic entities
        List<ResolvedLocation> resolvedLocations;
		try {
			resolvedLocations = parser.parse(locationName);
			
			for (ResolvedLocation rl : resolvedLocations)
			{
				geoNames.add(rl.geoname);
			}
		} catch (Exception e) {
			return null;
		}
        
        //add column names at the first line
        //sb.append("candidate,country,primarycountryname,position,longtitude,latitude \n");
        
        // Display the ResolvedLocations found for the location names
       // for (ResolvedLocation resolvedLocation : resolvedLocations)
      //  {
            //System.out.println(resolvedLocation);
        //    sb.append(resolvedLocation.toString());
            
        //}
        
        return geoNames;
    }

	
}
	


