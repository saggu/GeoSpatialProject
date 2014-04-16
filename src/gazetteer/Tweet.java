package gazetteer;

public class Tweet {
	
	private String username;
	private String location;
	private String tweetText;
	private String tweetDate;
	
	public Tweet(String username, String location, String tweetText,
			String tweetDate) {
		super();
		this.username = username;
		this.location = location;
		this.tweetText = tweetText;
		this.tweetDate = tweetDate;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTweetText() {
		return tweetText;
	}
	public void setTweetText(String tweetText) {
		this.tweetText = tweetText;
	}
	public String getTweetDate() {
		return tweetDate;
	}
	public void setTweetDate(String tweetDate) {
		this.tweetDate = tweetDate;
	}
	
}
