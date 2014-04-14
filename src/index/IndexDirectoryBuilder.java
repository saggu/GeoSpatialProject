package index;

import gazetteer.GeoRecordName;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//Builds  a Lucene index of geographic  entries based on  the geoname gazetteer. One time run before This program can be used

public class IndexDirectoryBuilder {

	public static final Logger logger =  LoggerFactory.getLogger(IndexDirectoryBuilder.class);
	
	//the geonames gazetteer file to be indexed
	
	static String pathToGazetteer = "./allCountries.txt";
	
	/**
     * Turns a GeoNames gazetteer file into a Lucene index, and adds
     * some supplementary gazetteer records at the end.
     */
	
	public static void main(String args[]) throws IOException
	{
		File iDir = new File("./IndexDirectory");
		if(iDir.exists()) //directory exists, exit gracefully
		{
			logger.info("IndexDirectory already exists, remove the directory and try again");
			System.exit(-1);
		}
		
		logger.info("Creating indexes... please wait");
		
		//create a new index file on the disk
		FSDirectory  index = FSDirectory.open(iDir);
		
		//indexing by lower-casing & tokenizing on whitespace
		Analyzer indexAnalyzer = new WhitespaceLowerCaseAnalyzer();
		
		//create object that will create Lucene index
		IndexWriter indexWriter = new IndexWriter(index, new IndexWriterConfig(Version.LUCENE_47, indexAnalyzer));
		
		//open the gazetteer file to be loaded
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(new File(pathToGazetteer)), "UTF-8"));
		
		String line;
		
		//count the time taken
		Date  start = new Date();
		
		//load GeoNames gazetteer into Lucene Index
		int count = 0;
		while ((line = r.readLine()) != null)
            try {
                count++;
                // print progress update to console
                if (count % 100000 == 0 ) logger.info("rowcount: " + count);
                addToIndex(indexWriter, line);
            } catch (Exception e) {
                 logger.info("Skipping... Error on line:" + line);
            }
		
		Date stop = new Date(); // stop it now
		
		logger.info("DONE");
		logger.info(indexWriter.maxDoc() + " geonames added to index");
		logger.info("Merging indices... please wait");
		
		indexWriter.close();
		index.close();
		r.close();
		
		logger.info("[Done]");
		
		
		DateFormat df = new  SimpleDateFormat("HH:mm:ss");
		long elapsed_MILLIS = stop.getTime() - start.getTime();
		logger.info("Process started: " + df.format(start) + ", ended: " + df.format(stop)
                + "; elapsed time: " + MILLISECONDS.toSeconds(elapsed_MILLIS) + " seconds.");
	
		
	}
	
	//add entries to the Lucene Index for each unique name associated with a Geoname object
	
	private static  void addToIndex(IndexWriter indexWriter, String geoNameEntry) throws IOException
	{
		GeoRecordName grn = GeoRecordName.parseGeoNameRecord(geoNameEntry);
		
		//add the primary name for this location
		if(grn.geoRecordName.length() > 0)
			indexWriter.addDocument(buildDoc(grn.asciiName, geoNameEntry, grn.geoRecordID, grn.population));
		
		// add alternate names (if any) if they differ from the primary
        // and alternate names
        for (String altName : grn.alternateNames)
            if (altName.length() > 0 && !altName.equals(grn.geoRecordName))
                indexWriter.addDocument(buildDoc(altName, geoNameEntry, grn.geoRecordID, grn.population));

	}
	
	/**
     * Builds a Lucene document to be added to the index based on a
     * specified name for the location and the corresponding
     * {@link GeoName} object.
    */
    public static Document buildDoc(String name, String geonameEntry, int geoRecordID, Long population) {

        Document doc = new Document();
        
        // this is essentially the key we'll try to match location
        // names against
        doc.add(new TextField("indexName", name, Field.Store.YES));
        
        // this is the payload we'll return when matching location
        // names to gazetteer records
        doc.add(new StoredField("geoname", geonameEntry));
        
 
        doc.add(new IntField("geoRecordID", geoRecordID, Field.Store.YES));
        
        // we'll initially sort match results based on population 
        //TODO CHECK IF THIS IS RELEVANT 
        doc.add(new LongField("population", population, Field.Store.YES));
        
        logger.debug("Adding to index: " + name);
        
        return doc;
    }

}
