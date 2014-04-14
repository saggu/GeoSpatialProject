package index;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;

/**
 * A Lucene Analyzer that filters WhitespaceTokenizer with
 * LowerCaseFilter.
 * 
 */
public class WhitespaceLowerCaseAnalyzer extends Analyzer{

	public final static Version matchVersion  = Version.LUCENE_47; // faster version //agent 47 - Hitman
	
	//constructor
	public WhitespaceLowerCaseAnalyzer() {}
		
	//provides tokenizer access for the analyzer
	
	@Override
	protected TokenStreamComponents createComponents(final String fieldName, final Reader reader)
	{
		return new TokenStreamComponents(new WhitespaceLowerCaseTokenizer(matchVersion, reader));
	}
	
	
	
}
