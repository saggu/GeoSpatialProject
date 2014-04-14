package tweetparser;

public class TweetNLP {
	
	private String word;
	private String tag;
	
	public String getWord() {
		return word;
	}
	
	public String getTag() {
		return tag;
	}
	
	public TweetNLP(String word, String tag)
	{
		this.word = word;
		this.tag = tag;
	}

}
