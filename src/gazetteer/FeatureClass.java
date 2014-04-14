package gazetteer;


/**
 * Feature categories used by GeoNames.
 * (see http://www.geonames.org/export/codes.html)
 *
 */

public enum FeatureClass {
	
	A("Administrative region", "country, state, region, etc."),
    P("Populated place", "city, village, etc."),
    V("Vegetation feature", "forest, heath, etc."),
    L("Locality or area", "park, area, etc."),
    U("Undersea feature", "reef, trench, etc."),
    R("Streets, highways, roads, or railroad", "road, railroad, etc."),
    T("Hypsographic feature", "mountain, hill, rock, etc."),
    H("Hydrographic feature", "stream, lake, etc."),
    S("Spot feature", "spot, building, farm, etc."),
    
    // manually added for feature codes & locations not assigned to a
    // feature class
    NULL("not available", "");
	
	
	//name of the feature class
	public final String type;
	
	//description of the feature class
	public final String description;
	
	//constructor
	private FeatureClass(String type, String description)
	{
		this.type = type;
		this.description = description;
	}

}
