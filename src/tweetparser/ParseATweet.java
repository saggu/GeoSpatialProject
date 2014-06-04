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
	TreebankLanguagePack tlp;
	LexicalizedParser lp;
	
	public ParseATweet()
	{
		taggedTweet = new ArrayList<TweetNLP>();
		String grammar = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
	    String[] options = { "-maxLength", "80", "-retainTmpSubcategories" };
	    this.lp = LexicalizedParser.loadModel(grammar, options);
	    this.tlp = lp.getOp().langpack();
	}
	
	private List<String> TokenizeTweet(String tweet)
	{
		List<String> tokens = new ArrayList<String>(Arrays.asList(tweet.trim().split(" "))); //break into tokens, space delimited
		tokens.removeAll(Arrays.asList("",null)); //remove all the empty items in the list
		return tokens;
	}
	
	private String RemoveSpacesFromString(String strLine)
	{
		StringBuilder sb = new StringBuilder();
		List<String> tokens = TokenizeTweet(strLine);
		
		for(String token : tokens)
		{
			sb.append(token);
		}
		
		return sb.toString();
	}
	
	private String RemoveTwitterHandles(String tweet) 
	{
		List<String> tokens = TokenizeTweet(tweet);
		
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
		
	    Tokenizer<? extends HasWord> toke =
		tlp.getTokenizerFactory().getTokenizer(new StringReader(tweet));
		List<? extends HasWord> sentence2 = toke.tokenize();
		
	    Tree parse = lp.parse(sentence2);
	    System.out.println("Tagging the tokens from the tweet \n\n");
	    for(TaggedWord tw: parse.taggedYield())
	    {
	    	taggedTweet.add(new TweetNLP(tw.word(), tw.tag()));
	    	System.out.println("Token: " + tw.word() + "- Tag:" + tw.tag());
	    }
	    //displayTaggedTweets();
	    return taggedTweet;
	    
	}
	
	private List<TweetNLP> ParseHashTags(List<TweetNLP> tweetNLP)
	{
		List<TweetNLP> hashTweet = new ArrayList<TweetNLP>();
		String firstChar;
		
		for(TweetNLP tweet : tweetNLP)
		{
			firstChar = tweet.getWord().substring(0,1);
			
			if(firstChar.equals("#") && (tweet.getTag().equals("NN") || tweet.getTag().equals("NNP") || tweet.getTag().equals("NNS") || tweet.getTag().equals("NNPS")) )
			{
				hashTweet.add(tweet);
			}
		}
		
		return hashTweet;
	}
	
	private List<String> GenerateCandidateSubStringList(String fullTweet) 
	{
		List<TweetNLP> tweetNLP = TagTheTweet(fullTweet);
		List<TweetNLP> hashTags = ParseHashTags(tweetNLP);
		List<String> candidates = new ArrayList<String>();
		
		if(hashTags.size() > 0)
		{
			for(int i =0 ; i < hashTags.size();i++)
			{
				candidates.add(hashTags.get(i).getWord().trim());
			}
		}
		
		
		for(int iToken=0;iToken<tweetNLP.size();iToken++)
		{
			TweetNLP tweet = tweetNLP.get(iToken);
			String firstChar = tweet.getWord().substring(0,1);
			
			if(!firstChar.equals("#"))
			{
			//check for nouns in the tokens. Standard notation in NLP terms
				if(tweet.getTag().equals("NN") || tweet.getTag().equals("NNP") || tweet.getTag().equals("NNS") || tweet.getTag().equals("NNPS")) 
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
		}
		
		System.out.println("\n\n Generating candidates for matching with Landmark gazetteer... \n\n ");
		for(String candidate : candidates)
		{
			System.out.println(candidate);
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
		String regexImgur = "(.)*imgur.com(.)*";
		String regexTwitterPic = "(.)*(pic.twitter.com)(.)*";
		String regexInstagram = "(.)instagram.com(.)*";
		Collection<PopularLandmarks> colLandmarks;
		
		for(String candidate: candidates)
		{
			//System.out.println(candidate);	
			String firstChar = candidate.substring(0, 1);


			//its a hashtag and a noun/proper noun and is has a pic link in the tweet - if it matches 
			//to a landmark -> highest likelihood that it is relevant
			if(firstChar.equals("#") && ( candidate.matches(regexInstagram) || candidate.matches(regexTwitterPic) || candidate.matches(regexImgur)))
			{
				String secondChar = candidate.substring(1,2); //get the second character as the first is a #
				String actualCandidate = candidate.substring(1,candidate.length());
				//System.out.print(secondChar);
				colLandmarks = landmarks.get(secondChar.toUpperCase());
				
				if(colLandmarks != null)
				{
					for(PopularLandmarks pl : colLandmarks)
					{
						Double score = qGram.getSimilarity(actualCandidate.trim(), RemoveSpacesFromString(pl.getName().trim()),2);
						
						if(maxScore < score) 
						{
							maxScore = score;
							matchedLandmark = pl;
							
						}
					}
				}
				
				if(maxScore >= 0.75)
				{
					System.out.println("\n\nMatched Landmark : " + matchedLandmark.getName() + " Score: " + maxScore);
					return matchedLandmark;
				}
			}
			
			//if its  hashtag and matches a landmark return the landmark. Higher priority than the landmark in normal text
			if(firstChar.equals("#"))
			{
				if(candidate.length() > 1)
				{
					String secondChar = candidate.substring(1,2); //get the second character as the first is a #
					colLandmarks = landmarks.get(secondChar.toUpperCase());
					String actualCandidate = candidate.substring(1,candidate.length());
					//System.out.println(secondChar);
					if(colLandmarks != null)
					{
						for(PopularLandmarks pl : colLandmarks)
						{
							Double score = qGram.getSimilarity(actualCandidate.trim(), RemoveSpacesFromString(pl.getName().trim()),2);
							//System.out.println(actualCandidate.trim() + ":" + RemoveSpacesFromString(pl.getName().trim()) + ":score:" + score);
							if(maxScore < score) 
							{
								maxScore = score;
								matchedLandmark = pl;
								
							}
						}
					}
					
					if(maxScore >= 0.75)
					{
						System.out.println("\n\nMatched Landmark : " + matchedLandmark.getName() + " Score: " + maxScore);
						return matchedLandmark;
					}
				}
			}
			
			
			colLandmarks = landmarks.get(firstChar.toUpperCase());
			
			//if(colLandmarks.size() > 0)
			if(colLandmarks != null)
			{
				for(PopularLandmarks pl : colLandmarks)
				{
					Double score = qGram.getSimilarity(candidate.trim(), pl.getName().trim(),2);
					if(maxScore < score) 
					{
						maxScore = score;
						matchedLandmark = pl;
						
					}
				}
			}
		}
		
		if(maxScore >= 0.75)
		{
			System.out.println("\n\nMatched Landmark : " + matchedLandmark.getName() + " Score: " + maxScore);
			return matchedLandmark;
		}
		
		return null;
	}
	
}
