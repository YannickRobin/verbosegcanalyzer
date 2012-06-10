package com.ebizance.verbosegcanalyzer;

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
	
    private static final String CONFIGURATION_FILE_DEFAULT = "../conf/conf.properties"; 
	
    public static boolean dislayMajorGC_;
    public static SimpleDateFormat dateFormat_;
    public static SimpleDateFormat exportDateFormat_;
    public static Date startDate_;
    public static Date endDate_;
      
    static {
    	Properties properties = new Properties();
    	try {
    		
    		String configurationFile;
    		if (System.getenv("verbosegcanalyzer.configuration") != null)
    			configurationFile = System.getenv("verbosegcanalyzer.configuration");
    		else
    			configurationFile = CONFIGURATION_FILE_DEFAULT;
    		   			
			properties.load(new FileInputStream(configurationFile));
			dislayMajorGC_ = Boolean.parseBoolean(properties.getProperty("dislayMajorGC", "true"));
			dateFormat_ = new SimpleDateFormat(properties.getProperty("dateFormat"));
			exportDateFormat_ = new SimpleDateFormat(properties.getProperty("exportDateFormat"));
			
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
