package util;

import java.util.ArrayList;
import java.util.List;



public class QGramDistance {
	 
	    private List<Result> results;
	 
	    public QGramDistance()
	    {
	        results = new ArrayList<Result>();
	    }
	
	 
	    public double getSimilarity(String wordOne, String wordTwo, int n)
	    {
	        List<Result> res1 = processString(wordOne, n);

	        List<Result> res2 = processString(wordTwo, n);

	        int c = common(res1,res2);
	        int u = union(res1,res2);
	        //System.out.print("common: " + c + " ");
	        //System.out.println("union: "+ u );
	        double sim = (double)c/(double)u;
	 
	        return sim;
	    }
	 
	    private int common(List<Result> One, List<Result> Two)
	    {
	        int res = 0;
	 
	        for (int i = 0; i < One.size(); i++)
	        {
	            for (int j = 0; j < Two.size(); j++)
	            {
	                if (One.get(i).getTheWord().equalsIgnoreCase(Two.get(j).getTheWord())) res++;
	            }
	        }
	 
	        return res;
	    }
	 
	    private int union(List<Result> One, List<Result> Two)
	    {
	        List<Result> t = One;
	 
	        for (int i = 0; i < Two.size(); i++)
	        {
	            boolean found = false;
	            for (int j = 0; j < t.size() && !found; j++)
	            {
	                if (Two.get(i).getTheWord().equalsIgnoreCase(t.get(j).getTheWord()))
	                {
	                    found = true;
	                }
	            }
	 
	            if (!found)
	            {
	                Result r = Two.get(i);
	                t.add(r);
	            }
	        }
	 
	        return t.size();
	    }
	 
	    private List<Result> processString(String c, int n)
	    {
	        List<Result> t = new ArrayList<Result>();
	 
	        String spacer = "";
	        for (int i = 0; i < n-1; i++)
	        {
	            spacer = spacer + "%";
	        }
	        c = spacer + c + spacer;
	         
	        for (int i = 0; i < c.length(); i++)
	        {
	            if (i <= (c.length() - n))
	            {
	                if (contains(c.substring(i, n+i)) > 0)
	                {
	                    t.get(i).setTheCount(results.get(i).getTheCount()+1);
	                }
	                else
	                {
	                    t.add(new Result(c.substring(i,n+i),1));
	                }
	            }
	        }
	        return t;
	    }
	 
	    private int contains(String c)
	    {
	        for (int i = 0; i < results.size(); i++)
	        {
	            if (results.get(i).getTheWord().equalsIgnoreCase(c))
	                return i;
	        }
	        return 0;
	    }
	 
	   
}
