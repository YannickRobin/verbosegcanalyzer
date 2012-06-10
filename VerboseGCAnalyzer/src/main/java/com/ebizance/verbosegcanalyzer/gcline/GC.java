package com.ebizance.verbosegcanalyzer.gcline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ebizance.verbosegcanalyzer.VerboseGCAnalyzerConfig;

/**
 * 
 * @author Yannick Robin
 */

public class GC {
	
	public static final int PAR_NEW = 0;
	public static final int CMS_INITIAL_MARK = 1;
	public static final int CMS_CONCURRENT_MARK = 2;
	public static final int CMS_CONCURRENT_PRECLEAN = 3;
	public static final int CMS_CONCURRENT_ABORTABLE_PRECLEAN = 4;
	public static final int CMS_REMARK = 5;
	public static final int CMS_CONCURRENT_SWEEP = 6;
	public static final int CMS_CONCURRENT_RESET = 7;
	
	int type;
	String date;
	double time;
	double collectionPauseTime;
	double cpuUser;
	double cpuSystem;
	double cpuReal;
	
	int youngSizeBefore;
	int youngSizeAfter;
	int youngSizeMax;
	int totalSizeBefore;
	int totalSizeAfter;
	int totalSizeMax;
	
	public int getType() {
		return type;
	}
	
	public String getDisplayType() {
		String sType = "";
		
		switch(getType()) {
			case PAR_NEW:
				sType = "PAR_NEW";
				break;
			case CMS_INITIAL_MARK:
				sType = "CMS_INITIAL_MARK";
				break;
			case CMS_CONCURRENT_MARK:
				sType = "CMS_CONCURRENT_MARK";
				break;
			case CMS_CONCURRENT_PRECLEAN:
				sType = "CMS_CONCURRENT_PRECLEAN";
				break;
			case CMS_CONCURRENT_ABORTABLE_PRECLEAN:
				sType = "CMS_CONCURRENT_ABORTABLE_PRECLEAN";
				break;
			case CMS_REMARK:
				sType = "CMS_REMARK";
				break;
			case CMS_CONCURRENT_SWEEP:
				sType = "CMS_CONCURRENT_SWEEP";
				break;
			case CMS_CONCURRENT_RESET:
				sType = "CMS_CONCURRENT_RESET";
		}
		
		return sType;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public Date getDate() {		
		SimpleDateFormat dateFormat = VerboseGCAnalyzerConfig.dateFormat_;
	    Date convertedDate = null;
		try {
			convertedDate = dateFormat.parse(date);
		} catch (ParseException e) {
			new Throwable("Incorrect date format"); 
		}
	    return convertedDate;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public Double getTime() {
		return time;
	}
	
	public void setTime(Double time) {
		this.time = time;
	}
	
	public double getCollectionPauseTime() {
		return collectionPauseTime;
	}
	
	public void setCollectionPauseTime(double collectionPauseTime) {
		this.collectionPauseTime = collectionPauseTime;
	}
	
	public Double getCpuUser() {
		return cpuUser;
	}
	
	public void setCpuUser(Double cpuUser) {
		this.cpuUser = cpuUser;
	}
	
	public Double getCpuSystem() {
		return cpuSystem;
	}
	
	public void setCpuSystem(Double cpuSystem) {
		this.cpuSystem = cpuSystem;
	}
	
	public Double getCpuReal() {
		return cpuReal;
	}
	
	public void setCpuReal(Double cpuReal) {
		this.cpuReal = cpuReal;
	}	
	
	public int getYoungSizeBefore() {
		return youngSizeBefore;
	}

	public void setYoungSizeBefore(int youngSizeBefore) {
		this.youngSizeBefore = youngSizeBefore;
	}

	public int getYoungSizeAfter() {
		return youngSizeAfter;
	}

	public void setYoungSizeAfter(int youngSizeAfter) {
		this.youngSizeAfter = youngSizeAfter;
	}

	public int getYoungSizeMax() {
		return youngSizeMax;
	}

	public void setYoungSizeMax(int minorSizeMax) {
		this.youngSizeMax = minorSizeMax;
	}

	public int getTotalSizeBefore() {
		return totalSizeBefore;
	}

	public void setTotalSizeBefore(int totalSizeBefore) {
		this.totalSizeBefore = totalSizeBefore;
	}

	public int getTotalSizeAfter() {
		return totalSizeAfter;
	}

	public void setTotalSizeAfter(int totalSizeAfter) {
		this.totalSizeAfter = totalSizeAfter;
	}

	public int getTotalSizeMax() {
		return totalSizeMax;
	}

	public void setTotalSizeMax(int totalSizeMax) {
		this.totalSizeMax = totalSizeMax;
	}
	
	/* Calculated values */
	
	public int getYoungSizeCollected() {
		return youngSizeBefore-youngSizeAfter;
	}

	public int getTotalSizeCollected() {
		return totalSizeBefore-totalSizeAfter;
	}

	public int getPromotedSize() {
		return getYoungSizeCollected()-getTotalSizeCollected();
	}
	
	public int getTenuredSizeBefore() {
		return totalSizeBefore-youngSizeBefore;
	}

	public int getTenuredSizeAfter() {
		return totalSizeAfter-youngSizeAfter;
	}

	public String toString()
	{
		return "date=" + date + "," +
				"time=" + time + "," +
				"type=" + getDisplayType() + "," +
				"cpuReal=" + cpuReal + "," +				
				"cpuUser=" + cpuUser + "," +
				"cpuSystem=" + cpuSystem + "," +
				"collectionPauseTime=" + collectionPauseTime + "," +
				"youngSizeBefore=" + youngSizeBefore + "," +
				"youngSizeAfter=" + youngSizeAfter + "," +
				"youngSizeMax=" + youngSizeMax + "," +
				"totalSizeBefore=" + totalSizeBefore + "," +
				"totalSizeAfter=" + totalSizeAfter + "," +
				"totalSizeMax=" + totalSizeMax + "," +
				"youngSizeCollected=" + getYoungSizeCollected() + "," +
				"totalSizeCollected=" + getTotalSizeCollected() + "," +
				"promotedSize=" + getPromotedSize() + "," +
				"tenuredSizeBefore=" + getTenuredSizeBefore() + "," +
				"tenuredSizeAfter=" + getTenuredSizeAfter();
	}
	
}
