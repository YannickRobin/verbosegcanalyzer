package com.ebizance.verbosegcanalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @author Yannick Robin
 *
 */
public class VerboseGCAnalyzerConfig 
{
	private static final Logger logger = Logger.getLogger(VerboseGCAnalyzerConfig.class);
	
    private static final String ROOT_DIR_DEFAULT = ".."; 
    private static final String CONFIGURATION_FILE_DEFAULT = File.separator + "conf" + File.separator + "conf.properties"; 

    public static File rootDir;
    public static boolean dislayMajorGC_;
    public static SimpleDateFormat dateFormat_;
    public static SimpleDateFormat exportDateFormat_;
    public static Date startDate_;
    public static Date endDate_;
    public static boolean exportCSV_;
    public static boolean exportHTMLReport_;
      
    static {
    	Properties properties = new Properties();
    	try {
    		
    		String sRootDir;
    		if (System.getenv("verbosegcanalyzer.root_dir") != null)
    			sRootDir = System.getenv("verbosegcanalyzer.root_dir");
    		else
    			sRootDir = ROOT_DIR_DEFAULT;
    		
    		rootDir = new File(sRootDir);    		    		   			
			properties.load(new FileInputStream(sRootDir + CONFIGURATION_FILE_DEFAULT));
			
			dislayMajorGC_ = Boolean.parseBoolean(properties.getProperty("dislayMajorGC", "true"));
			dateFormat_ = new SimpleDateFormat(properties.getProperty("dateFormat"));
			exportDateFormat_ = new SimpleDateFormat(properties.getProperty("exportDateFormat"));
			exportCSV_ = Boolean.parseBoolean(properties.getProperty("exportCSV", "true"));
			exportHTMLReport_ = Boolean.parseBoolean(properties.getProperty("exportHTMLReport", "true"));
			
			try {
				if (!properties.getProperty("startDate", "").equals(""))
					startDate_ = VerboseGCAnalyzerConfig.exportDateFormat_.parse(properties.getProperty("startDate"));
			} catch (ParseException e) {
				new Throwable("Incorrect date format"); 
			}
			
			try {
				if (!properties.getProperty("endDate", "").equals(""))
					endDate_ = VerboseGCAnalyzerConfig.exportDateFormat_.parse(properties.getProperty("endDate"));
			} catch (ParseException e) {
				new Throwable("Incorrect date format"); 
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
