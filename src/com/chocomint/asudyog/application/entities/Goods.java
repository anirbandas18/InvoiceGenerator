package com.chocomint.asudyog.application.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.chocomint.asudyog.util.Constants;

public class Goods {
	
	private HashMap<String,Object> item;
	private ArrayList<HashMap<String,Object>> list;
	private Vector<Object> entry;
	private Invoice invoice;
	
	public Goods() {
		list = new ArrayList<HashMap<String,Object>>();
	}
	
	public void newItem() {
		item = new HashMap<String,Object>();
		entry = new Vector<Object>();
	}
	//setter methods will be called on lost focus of text fields 
	public void setSerialNo(int serial_no) {
		item.put("Serial No.", serial_no);
	}
	public void setDescription(String Description) {
		item.put("Description", Description);
	}
	public void setQuantity(int Quantity) {
		item.put("Quantity", Quantity);
	}
	public void setRate(double Rate) {
		item.put("Rate", Rate);
	}
	public void setPer(String Per) {
		item.put("Per", Per);
	}
	public void setValue(double Value) {
		item.put("Value", Value);
	}
	public void setVAT(double VAT) {
		item.put("VAT", VAT);
	}
	public void setTax(double Tax) {
		item.put("Tax", Tax);
	}
	public void setSubTotal(double SubTotal) {
		item.put("Sub - Total", SubTotal);
	}
	
	public void addItem() {
		list.add(item);
	}
	
	public Vector<Object> getItem() {
		entry.add(item.get("Serial No."));
		entry.add(item.get("Description"));
		entry.add(item.get("Quantity"));
		entry.add(item.get("Rate"));
		entry.add(item.get("Per"));
		entry.add(item.get("Value"));
		entry.add(item.get("VAT"));
		entry.add(item.get("Tax"));
		entry.add(item.get("Sub - Total"));
		return entry;
	}
	
	public void discardItem(int pos) {
		list.remove(pos);
	}
	
	public void setItem(int pos) {
		list.set(pos, item);
	}
	
	public double computeGrandTotal() {
		double gt = 0;
		for(HashMap<String,Object> map : list) {
			for(Map.Entry<String, Object> x : map.entrySet()) {
				if(x.getKey().equals("Sub - Total") ==  true) {
					gt = gt + (double)x.getValue();
				}
			}
		}
		gt = Math.round(gt*100)/100.0d;
		return gt;
	}
	
	public int computeTotalItems() {
		return list.size();
	}
	
	public ArrayList<HashMap<String, Object>> getList() {
		return list;
	}
	
	public String toString() {
		String s  = "";
		if(list.size() != 0) {
			s = "Bill No : " + invoice.getBill_no() +"\n"
				 + "VAT No : " + Constants.VAT_No + "\n"
				 + "Bill Date : " + invoice.getBill_date() + "\n"
				 + "Messers : " + invoice.getMessers() + "\n"
				 + "Buyer's VAT no. : " + invoice.getBuyers_vat_no() + "\n"
				 + "Address : " + invoice.getAddress() + "\n"
				 + "Buyer's Order No. : " + invoice.getBuyers_order_no() + "\n"
				 + "Date 1 : " + invoice.getDate_1().toString() + "\n"
				 + "Challan No. : " + invoice.getChallan_no() + "\n"
				 + "Date 2 : " + invoice.getDate_2().toString() +"\n";
			for(HashMap<String,Object> map : list) {
				for(Map.Entry<String, Object> x : map.entrySet()) {
					s = s + x.getKey() + " : " + x.getValue() + "\n";
				}
			}
			s = s + "Total no. of items : " + (invoice.getTotal_items() > 0 ? invoice.getTotal_items()
					: list.size()) + "\n"
					+ "Grand Total (INR) : " + invoice.getGrand_total();
		} 
		return s;
	}
}
