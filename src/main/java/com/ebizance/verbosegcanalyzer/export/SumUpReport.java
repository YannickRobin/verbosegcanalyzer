package com.ebizance.verbosegcanalyzer.export;

import com.ebizance.verbosegcanalyzer.gcline.GC;

public class SumUpReport {
	
	double startTime = 0;
	double endTime = 0;
	
	long youngSizeCollected = 0;
	long totalSizeCollected = 0;
	long promotedSize = 0;
	double cpuMinorGCReal = 0;
	double cpuMinorGCUser = 0;
	double cpuMinorGCSystem = 0;
	double cpuMajorGCReal = 0;
	double cpuMajorGCUser = 0;
	double cpuMajorGCSystem = 0;
	
	public SumUpReport()
	{

	}
	
	public void exportGCLine(GC gc)
	{
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
		

	}
	
	public void display()
	{
    	double totalTime = endTime-startTime;
    	
    	System.out.println("\n*** Sum-up report ***\n");
    	
    	System.out.println("Total time: " + Math.round(totalTime*100/60)/100d + " min");
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
