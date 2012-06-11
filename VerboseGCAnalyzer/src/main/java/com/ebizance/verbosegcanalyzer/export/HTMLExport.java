package com.ebizance.verbosegcanalyzer.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.ebizance.verbosegcanalyzer.VerboseGCAnalyzerConfig;
import com.ebizance.verbosegcanalyzer.gcline.GC;

public class HTMLExport {
	
	TimeSeries seriesYoungSize;
	TimeSeries seriesTenuedSize;
	TimeSeries seriesPromotedSize;
	TimeSeries seriesCollectionPauseTime;
	TimeSeries seriesCpuReal;
	
	private static final File HTML_REPORT_DIR = new File(VerboseGCAnalyzerConfig.rootDir.getAbsolutePath() + File.separator + "export" + File.separator + "html_report");
	
	public HTMLExport()
	{
		HTML_REPORT_DIR.mkdirs();
		
		seriesYoungSize = new TimeSeries("Young size");
		seriesTenuedSize = new TimeSeries("Tenured size");
		seriesPromotedSize = new TimeSeries("Promoted size");
		seriesCollectionPauseTime = new TimeSeries("Collection pause time");
		seriesCpuReal = new TimeSeries("CPU real");
	}
	
	public void exportGCLine(GC gc)
	{
		if (gc.getType() == GC.PAR_NEW)
		{
			seriesYoungSize.add(new FixedMillisecond(gc.getDate().getTime()), gc.getYoungSizeBefore()/1024);
			seriesTenuedSize.add(new FixedMillisecond(gc.getDate().getTime()), gc.getTenuredSizeBefore()/1024);
			seriesPromotedSize.add(new FixedMillisecond(gc.getDate().getTime()), gc.getPromotedSize()/1024);
			seriesCollectionPauseTime.add(new FixedMillisecond(gc.getDate().getTime()), gc.getCollectionPauseTime());
		}
		
		seriesCpuReal.add(new FixedMillisecond(gc.getDate().getTime()),gc.getCpuReal());
		
	}
	
	public void generateChart()
	{
		TimeSeriesCollection datasetYoungSize = new TimeSeriesCollection(seriesYoungSize);
		TimeSeriesCollection datasetTenuredSize = new TimeSeriesCollection(seriesTenuedSize);
		TimeSeriesCollection datasetPromotedSize = new TimeSeriesCollection(seriesPromotedSize);
		TimeSeriesCollection datasetCollectionPauseTime = new TimeSeriesCollection(seriesCollectionPauseTime);
		TimeSeriesCollection datasetCpuReal = new TimeSeriesCollection(seriesCpuReal);

		DateAxis dateAxis = new DateAxis("Time");
		dateAxis.setLowerMargin(0.0D);   
		dateAxis.setUpperMargin(0.0D);
	
		JFreeChart chartYoungSize = ChartFactory.createXYAreaChart("Young size", "Time", "MB", datasetYoungSize, PlotOrientation.VERTICAL, false, false, false);
		XYPlot xyplotYoungSize  = chartYoungSize.getXYPlot();
        xyplotYoungSize.setDomainAxis(dateAxis);
        
		JFreeChart chartTenuredSize = ChartFactory.createXYAreaChart("Tenured size", "Time", "MB", datasetTenuredSize, PlotOrientation.VERTICAL, false, false, false);
		XYPlot xyplotTenuredSize  = chartTenuredSize.getXYPlot();
        xyplotTenuredSize.setDomainAxis(dateAxis);

		JFreeChart chartPromotedSize = ChartFactory.createXYAreaChart("Promoted size", "Time", "MB", datasetPromotedSize, PlotOrientation.VERTICAL, false, false, false);
		XYPlot xyplotPromotedSize  = chartPromotedSize.getXYPlot();
		xyplotPromotedSize.setDomainAxis(dateAxis);
        
        JFreeChart chartCollectionPauseTime = ChartFactory.createXYAreaChart("Minor GC pause time", "Time", "sec", datasetCollectionPauseTime, PlotOrientation.VERTICAL, false, false, false);
		XYPlot xyplotCollectionPauseTime  = chartCollectionPauseTime.getXYPlot();
		xyplotCollectionPauseTime.setDomainAxis(dateAxis);
		
        JFreeChart chartCpuReal = ChartFactory.createXYAreaChart("CPU real", "Time", "sec", datasetCpuReal, PlotOrientation.VERTICAL, false, false, false);
		XYPlot xyplotCpuReal  = chartCpuReal.getXYPlot();
		xyplotCpuReal.setDomainAxis(dateAxis);
		
		File fileYoungSize = new File(HTML_REPORT_DIR.getAbsolutePath() + File.separator + "young_size.jpg");
		File fileTenuredSize = new File(HTML_REPORT_DIR.getAbsolutePath() + File.separator + "tenured_size.jpg");
		File filePromotedSize = new File(HTML_REPORT_DIR.getAbsolutePath() + File.separator + "promoted_size.jpg");
		File fileCollectionPauseTime = new File(HTML_REPORT_DIR.getAbsolutePath() + File.separator + "collection_pause_time.jpg");
		File fileCpuReal = new File(HTML_REPORT_DIR.getAbsolutePath() + File.separator + "cpu_real.jpg");

		try {
			ChartUtilities.saveChartAsJPEG(fileYoungSize, chartYoungSize, 600, 300);
		} catch (IOException e) {
			throw new RuntimeException("Unable to save picture. " + fileYoungSize.getAbsolutePath() + " " + e.getLocalizedMessage());
		}
		
		try {
			ChartUtilities.saveChartAsJPEG(fileTenuredSize, chartTenuredSize, 600, 300);
		} catch (IOException e) {
			throw new RuntimeException("Unable to save picture. " + fileTenuredSize.getAbsolutePath() + " " + e.getLocalizedMessage());
		}
		
		try {
			ChartUtilities.saveChartAsJPEG(filePromotedSize, chartPromotedSize, 600, 300);
		} catch (IOException e) {
			throw new RuntimeException("Unable to save picture. " + filePromotedSize.getAbsolutePath() + " " + e.getLocalizedMessage());
		}
		
		try {
			ChartUtilities.saveChartAsJPEG(fileCollectionPauseTime, chartCollectionPauseTime, 600, 300);
		} catch (IOException e) {
			throw new RuntimeException("Unable to save picture. " + fileCollectionPauseTime.getAbsolutePath() + " " + e.getLocalizedMessage());
		}
		
		try {
			ChartUtilities.saveChartAsJPEG(fileCpuReal, chartCpuReal, 600, 300);
		} catch (IOException e) {
			throw new RuntimeException("Unable to save picture. " + fileCpuReal.getAbsolutePath() + " " + e.getLocalizedMessage());
		}
		
		File srcFileJavaGCReportHTML = new File(VerboseGCAnalyzerConfig.rootDir.getAbsolutePath() + File.separator + "conf" + File.separator + "java_gc_report.html");
		File destFileJavaGCReportHTML = new File(HTML_REPORT_DIR.getAbsolutePath()  + File.separator + "java_gc_report.html");

		try {
			copyFile(srcFileJavaGCReportHTML, destFileJavaGCReportHTML);
		} catch (IOException e) {
			throw new RuntimeException("Unable to copy file. " + srcFileJavaGCReportHTML.getAbsolutePath() + " " + e.getLocalizedMessage());
		}
	}
	
	private static void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}
}
