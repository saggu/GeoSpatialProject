package gazetteer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class FeatureCodeBuilder {
	/**
     * Reads-in featureCodes_en.txt file, spits-out
     * {@link FeatureClass} enum definitions.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void generateFeatureCodes() throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("./featureCodes_en.txt"), Charset.forName("UTF-8")));
        String line;
        while ((line = in.readLine()) != null) {
            String[] tokens = line.split("\t");
            if (tokens[0].equals("null")) {
                System.out.println("NULL(FeatureClass.NULL, \"not available\", \"\");");
            } else {
                String[] codes = tokens[0].split("\\.");
                if (tokens.length == 3) {
                    System.out.println(codes[1] + "(FeatureClass." + codes[0] + ", \"" + tokens[1] + "\", \"" + tokens[2] + "\"),");
                } else {
                    System.out.println(codes[1] + "(FeatureClass." + codes[0] + ", \"" + tokens[1] + "\", \"\"),");
                }
            }
        }
        in.close();
    }

}
