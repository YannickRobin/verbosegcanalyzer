package com.ebizance.verbosegcanalyzer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
