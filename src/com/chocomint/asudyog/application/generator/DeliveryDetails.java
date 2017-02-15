package com.chocomint.asudyog.application.generator;

import java.awt.Color;
import java.beans.PropertyVetoException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;




import org.jdesktop.swingx.prompt.PromptSupport;

import com.chocomint.asudyog.application.system.InvoiceGenerator;
import com.chocomint.asudyog.util.CurrentDate;
import com.michaelbaranov.microba.calendar.DatePicker;

public class DeliveryDetails extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8542233372359431601L;
	public static final int DATE1 = 1, DATE2 = 2;
	private JTextField txtBuyersOrderNo;
	private JTextField txtChallanNo;
	private DatePicker datePicker1, datePicker2;
	private Date d;
	private HashMap<String, Boolean> marker;
	private ArrayList<JComponent> c;
	private boolean f1[], f2[];
	
	public DeliveryDetails() {
		f1 = new boolean[2];
		f2 = new boolean[2];
		marker = new HashMap<String, Boolean>(4);
		c =  new ArrayList<JComponent>(4);
		initialize();
	}
	
	protected void loadNewDeliveryDetails() {
		txtBuyersOrderNo.setText("");
		txtChallanNo.setText("");
		try {
			datePicker1.setDate(CurrentDate.getCurrentSQLDate());
			datePicker2.setDate(CurrentDate.getCurrentSQLDate());
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		unmarkFields();
	}
	
	protected void lockFields(boolean f) {
		txtBuyersOrderNo.setEnabled(f);
		txtChallanNo.setEnabled(f);
		datePicker1.setEnabled(f);
		datePicker2.setEnabled(f);
	}
	
	private Date getDate(int DATE) {
		switch(DATE) {
			case DeliveryDetails.DATE1 : d = new Date(datePicker1.getDate().getTime());
										 break;
			case DeliveryDetails.DATE2 : d = new Date(datePicker2.getDate().getTime());
			 						     break;
		}
		return d;
	}
	
	private void markFields_1() {
		Color c = Color.red;
		for(Map.Entry<String, Boolean> x : marker.entrySet()) {
			if(x.getKey().equals("Buyer's Order No.") && x.getValue() == false) {
				txtBuyersOrderNo.setBackground(c);
			}
			if(x.getKey().equals("Challan No.") && x.getValue() == false) {
				txtChallanNo.setBackground(c);
			}
		}
	}
	
	private void markFields_2() {
		String msg = "";
		for(Map.Entry<String, Boolean> x : marker.entrySet()) {
			if(x.getKey().equals("Date 1") && x.getValue() == false) {
				msg = msg + "Date 1 is invalid!" + "\n";
			}
			if(x.getKey().equals("Date 2") && x.getValue() == false) {
				msg = msg + "Date 2 is invalid!" + "\n";
			}
		}
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	private void unmarkFields() {
		Color c = Color.white;
		txtBuyersOrderNo.setBackground(c);
		datePicker1.setBackground(c);
		txtChallanNo.setBackground(c);
		datePicker2.setBackground(c);
	}
	
	private boolean validateDeliveryDetails_1() {
		boolean flag = true;
		if(txtChallanNo.getText().equals("") || Item.isNumeric(txtChallanNo.getText()) == false) {
			f1[1] = false;
		} else {
			if(Double.parseDouble(txtChallanNo.getText())>0) {
				f1[1] = true;
			} else {
				f1[1] = false;
			}
		}
		if(txtBuyersOrderNo.getText().equals("") || Item.isNumeric(txtBuyersOrderNo.getText()) == false) {
			f1[0] = false;
		} else {
			if(Double.parseDouble(txtBuyersOrderNo.getText())>0) {
				f1[0] = true;
			} else {
				f1[0] = false;
			}
		}
		marker.put("Buyer's Order No.", f1[0]);
		marker.put("Challan No.", f1[1]);
		for(boolean x : f1) {
			if(x == false) {
				flag = false;
				break;
			} else {
				flag = true;
			}
		}
		if(flag == false) {
			markFields_1();
		}
		return flag;
	}
	
	private boolean validateDeliveryDetails_2() {
		boolean flag = true;
		Date dateModel1 = getDate(DATE1);
		Date dateModel2 = getDate(DATE2);
		java.util.Date sysDate = new java.util.Date(); 
		Date systemDate = new Date(sysDate.getTime());
		if(dateModel1.getTime() < systemDate.getTime() ||
				dateModel1.getTime() > dateModel2.getTime()) {
			f2[0] = false;
		} else {
			f2[0] = true;
		}
		if(dateModel2.getTime() < systemDate.getTime()
				|| dateModel2.getTime() < dateModel1.getTime()) {
			f2[1] = false;
		} else {
			f2[1] = true;
		}
		marker.put("Date 1", f2[0]);
		marker.put("Date 2", f2[1]);
		for(boolean x : f2) {
			if(x == false) {
				flag = false;
				break;
			} else {
				flag = true;
			}
		}
		if(flag == false) {
			markFields_2();
		}
		return flag;
	}
	
	protected boolean validateDeliveryDetails() {
		boolean flag = true;
		boolean x = validateDeliveryDetails_1();
		boolean y = validateDeliveryDetails_2();
		if(x && y) {
			flag = true;
			commitDeliveryDetails();
		} else {
			flag = false;
		}
		return flag;
	}
	
	private void initialize() {
		setBounds(10, 170, 925, 56);
		setBorder(new TitledBorder(null, "Delivery Details", TitledBorder.LEADING, 
				TitledBorder.TOP, null, Color.BLACK));
		setLayout(null);
		
		txtBuyersOrderNo = new JTextField();
		PromptSupport.setPrompt("Buyer's Order No.", txtBuyersOrderNo);
		txtBuyersOrderNo.setColumns(10);
		txtBuyersOrderNo.setBounds(10, 22, 290, 20);
		add(txtBuyersOrderNo);
		
		
		txtChallanNo = new JTextField();
		PromptSupport.setPrompt("Challan No.", txtChallanNo);
		txtChallanNo.setColumns(10);
		txtChallanNo.setBounds(461, 22, 290, 20);
		add(txtChallanNo);
		
		datePicker1 = new DatePicker();
		datePicker1.setFieldEditable(false);
		datePicker1.setBounds(310, 22, 141, 18);
		add(datePicker1);
		
		
		datePicker2 = new DatePicker();
		datePicker2.setFieldEditable(false);
		datePicker2.setBounds(761, 22, 148, 18);
		add(datePicker2);
		
		c.add(txtBuyersOrderNo);
		c.add(datePicker1);
		c.add(txtChallanNo);
		c.add(datePicker2);
		
		marker.put("Buyer's Order No.", true);
		marker.put("Date 1", true);
		marker.put("Challan No.", true);
		marker.put("Date 2", true);
	}
	
	private void commitDeliveryDetails() {
		InvoiceGenerator.invoice.setBuyers_order_no(new BigDecimal(txtBuyersOrderNo.getText()));
		InvoiceGenerator.invoice.setChallan_no(new BigDecimal(txtChallanNo.getText()));
		InvoiceGenerator.invoice.setDate_1(getDate(DATE1));
		InvoiceGenerator.invoice.setDate_2(getDate(DATE2));
	}
}
