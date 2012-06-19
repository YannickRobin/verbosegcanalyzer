package com.ebizance.verbosegcanalyzer.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.ebizance.verbosegcanalyzer.VerboseGCAnalyzerConfig;
import com.ebizance.verbosegcanalyzer.gcline.GC;

public class CSVExport {
	
	BufferedWriter out;
	
	public CSVExport()
	{
		File file = new File(VerboseGCAnalyzerConfig.rootDir.getAbsolutePath() + File.separator +  "export");
		file.mkdir();
		
		try {
			out = new BufferedWriter(new FileWriter(VerboseGCAnalyzerConfig.rootDir.getAbsolutePath() + File.separator +  "export" + File.separator + "java_gc.csv"));
		} catch (IOException e) {
			new RuntimeException("Unable to open file." + e);
		}
	    	    
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
    	
    	try {
			out.write(header + "\n");
		} catch (IOException e) {
			new RuntimeException("Unable to write file." + e);
		}
	}
	
	public void exportGCLine(GC gc)
	{
		if (gc.getType() == GC.PAR_NEW || VerboseGCAnalyzerConfig.dislayMajorGC_)
		{
    		String line=
    			VerboseGCAnalyzerConfig.exportDateFormat_.format(gc.getDate()) + "," +
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
    		
        	try {
    			out.write(line + "\n");
    		} catch (IOException e) {
    			new RuntimeException("Unable to write file." + e);
    		}
		}
	}
	
	public void close()
	{
    	try {
    		out.close();
		} catch (IOException e) {
			new RuntimeException("Unable to close file." + e);
		}
	}
}
