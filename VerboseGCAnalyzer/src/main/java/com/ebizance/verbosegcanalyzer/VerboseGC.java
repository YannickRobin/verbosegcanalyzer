package com.ebizance.verbosegcanalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
    	String str;
    	List<GC> gcs = new LinkedList<GC>();
    	int i=0;
		while ((str = in.readLine()) != null) {
			i++;
						
			try {
				if (str.contains("ParNew:"))
				{
					GC gc = parseMinorGC(str);
					gcs.add(gc);
				} else if (str.contains("CMS-initial-mark:"))
				{
					GC gc = parseCMSInitialMark(str);
					gcs.add(gc);
				} else if (str.contains("CMS-concurrent-mark:"))
				{
					GC gc = parseCMSConcurrentMark(str);
					gcs.add(gc);
				} else if (str.contains("CMS-concurrent-preclean:"))
				{
					GC gc = parseCMSConcurrentPreclean(str);
					gcs.add(gc);
				} else if (str.contains("CMS-concurrent-abortable-preclean:"))
				{
					GC gc = parseCMSConcurrentAbortablePreclean(str);
					gcs.add(gc);
				} else if (str.contains("CMS-remark:"))
				{
					GC gc = parseCMSRemark(str);
					gcs.add(gc);
				} else if (str.contains("CMS-concurrent-sweep:"))
				{
					GC gc = parseCMSConcurrentSweep(str);
					gcs.add(gc);
				} else if (str.contains("CMS-concurrent-reset:"))
				{
					GC gc = parseCMSConcurrentReset(str);
					gcs.add(gc);
				}
			}
			catch(RuntimeException e)
			{
				System.out.println("Unable to parse line " + i);
			}			
			
		}	
		
		displayGC(gcs);		
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
		
		String date = tokens[0] + ":" + tokens[1];
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
    	
	private void displayGC(List<GC> gcs)
    {
    	System.out.println("*** display GC ***");
    	
    	double startTime = 0;
    	double endTime = 0;
    	
    	int youngSizeCollected = 0;
    	int totalSizeCollected = 0;
    	int promotedSize = 0;
    	double cpuMinorGCReal = 0;
    	double cpuMinorGCUser = 0;
    	double cpuMinorGCSystem = 0;
    	double cpuMajorGCReal = 0;
    	double cpuMajorGCUser = 0;
    	double cpuMajorGCSystem = 0;
    	
    	String header =
	    	"date," +
			"time," +
			"type," +
			"cpuReal," +				
			"cpuUser," +
			"cpuSystem," +
			"collectionPauseTime," +
			"youngSizeBefore," +
			"youngSizeAfter," +
			"youngSizeMax," +
			"totalSizeBefore," +
			"totalSizeAfter," +
			"totalSizeMax," +
			"youngSizeCollected," +
			"totalSizeCollected," +
			"promotedSize," +
			"tenuredSizeBefore," +
			"tenuredSizeAfter";
    	
    	System.out.println(header);
    	
    	for (Iterator <GC> it=gcs.iterator(); it.hasNext();)
    	{
    		GC gc = (GC) it.next();
    		
    		if (startTime==0)
    			startTime = gc.getTime();
    		
    		endTime = gc.getTime();
    		
    		if (gc.getType()==GC.PAR_NEW)
    		{
	    		cpuMinorGCReal += gc.getCpuReal();
	    		cpuMinorGCUser += gc.getCpuUser();
	    		cpuMinorGCSystem += gc.getCpuSystem();
    		} else
    		{
	    		cpuMajorGCReal += gc.getCpuReal();
	    		cpuMajorGCUser += gc.getCpuUser();
	    		cpuMajorGCSystem += gc.getCpuSystem();
    		}	
    		
    		youngSizeCollected += gc.getYoungSizeCollected();
    		totalSizeCollected += gc.getTotalSizeCollected();
    		promotedSize += gc.getPromotedSize();
    		
    		String line=
    			gc.getDate() + "," +
    			gc.getTime() + "," +
    			gc.getDisplayType() + "," +
    			gc.getCpuReal() + "," +				
    			gc.getCpuUser() + "," +
    			gc.getCpuSystem() + "," +
    			gc.getCollectionPauseTime() + "," +
    			gc.getYoungSizeBefore() + "," +
    			gc.getYoungSizeAfter() + "," +
    			gc.getYoungSizeMax() + "," +
    			gc.getTotalSizeBefore() + "," +
    			gc.getTotalSizeAfter() + "," +
    			gc.getTotalSizeMax() + "," +
    			gc.getYoungSizeCollected() + "," +
    			gc.getTotalSizeCollected() + "," +
    			gc.getPromotedSize() + "," +
    			gc.getTenuredSizeBefore() + "," +
    			gc.getTenuredSizeAfter();
    		
    		boolean dislayMajorGC = false;
    		
    		if (gc.getType() == GC.PAR_NEW || VerboseGCAnalyzerConfig.dislayMajorGC_)
    			System.out.println(line);
    	}
    	
    	double totalTime = endTime-startTime;
    	
    	System.out.println("\nTotal time: " + Math.round(totalTime*100/60)/100d + " min");
    	System.out.println("Young size collected: " + youngSizeCollected/1024/1024 + " GB");
    	System.out.println("Young size collected: " + Math.round((youngSizeCollected/1024)*1000/totalTime)/1000d + " MB/s");
    	System.out.println("Total size collected by minor GC: " + totalSizeCollected/1024/1024 + " GB");
    	System.out.println("Total size collected by minor GC: " + Math.round((totalSizeCollected/1024)*1000/totalTime)/1000d + " MB/s");
    	System.out.println("Promoted size: " + promotedSize/1024/1024 + " GB");
    	System.out.println("Promoted size: " + Math.round((promotedSize/1024)*1000/totalTime)/1000d + " MB/s");
    	System.out.println("Cpu Minor GC real: " + Math.round(10000*cpuMinorGCReal/totalTime)/100d + "%");
    	System.out.println("Cpu Major GC total processors: " + Math.round(10000*(cpuMinorGCUser+cpuMinorGCSystem)/totalTime)/100d + "%");
    	System.out.println("Cpu Major GC real: " + Math.round(10000*cpuMajorGCReal/totalTime)/100d + "%");
    	System.out.println("Cpu Major GC total processors: " + Math.round(10000*(cpuMajorGCUser+cpuMajorGCSystem)/totalTime)/100d + "%");

    }

}