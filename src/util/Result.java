package util;

public class Result {
	
		private String theWord;
        private int theCount;
 
        public Result(String w, int c)
        {
            theWord = w;
            theCount = c;
        }
 
        public void setTheCount(int c)
        {
            theCount = c;
        }
 
        public String getTheWord()
        {
            return theWord;
        }
 
        public int getTheCount()
        {
            return theCount;
        }
    }
 


