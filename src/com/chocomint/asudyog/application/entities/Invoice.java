package com.chocomint.asudyog.application.entities;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.chocomint.asudyog.util.Constants;


public class Invoice {
	
	private double grand_total;
	private String file_Name;
	private String messers, address;
	private Date bill_date, date_1, date_2;
	private BigDecimal bill_no, buyers_vat_no, buyers_order_no, challan_no;
	
	private int total_items;
	
	public String getFile_Name() {
		return file_Name;
	}


	public void setFile_Name(String file_Name) {
		this.file_Name = file_Name;
	}

	public BigDecimal getBill_no() {
		return bill_no;
	}


	public void setBill_no(String bill_no) {
		this.bill_no = new BigDecimal(bill_no);
	}


	public BigDecimal getBuyers_vat_no() {
		return buyers_vat_no;
	}


	public void setBuyers_vat_no(BigDecimal buyers_vat_no) {
		this.buyers_vat_no = buyers_vat_no;
	}


	public BigDecimal getBuyers_order_no() {
		return buyers_order_no;
	}


	public void setBuyers_order_no(BigDecimal buyers_order_no) {
		this.buyers_order_no = buyers_order_no;
	}


	public BigDecimal getChallan_no() {
		return challan_no;
	}


	public void setChallan_no(BigDecimal bigDecimal) {
		this.challan_no = bigDecimal;
	}

	public String getMessers() {
		return messers;
	}


	public void setMessers(String messers) {
		this.messers = messers;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Date getBill_date() {
		return bill_date;
	}


	public void setBill_date(Date bill_date) {
		this.bill_date = bill_date;
	}


	@SuppressWarnings("finally")
	private Date displayDate(Date date) {
		// TODO Auto-generated method stub
		Date j = null;
		try {
			DateFormat df = new SimpleDateFormat(Constants.getDateFormat);
			String text = df.format(date);
			java.util.Date x = df.parse(text);
			j = new Date(x.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			return j;
		}
	}


	public Date getDate_1() {
		return date_1;
	}


	public void setDate_1(Date date_1) {
		displayDate(date_1);
		this.date_1 = date_1;
	}


	public Date getDate_2() {
		return date_2;
	}


	public void setDate_2(Date date_2) {
		this.date_2 = date_2;
	}


	public int getTotal_items() {
		return total_items;
	}


	public void setTotal_items(int total_items) {
		this.total_items = total_items;
	}


	public Invoice() {
		grand_total = 0;
		messers = address = "";
		total_items = 0;
		file_Name = "zx";
		buyers_order_no = new BigDecimal("0");
		buyers_vat_no = new BigDecimal("0");
		challan_no = new BigDecimal("0");
		
	}


	public double getGrand_total() {
		return grand_total;
	}


	public void setGrand_total(double grand_total) {
		this.grand_total = grand_total;
	}
	
	
}