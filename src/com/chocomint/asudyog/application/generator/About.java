package com.chocomint.asudyog.application.generator;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.jdesktop.swingx.prompt.PromptSupport;

import com.chocomint.asudyog.application.system.InvoiceGenerator;

public class About extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3938567981014603001L;
	private JTextField txtMessers;
	private JTextField txtBuyersVatNo;
	private JTextArea txtrAddress;
	private JScrollPane scrollPane;
	private HashMap<String, Boolean> marker;
	private ArrayList<JComponent> c;
	
	public About() {
		containers();
		initialize();
	}
	
	private void containers() {
		marker = new HashMap<String, Boolean>(3);
		c =  new ArrayList<JComponent>(3);
	}
	
	protected void loadNewAbout() {
		txtMessers.setText("");
		txtBuyersVatNo.setText("");
		txtrAddress.setText("");
		unmarkFields();
	}
	
	protected void lockFields(boolean f) {
		txtMessers.setEnabled(f);
		txtBuyersVatNo.setEnabled(f);
		txtrAddress.setEnabled(f);
	}
	
	protected boolean validateAbout() {
		boolean flag = true;
		boolean f[] = new boolean[3];
		if(txtMessers.getText().equals("")) {
			f[0] = false;
		} else {
			f[0] = true;
		}
		if(txtBuyersVatNo.getText().equals("") || Item.isNumeric(txtBuyersVatNo.getText()) == false) {
			f[2] = false;
		} else {
			if(Double.parseDouble(txtBuyersVatNo.getText())>0) {
				f[2] = true;
			} else {
				f[2] = false;
			}
		}
		if(txtrAddress.getText().equals("") || Item.isNumeric(txtrAddress.getText()) == true) {
			f[1] = false;
		} else {
			f[1] = true;
		}
		marker.put("Messers", f[0]);
		marker.put("Address", f[1]);
		marker.put("Buyer's VAT No.", f[2]);
		for(boolean x : f) {
			if(x == false) {
				flag = false;
				break;
			} else {
				flag = true;
			}
		}
		if(flag == false) {
			markFields();
		} else {
			commitAboutDetails();
		}
		return flag;
	}
	
	private void commitAboutDetails() {
		// TODO Auto-generated method stub
		InvoiceGenerator.invoice.setMessers(txtMessers.getText());
		InvoiceGenerator.invoice.setBuyers_vat_no(new BigDecimal(txtBuyersVatNo.getText()));
		InvoiceGenerator.invoice.setAddress(txtrAddress.getText());
	}

	private void markFields() {
		Color c = Color.red;
		for(Map.Entry<String, Boolean> x : marker.entrySet()) {
			if(x.getKey().equals("Messers") && x.getValue() == false) {
				txtMessers.setBackground(c);
			}
			if(x.getKey().equals("Adddress") && x.getValue() == false) {
				txtrAddress.setBackground(c);
			}
			if(x.getKey().equals("Buyer's VAT No.") && x.getValue() == false) {
				txtBuyersVatNo.setBackground(c);
			}
		}
	}
	
	private void unmarkFields() {
		Color c = Color.white;
		txtMessers.setBackground(c);
		txtrAddress.setBackground(c);
		txtBuyersVatNo.setBackground(c);
	}
	
	private void initialize() {
		setBorder(new TitledBorder(
				new TitledBorder(
						UIManager.getBorder("TitledBorder.border"),
						"About", TitledBorder.LEADING, TitledBorder.TOP, 
						null, new Color(0, 0, 0)), 
						"About", TitledBorder.LEADING, TitledBorder.TOP, 
						null, new Color(0, 0, 0)));
		setBounds(10, 80, 925, 87);
		setLayout(null);
		
		txtMessers = new JTextField();
		txtMessers.setColumns(10);
		txtMessers.setBounds(10, 22, 450, 20);
		add(txtMessers);
		PromptSupport.setPrompt("Messers", txtMessers);
		
		txtBuyersVatNo = new JTextField();
		PromptSupport.setPrompt("Buyer's VAT No.", txtBuyersVatNo);
		txtBuyersVatNo.setColumns(10);
		txtBuyersVatNo.setBounds(10, 53, 450, 20);
		add(txtBuyersVatNo);
		
		txtrAddress = new JTextArea();
		txtrAddress.setColumns(10);
		txtrAddress.setFont(new Font("Tahoma", Font.PLAIN, 11));
		PromptSupport.setPrompt("Address", txtrAddress);
		
		scrollPane = new JScrollPane(txtrAddress);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(470, 20, 445, 53);
		add(scrollPane);
		
		marker.put("Messers", true);
		marker.put("Address", true);
		marker.put("Buyer's VAT No.", true);
		
		c.add(txtMessers);
		c.add(txtBuyersVatNo);
		c.add(txtrAddress);
		
	}
}
