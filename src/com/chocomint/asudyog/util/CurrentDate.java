package com.chocomint.asudyog.util;

import java.sql.Date;
import java.text.DateFormatSymbols;
import java.util.Calendar;


public class CurrentDate {
	
	public static String getCurrentDateString() {
		Calendar c = Calendar.getInstance();
		String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		String month = Integer.toString(c.get(Calendar.MONTH) + 1);
		String year = Integer.toString(c.get(Calendar.YEAR));
		String current_date = year + "-" + month + "-" + day;
		return current_date;
		
	}
	public static String getCurrentDateStringMonth() {
		Calendar c = Calendar.getInstance();
		String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		String month = getMonthForInt(c.get(Calendar.MONTH));
		String year = Integer.toString(c.get(Calendar.YEAR));
		String current_date = year + "-" + month + "-" + day;
		return current_date;
		
	}
	
	public static Date getCurrentSQLDate() {
		java.util.Date x = new java.util.Date();
		Date d = new Date(x.getTime());
		return d;
	}
	
	private static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getShortMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }
}
