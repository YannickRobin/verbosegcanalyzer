package com.ebizance.verbosegcanalyzer;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * Command line application to run Thread dump sampler.<br/><br/>
 * 
 * 
 * @author Yannick Robin
 *
 */
public class VerboseGCAnalyzer
{
	private static final Logger logger = Logger.getLogger(VerboseGCAnalyzer.class);
	
	public static void main(String[] args) throws IOException
    {
    	if (args.length == 0)
    	{
    		displayUsageMessage();
    		System.exit(-1);
    	}
    	
    	String sFile = args[0];
    	File file = new File(sFile);
    	if (!file.exists() || file.isDirectory())
    	{
    		displayUsageMessage();
	    	System.exit(-1);
    	}
			
		VerboseGC verboseGC = new VerboseGC(sFile);
		verboseGC.parse();
    }
    
	private static void displayUsageMessage()
    {
		System.out.println("\nUsage: java -Dlog4j.configuration=file:../conf/log4j.properties -jar ../lib/VerboseGCAnalyzer-1.0-SNAPSHOT.jar <file>");
		System.out.println("");
    }
}
