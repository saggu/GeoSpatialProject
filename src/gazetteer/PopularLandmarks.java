package gazetteer;

public class PopularLandmarks {
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	private String name;
	private String country;
	private String city;
	
	public PopularLandmarks(String name, String city, String country)
	{
		this.name = name;
		this.city = city;
		this.country = country;
	}
	
	public static PopularLandmarks CreateLandmark(String name, String city, String country)
	{
		return new PopularLandmarks(name, city, country);
	}

}
