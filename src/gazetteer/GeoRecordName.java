package gazetteer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class GeoRecordName {
	
	//geoRecordName id from geonames.org
	public final int geoRecordID;
	
	//name of the record
	public final String geoRecordName;
	
	//asciiname in plain ascii characters
	public final String asciiName;
	
	//list of all the alternate names
	public final List<String> alternateNames;
	
	//latitude in decimal degrees
	public final double latitude;
	
	//longitude in decimal degrees
	public final double longitude;
	
	//major feature category - need to identify the feature classes which identify popular landmarks
	// (see http://www.geonames.org/export/codes.html)
	public final FeatureClass featureClass;
	
	// (see http://www.geonames.org/export/codes.html)
	public final FeatureCode featureCode;
	
	//ISO-3166 2-letter country code
	public final CountryCode primaryCountryCode;
	
	//associated name with the country code
	public String getPrimaryCountryName()
	{
		return primaryCountryCode.name;
	}
	
	//list of alternate ISO-3166 2-letter country codes
	public final List<CountryCode> alternateCountryCodes;
	
	 
	
	
	// Mostly FIPS codes. ISO codes are used for US, CH, BE and ME. UK
    // and Greece are using an additional level between country and
    // FIPS code.
    public final String admin1Code;
    
    // code for the second administrative division
    // (e.g., a county in the US)
    public final String admin2Code;
    
    // code for third level administrative division
    public final String admin3Code;
    
    // code for fourth level administrative division
    public final String admin4Code;
    
	
	//population
	public final long population;
	
	//meters
	public final  int elevation;
	
	// digital elevation model, srtm3 or gtopo30, average elevation of
    // 3''x3'' (ca 90mx90m) or 30''x30'' (ca 900mx900m) area in meters,
    // integer. srtm processed by cgiar/ciat.
    public final int digitalElevationModel;
    
    //timezone 
    public final TimeZone timeZone;
    
    //last modification date
    public final Date modificationDate;
    
    //Default NULL value	
	public static final int OUT_OF_BOUNDS = -9999999;
	
	
	/**
     * Sole constructor for {@link GeoName} class.
     * 
     * Encapsulates a gazetteer record from the GeoNames database.
     * 
     * @param geonameID                 unique identifier
     * @param name                      name of this location
     * @param asciiName                 plain text version of name
     * @param alternateNames            list of alternate names, if any
     * @param latitude                  lat coord
     * @param longitude                 lon coord
     * @param featureClass              general type of feature (e.g., "Populated place")
     * @param featureCode               specific type of feature (e.g., "capital of a political entity")
     * @param primaryCountryCode        ISO country code
     * @param alternateCountryCodes     list of alternate country codes, if any (i.e., disputed territories)
     * @param admin1Code                FIPS code for first-level administrative subdivision (e.g., state or province)
     * @param admin2Code                second-level administrative subdivision (e.g., county)
     * @param admin3Code                third-level administrative subdivision
     * @param admin4Code                fourth-level administrative subdivision
     * @param population                number of inhabitants
     * @param elevation                 elevation in meters
     * @param digitalElevationModel     another way to measure elevation
     * @param timezone                  timezone for this location
     * @param modificationDate          date of last modification for the GeoNames record
     */
    public GeoRecordName(
            int geoRecordNameID,
            String name,
            String asciiName,
            List<String> alternateNames,
            Double latitude,
            Double longitude,
            FeatureClass featureClass,
            FeatureCode featureCode,
            CountryCode primaryCountryCode,
            List<CountryCode> alternateCountryCodes,
            String admin1Code,
            String admin2Code,
            String admin3Code,
            String admin4Code,
            Long population,
            Integer elevation,
            Integer digitalElevationModel,
            TimeZone timezone,
            Date modificationDate) {
        this.geoRecordID = geoRecordNameID;
        this.geoRecordName = name;
        this.asciiName = asciiName;
        this.alternateNames = alternateNames;
        this.latitude = latitude;
        this.longitude = longitude;
        this.featureClass = featureClass;
        this.featureCode = featureCode;
        this.primaryCountryCode = primaryCountryCode;
        this.alternateCountryCodes = alternateCountryCodes;
        this.admin1Code = admin1Code;
        this.admin2Code = admin2Code;
        this.admin3Code = admin3Code;
        this.admin4Code = admin4Code;
        this.population = population;
        this.elevation = elevation;
        this.digitalElevationModel = digitalElevationModel;
        this.timeZone = timezone;
        this.modificationDate = modificationDate;
    }
	
    //parses a single line of tab delimited record from geonames and returns a GeoRecordName object
    public static GeoRecordName parseGeoNameRecord(String inputRecord)
    {
    	//split the tab delimited record into tokens
    	String[] tokens = inputRecord.split("\t");
    	
    	//initialize the fields with appropriate token
    	int geoRecordID = Integer.parseInt(tokens[0]);
    	String name = tokens[1];
    	String asciiName = tokens[2];
    	
    	List<String> alternateName;
    	if(tokens[3].length() > 0)
    	{
    		alternateName = Arrays.asList(tokens[3].split(","));
    	}
    	else
    	{
    		alternateName = new ArrayList<String>();
    	}
    	
    	double latitude;
    	try
    	{
    		latitude = Double.parseDouble(tokens[4]);
    	}
    	catch(NumberFormatException ex)
    	{
    		latitude = OUT_OF_BOUNDS;
    	}
    	
    	double longitude;
    	try
    	{
    		longitude = Double.parseDouble(tokens[5]);
    	}
    	catch(NumberFormatException ex)
    	{
    		longitude = OUT_OF_BOUNDS;
    	}
    	
    	FeatureClass featureClass;
    	if(tokens[6].length() > 0)
    	{
    		featureClass = FeatureClass.valueOf(tokens[6]);
    	}
    	else
    	{
    		featureClass = FeatureClass.NULL;
    	}
    	
    	FeatureCode  featureCode;
    	if(tokens[7].length() > 0)
    	{
    		featureCode = FeatureCode.valueOf(tokens[7]);
    	}
    	else
    	{
    		featureCode =FeatureCode.NULL;
    	}
    	
    	CountryCode primaryCountryCode;
    	if(tokens[8].length() > 0)
    	{
    		primaryCountryCode = CountryCode.valueOf(tokens[8]);
    	}
    	else
    	{
    		primaryCountryCode = CountryCode.NULL;
    	}
    	
    	List<CountryCode> alternateCountryCodes = new ArrayList<CountryCode>();
    	if(tokens[9].length() > 0)
    	{
    		for(String code : tokens[9].split(","))
    		{
    			if(code.length() > 0)
    				alternateCountryCodes.add(CountryCode.valueOf(code));
    		}
    	}
    	
    	
    	String admin1Code = tokens[10];
    	String admin2Code = tokens[11];
    	
    	String admin3Code;
    	String admin4Code;
    	long population;
    	int elevation;
    	int digitalElevationModel;
    	TimeZone timeZone;
    	Date modificationDate;
    	
    	if(tokens.length < 19) //dirtydata, ignore everything after this point
    	{
    		admin3Code = "";
    		admin4Code = "";
    		population = OUT_OF_BOUNDS;
    		elevation = OUT_OF_BOUNDS;
    		digitalElevationModel = OUT_OF_BOUNDS;
    		timeZone = null;
    		modificationDate = new Date(0);
    	}
    	else //lets get the data
    	{
    		admin3Code = tokens[12];
    		admin4Code = tokens[13];
    		
    		try
    		{
    			population = Long.parseLong(tokens[14]);
    		}
    		catch(NumberFormatException ex)
    		{
    			population = OUT_OF_BOUNDS;
    		}
    		
    		try 
    		{
    			elevation = Integer.parseInt(tokens[15]);
    		}
    		catch(NumberFormatException ex)
    		{
    			elevation = OUT_OF_BOUNDS;
    		}
    		
    		try
    		{
    			digitalElevationModel = Integer.parseInt(tokens[16]);
    		}
    		catch(NumberFormatException ex)
    		{
    			digitalElevationModel = OUT_OF_BOUNDS;
    		}
    		
    		timeZone = TimeZone.getTimeZone(tokens[17]);
    		
    		try
    		{
    			modificationDate = new SimpleDateFormat("yyyy-MM-dd").parse(tokens[18]);
    		}
    		catch(ParseException ex)
    		{
    			modificationDate = new Date(0);
    		}
    	}
    	
    	return new GeoRecordName(geoRecordID, name, asciiName, alternateName, 
    						latitude, longitude, featureClass, featureCode, primaryCountryCode, 
    						alternateCountryCodes, admin1Code, admin2Code, admin3Code, admin4Code, population, 
    						elevation, digitalElevationModel, timeZone, modificationDate);
    	
    }
    
    
    //for printing in the required format - CHANGE IT LATER
    @Override
    public String toString()
    {
    	return geoRecordName;
    }
	
	
	

}
