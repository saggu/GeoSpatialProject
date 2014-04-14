package util;

public class JaroWinkler {
	
	
	private static int maxPrefLength = 4;
	private static double weight = 0.1;
	
	
	public static double distance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();
		double dist = score(s1, s2);
		dist = dist + commonPrefix(s1, s2, maxPrefLength) * weight * (100 - dist);
		if (dist < 0) dist = 0;
		if (dist > 100) dist = 100;
		return dist;
	}
	
	public static double score(String s1,String s2) {

		/**
		 * TODO: Maybe the commented one is better to solve switched first/last names 
		 * Is there any paper to acknowledge that?
		 */
		int limit = (s1.length() > s2.length()) ? s2.length()/2 + 1 : s1.length()/2 + 1;
		//int limit = (s1.length() > s2.length()) ? s2.length() : s1.length();
		
		String c1 = commonChars(s1, s2, limit);
		String c2 = commonChars(s2, s1, limit);

		if ((c1.length() != c2.length()) || c1.length() == 0 || c2.length() == 0) {
			return 0;
		}
		int transpositions = transpositions(c1, c2);
		return (c1.length() / ((double)s1.length()) + c2.length() / ((double)s2.length()) + (c1.length() - transpositions) / ((double)c1.length())) / 3.0 * 100;
	}
	
	private static String commonChars(String s1, String s2, int limit) {

		StringBuilder common = new StringBuilder(); 
		StringBuilder copy = new StringBuilder(s2);

		for (int i = 0; i < s1.length(); i++) {
			char ch = s1.charAt(i);
			boolean foundIt = false;
			for (int j = Math.max(0, i - limit); !foundIt && j < Math.min(i + limit, s2.length()); j++) {
				if (copy.charAt(j)==ch) {
					foundIt = true;
					common.append(ch);
					copy.setCharAt(j, '*');
				}
			}
		}
		return common.toString();
	}
	
	private static int transpositions(String c1, String c2) {
		int transpositions = 0;
		for (int i = 0; i < c1.length(); i++) {
			if (c1.charAt(i) != c2.charAt(i)) { 
				transpositions++;
			}
		}
		return transpositions / 2;
	}
	
	private static int commonPrefix(String c1,String c2, int maxPref) {
		int n = Math.min(maxPref, Math.min(c1.length(), c2.length()) );
		for (int i = 0; i < n; i++) {
			if (c1.charAt(i) != c2.charAt(i)) {
				return i;
			}
		}
		return n;
	}

}
