package index;

import java.io.Reader;
import org.apache.lucene.analysis.util.CharTokenizer;
import org.apache.lucene.util.Version;

/**
 * LowerCaseTokenizer performs the function of WhitespaceTokenizer
 * and LowerCaseFilter together. It divides text at whitespace and
 * converts them to lower case. While it is functionally equivalent to
 * a combination of WhitespaceTokenizer and LowerCaseFilter, there is a
 * performance advantage to doing the two tasks at once, hence this
 * (redundant) implementation.
 * 
 */
public class WhitespaceLowerCaseTokenizer extends CharTokenizer{
	
	//call super constructor
	public WhitespaceLowerCaseTokenizer(Version matchVersion, Reader in) {
        super(matchVersion, in);
    }
	
	/** Collects only characters which do not satisfy
     * {@link Character#isWhitespace(int)}.
     * 
     * @param c     char being processed
     */
    @Override
    protected boolean isTokenChar(int c) {
        return !Character.isWhitespace(c);
    }

    /** Converts char to lower case
     * {@link Character#toLowerCase(int)}.
     * 
     * @param c     char being processed
     */
    @Override
    protected int normalize(int c) {
        return Character.toLowerCase(c);
    }
	
}
