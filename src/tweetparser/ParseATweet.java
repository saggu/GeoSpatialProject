package tweetparser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import util.QGramDistance;


import com.google.common.collect.Multimap;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import gazetteer.PopularLandmarks;

public class ParseATweet implements TweetParser{
	
	List<TweetNLP> taggedTweet;
	
	public ParseATweet()
	{
		taggedTweet = new ArrayList<TweetNLP>();
	}
	
	private String RemoveTwitterHandles(String tweet) 
	{
		List<String> tokens = new ArrayList<String>(Arrays.asList(tweet.trim().split(" "))); //break into tokens, space delimited
		tokens.removeAll(Arrays.asList("",null)); //remove all the empty items in the list
		
		int iIterator = 0;
		
		while(iIterator < tokens.size())
		{
			if(tokens.get(iIterator).charAt(0) == '@') //  I get concurrentmodifictionexception while using for(token:tokenizesTweet)
			{
				tokens.remove(iIterator);
			}
			else
			{
				iIterator++;
			}
		}
		StringBuilder cleanTweet = new StringBuilder();
		for(String s : tokens){
			cleanTweet.append(s + " "); 
		}
		
		return cleanTweet.toString();
	}
	
	private List<TweetNLP> TagTheTweet(String fullTweet)
	{
		//Remove tweet usernames
		String tweet = RemoveTwitterHandles(fullTweet);
		
		taggedTweet = new ArrayList<TweetNLP>();
		
		String grammar = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
	    String[] options = { "-maxLength", "80", "-retainTmpSubcategories" };
	    LexicalizedParser lp = LexicalizedParser.loadModel(grammar, options);
	    TreebankLanguagePack tlp = lp.getOp().langpack();
	    
	    Tokenizer<? extends HasWord> toke =
		tlp.getTokenizerFactory().getTokenizer(new StringReader(tweet));
		List<? extends HasWord> sentence2 = toke.tokenize();
		
	    Tree parse = lp.parse(sentence2);
	      
	    for(TaggedWord tw: parse.taggedYield())
	    {
	    	taggedTweet.add(new TweetNLP(tw.word(), tw.tag()));
	    }
	    //displayTaggedTweets();
	    return taggedTweet;
	    
	}
	
	private void displayTaggedTweets()
	{
		for(TweetNLP t : taggedTweet)
		{
			System.out.println("Word: " + t.getWord() + ", " + "Tag: " + t.getTag());
		}
	}
	
	
	
	private List<String> GenerateCandidateSubStringList(String fullTweet) 
	{
		List<TweetNLP> tweetNLP = TagTheTweet(fullTweet);
		List<String> candidates = new ArrayList<String>();
		
		for(int iToken=0;iToken<tweetNLP.size();iToken++)
		{
			TweetNLP tweet = tweetNLP.get(iToken);
			
			if(tweet.getTag().equals("NN") || tweet.getTag().equals("NNP")) //check for nouns in the tokens. Standard notation
			{
				int start = iToken - 2; //generate candidates within distance +- 2 tokens from the "NOUN" index
				int end = iToken + 2;
				
				for (int i = start;i <= end;i++)
				{
					for(int j = i; j <= end; j++)
					{
						if(i>= 0 && j < tweetNLP.size()) //keep it within boundaries
						{
							String candidate = GenerateCandidateSubString(tweetNLP, i, j);
							if(!candidates.contains(candidate))
								candidates.add(candidate);
						}
					}
				}
				
			}
		}
		return candidates;
	}
	
	private String GenerateCandidateSubString(List<TweetNLP> tweet, int start, int end)
	{
		StringBuilder returnString = new StringBuilder();
		for(int i = start; i <= end; i++)
		{
			returnString.append(tweet.get(i).getWord() + " ");
		}
		
		return returnString.toString().trim();
		
	}
	
	@Override
	public PopularLandmarks FindLandmark(String fullTweet, Multimap <String, PopularLandmarks> landmarks)
	{
		List<String> candidates = GenerateCandidateSubStringList(fullTweet);
		QGramDistance qGram = new QGramDistance();
		Double maxScore = 0.0;
		PopularLandmarks matchedLandmark = null;
		
		for(String candidate: candidates)
		{
			String firstChar = candidate.substring(0, 1);
			//System.out.println(firstChar);
			Collection<PopularLandmarks> colLandmarks = landmarks.get(firstChar.toUpperCase());
			
			//if(colLandmarks.size() > 0)
			if(colLandmarks != null)
			{
				for(PopularLandmarks pl : colLandmarks)
				{
					Double score = qGram.getSimilarity(candidate.trim(), pl.getName().trim(),2);
					//System.out.println("Candidate: " + candidate + " Score: " + score);
					//System.out.println("Landmark: " + pl.getName());
					if(maxScore < score) 
					{
						maxScore = score;
						matchedLandmark = pl;
					}
				}
			}
		}
		
		if(maxScore > 0.75)
			return matchedLandmark;
		
		return null;
	}
	

}
