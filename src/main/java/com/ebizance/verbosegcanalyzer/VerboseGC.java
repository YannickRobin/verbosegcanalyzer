package com.ebizance.verbosegcanalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jfree.data.general.SeriesException;

import com.ebizance.verbosegcanalyzer.export.CSVExport;
import com.ebizance.verbosegcanalyzer.export.HTMLExport;
import com.ebizance.verbosegcanalyzer.export.SumUpReport;
import com.ebizance.verbosegcanalyzer.gcline.GC;

/**
 *
 *  
 * @author Yannick Robin
 * 
 * 
 */

public class VerboseGC {
    
    private BufferedReader in = null;
    
	public VerboseGC(String filePath)
	{
		try {
		    in = new BufferedReader(new FileReader(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public void parse() throws IOException
    {	
    	System.out.println("*** Start parsing ***");
    	  
    	SumUpReport sumUpReport = new SumUpReport();
    	
    	HTMLExport htmlExport = null;
    	if (VerboseGCAnalyzerConfig.exportHTMLReport_)
    		htmlExport = new HTMLExport();
    	
    	CSVExport csvExport = null;
    	if (VerboseGCAnalyzerConfig.exportCSV_)
    		csvExport = new CSVExport();
    	   	
    	String str;
    	int i=0;
		while ((str = in.readLine()) != null) {
			i++;
			GC gc = null;						
			try {
				if (str.contains("ParNew:"))
				{
					gc = parseMinorGC(str);
				} else if (str.contains("CMS-initial-mark:"))
				{
					gc = parseCMSInitialMark(str);
				} else if (str.contains("CMS-concurrent-mark:"))
				{
					gc = parseCMSConcurrentMark(str);
				} else if (str.contains("CMS-concurrent-preclean:"))
				{
					gc = parseCMSConcurrentPreclean(str);
				} else if (str.contains("CMS-concurrent-abortable-preclean:"))
				{
					gc = parseCMSConcurrentAbortablePreclean(str);
				} else if (str.contains("CMS-remark:"))
				{
					gc = parseCMSRemark(str);
				} else if (str.contains("CMS-concurrent-sweep:"))
				{
					gc = parseCMSConcurrentSweep(str);
				} else if (str.contains("CMS-concurrent-reset:"))
				{
					gc = parseCMSConcurrentReset(str);
				}
			}
			catch(RuntimeException e)
			{
				System.out.println("Unable to parse line " + i);
			}			
						
			boolean valid = false;
			
			if (gc!=null)
			{
				valid = true;
				if (VerboseGCAnalyzerConfig.startDate_ != null &&
						VerboseGCAnalyzerConfig.startDate_.after(gc.getDate())
					)
					valid= false;
				if (VerboseGCAnalyzerConfig.endDate_ != null &&
						VerboseGCAnalyzerConfig.endDate_.before(gc.getDate())
					)
					valid= false;	
			}
			
			if (valid==true)
			{
				try {
					sumUpReport.exportGCLine(gc);
					if (VerboseGCAnalyzerConfig.exportHTMLReport_)
						htmlExport.exportGCLine(gc);
					if (VerboseGCAnalyzerConfig.exportCSV_)
						csvExport.exportGCLine(gc);				
				} catch (Exception e)
				{
					System.err.println("Unable to export line " + i);
				}
			}
		}	
		
		sumUpReport.display();
		if (VerboseGCAnalyzerConfig.exportHTMLReport_)
			htmlExport.generateChart();
		if (VerboseGCAnalyzerConfig.exportCSV_)
			csvExport.close();		
    }
    
    private GC parseMinorGC(String str)
    {
		GC gc = new GC();
    	gc.setType(GC.PAR_NEW);
		gc = parseDate(str, gc);
		
		String[] tokens = str.split("[:]");

		String[] tokensGC = tokens[6].split("[->K(), ]");
		gc.setYoungSizeBefore(Integer.parseInt(tokensGC[1]));
		gc.setYoungSizeAfter(Integer.parseInt(tokensGC[4]));
		gc.setYoungSizeMax(Integer.parseInt(tokensGC[6]));
		gc.setTotalSizeBefore(Integer.parseInt(tokensGC[12]));
		gc.setTotalSizeAfter(Integer.parseInt(tokensGC[15]));
		gc.setTotalSizeMax(Integer.parseInt(tokensGC[17]));
		//minor collection pause time: tokensGC[10]
		//entire gc collection pause time: tokensGC[21]
		gc.setCollectionPauseTime(Double.parseDouble(tokensGC[21]));
		
		String[] tokensCPU = tokens[7].split("[=, ]");
		gc = parseCPU(tokensCPU, gc);
				
		return gc;
	}

	private GC parseCMSInitialMark(String str) {		
		String[] tokens = str.split("[:]");		
		String[] tokensCPU = tokens[6].split("[=, ]");
				
		GC gc = new GC();
    	gc.setType(GC.CMS_INITIAL_MARK);
		gc = parseDate(str, gc);
		gc = parseCPU(tokensCPU, gc);
		
    	return gc;
	}    
	
	private GC parseCMSConcurrentMark(String str) {
		String[] tokens = str.split("[:]");		
		String[] tokensCPU = tokens[6].split("[=, ]");
		
		GC gc = new GC();
    	gc.setType(GC.CMS_CONCURRENT_MARK);
		gc = parseDate(str, gc);
		gc = parseCPU(tokensCPU, gc);
		
    	return gc;
	}
	
	private GC parseCMSConcurrentPreclean(String str) {
		String[] tokens = str.split("[:]");		
		String[] tokensCPU = tokens[6].split("[=, ]");
		
		GC gc = new GC();
    	gc.setType(GC.CMS_CONCURRENT_PRECLEAN);
		gc = parseDate(str, gc);
		gc = parseCPU(tokensCPU, gc);
		
    	return gc;
	}
	
	private GC parseCMSConcurrentAbortablePreclean(String str) {
		String[] tokens = str.split("[:]");		
		String[] tokensCPU = tokens[6].split("[=, ]");
		
		GC gc = new GC();
    	gc.setType(GC.CMS_CONCURRENT_ABORTABLE_PRECLEAN);
		gc = parseDate(str, gc);
		gc = parseCPU(tokensCPU, gc);
			
    	return gc;
	}
	
	private GC parseCMSRemark(String str) {
		String[] tokens = str.split("[:]");		
		String[] tokensCPU = tokens[9].split("[=, ]");
		
		GC gc = new GC();
    	gc.setType(GC.CMS_REMARK);
		gc = parseDate(str, gc);
		gc = parseCPU(tokensCPU, gc);
		
    	return gc;
	}
	
	private GC parseCMSConcurrentSweep(String str) {
		String[] tokens = str.split("[:]");		
		String[] tokensCPU = tokens[6].split("[=, ]");
		
		GC gc = new GC();
    	gc.setType(GC.CMS_CONCURRENT_SWEEP);
		gc = parseDate(str, gc);
		gc = parseCPU(tokensCPU, gc);
		
    	return gc;
	}

    private GC parseCMSConcurrentReset(String str) {
		String[] tokens = str.split("[:]");		
		String[] tokensCPU = tokens[6].split("[=, ]");
		
		GC gc = new GC();
    	gc.setType(GC.CMS_CONCURRENT_RESET);
		gc = parseDate(str, gc);
		gc = parseCPU(tokensCPU, gc);

		return gc;
	}

	private GC parseDate(String str, GC gc)
	{
		String[] tokens = str.split("[:]");
		
		String date = tokens[0] + ":" + tokens[1] + ":" + tokens[2];
		gc.setDate(date);
		double time = Double.parseDouble(tokens[3]);
		gc.setTime(time);
		
    	return gc;
	}
	
	private GC parseCPU(String[] tokensCPU, GC gc)
	{
		gc.setCpuUser(Double.parseDouble(tokensCPU[2]));
		gc.setCpuSystem(Double.parseDouble(tokensCPU[4]));
		gc.setCpuReal(Double.parseDouble(tokensCPU[7]));	
    	
		return gc;
	}
	
	private void displayLineParsing(String str)
    {
		String[] tokens = str.split("[:]");
		for (int i=0; i<tokens.length; i++)
		{
			System.out.println(i + ":" + tokens[i]);
		}
    }

}