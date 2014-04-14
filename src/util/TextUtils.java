package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class TextUtils {
	
	 /**
     * Wrapper for calling Apache Commons IO toString(BufferedReader)
     * on a File, essentially providing a File.toString() method for
     * the contents of a text file.
     * 
     */
    public static String fileToString(File file) throws IOException {
        return IOUtils.toString(new BufferedReader(new FileReader(file)));
    }

}
