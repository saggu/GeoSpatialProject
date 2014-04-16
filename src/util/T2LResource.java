package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class T2LResource {
    public static String getProperty(String property) {
    	Properties prop = new Properties();
    	InputStream in = T2LResource.class.getResourceAsStream("t2l.properties");
    	try {
			prop.load(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return prop.getProperty(property);
    }

}
