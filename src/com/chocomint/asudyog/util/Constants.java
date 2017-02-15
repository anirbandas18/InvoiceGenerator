package com.chocomint.asudyog.util;

import java.util.Calendar;

public class Constants {
	public static final String databaseName = System.getProperty("user.dir") + "\\Database\\AS Udyog";
	public static final String invoicesPath = System.getProperty("user.dir") + "\\Invoices\\";
	public static final String getDateFormat = "yyy/mm/dd";
	public static final String VAT_No = "19461031043";
	private static String Bill_No = "";
	public static String getNewBill_No() {
		Calendar c = Calendar.getInstance();
		Bill_No = Bill_No + c.get(Calendar.YEAR);
		Bill_No = Bill_No + c.get(Calendar.MONTH);
		Bill_No = Bill_No + c.get(Calendar.DAY_OF_MONTH);
		Bill_No = Bill_No + c.get(Calendar.HOUR_OF_DAY);
		Bill_No = Bill_No + c.get(Calendar.MINUTE);
		Bill_No = Bill_No + c.get(Calendar.SECOND);
		return Bill_No;
	}
}

