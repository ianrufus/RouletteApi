package com.ianrufus.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateValidator {
	public Date startDate;
	public Date endDate;
	
	public static DateValidator ValidDates(String start, String end) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date convertedStartDate;
	    Date convertedEndDate;
	    try {
	    	convertedStartDate = dateFormat.parse(start);
	    	convertedEndDate = dateFormat.parse(end);
	    } catch (ParseException e) {
	    	return null;
	    }
	    Date today = Calendar.getInstance().getTime();
	    
	    if (convertedStartDate.before(convertedEndDate) &&
	    			convertedEndDate.before(today)) {
	    	return new DateValidator() {
	    		{
	    			startDate = convertedStartDate;
	    			endDate = convertedEndDate;
	    		}
	    	};
	    }
		
		return null;
	}
}
