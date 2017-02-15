package com.chocomint.asudyog.application.viewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.chocomint.asudyog.application.entities.Invoice;
import com.chocomint.asudyog.application.system.InvoiceGenerator;
import com.chocomint.asudyog.util.CurrentDate;
import com.michaelbaranov.microba.calendar.DatePicker;

import slider.RangeSlider;

public class Search extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1244471050544814253L;
	
	public static final int Invoice_No = 1;
	public static final int Invoice_Date = 2;
	public static final int Messers = 3;
	public static final int Challan_No = 4;
	public static final int Grand_Total = 5;
	
	private JTextField txtChallanNo;
	private JTextField txtMessers;
	private JTextField txtInvoiceNo;
	private JButton btnSearch;
	private JButton btnReset;
	private JRadioButton rdbtnInvoiceNo;
	private JRadioButton rdbtnMessers;
	private JRadioButton rdbtnInvoiceDate;
	private JRadioButton rdbtnChallanNo;
	private JRadioButton rdbtnGrandTotalinr;
	private RangeSlider rangeSlider;
	private DatePicker datePickerImpl;
	private ButtonGroup btnGroup;

	
	 public Search() {
		 	initialize();
	 }
	 
	 private void initialize() {
		// TODO Auto-generated method stub
			setSize(new Dimension(945, 548));
			setLayout(null);
			
			 setBorder(new TitledBorder(null, "Search by", 
					 TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
			 setBounds(10, 10, 925, 82);
			 setLayout(null);
			
			
			txtInvoiceNo = new JTextField();
			txtInvoiceNo.setEnabled(false);
			txtInvoiceNo.setBounds(10, 51, 140, 20);
			 add(txtInvoiceNo);
			txtInvoiceNo.setColumns(10);
			
			rdbtnInvoiceNo = new JRadioButton("Invoice No.");
			rdbtnInvoiceNo.setIconTextGap(35);
			rdbtnInvoiceNo.setHorizontalAlignment(SwingConstants.LEFT);
			rdbtnInvoiceNo.setFont(new Font("Tahoma", Font.BOLD, 11));
			rdbtnInvoiceNo.setBorder(null);
			rdbtnInvoiceNo.setBounds(10, 20, 140, 23);
			rdbtnInvoiceNo.addActionListener(radioListener);
			 add(rdbtnInvoiceNo);
			
			
			rdbtnInvoiceDate = new JRadioButton("Invoice Date");
			rdbtnInvoiceDate.setBackground(SystemColor.control);
			rdbtnInvoiceDate.setIconTextGap(25);
			rdbtnInvoiceDate.setHorizontalAlignment(SwingConstants.LEFT);
			rdbtnInvoiceDate.setFont(new Font("Tahoma", Font.BOLD, 11));
			rdbtnInvoiceDate.setBorder(null);
			rdbtnInvoiceDate.setBounds(160, 20, 143, 23);
			rdbtnInvoiceDate.addActionListener(radioListener);
			 add(rdbtnInvoiceDate);
			
			datePickerImpl = new DatePicker();
			datePickerImpl.setFieldEditable(false);
			datePickerImpl.setEnabled(false);
			datePickerImpl.setBounds(160, 51, 143, 20);
			add(datePickerImpl);
			
			rdbtnMessers = new JRadioButton("Messers");
			rdbtnMessers.setIconTextGap(35);
			rdbtnMessers.setHorizontalAlignment(SwingConstants.LEFT);
			rdbtnMessers.setFont(new Font("Tahoma", Font.BOLD, 11));
			rdbtnMessers.setBorder(new LineBorder(new Color(0, 0, 0)));
			rdbtnMessers.setBounds(313, 20, 143, 23);
			rdbtnMessers.addActionListener(radioListener);
			 add(rdbtnMessers);
			
			txtMessers = new JTextField();
			txtMessers.setEnabled(false);
			txtMessers.setColumns(10);
			txtMessers.setBounds(313, 51, 143, 20);
			 add(txtMessers);
			
			rdbtnChallanNo = new JRadioButton("Challan No.");
			rdbtnChallanNo.setIconTextGap(30);
			rdbtnChallanNo.setHorizontalAlignment(SwingConstants.LEFT);
			rdbtnChallanNo.setFont(new Font("Tahoma", Font.BOLD, 11));
			rdbtnChallanNo.setBorder(new LineBorder(new Color(0, 0, 0)));
			rdbtnChallanNo.setBounds(466, 20, 143, 23);
			rdbtnChallanNo.addActionListener(radioListener);
			 add(rdbtnChallanNo);
			
			txtChallanNo = new JTextField();
			txtChallanNo.setEnabled(false);
			txtChallanNo.setColumns(10);
			txtChallanNo.setBounds(466, 51, 143, 20);
			 add(txtChallanNo);
			
			rdbtnGrandTotalinr = new JRadioButton("Grand Total (INR)");
			rdbtnGrandTotalinr.setIconTextGap(15);
			rdbtnGrandTotalinr.setHorizontalAlignment(SwingConstants.LEFT);
			rdbtnGrandTotalinr.setFont(new Font("Tahoma", Font.BOLD, 11));
			rdbtnGrandTotalinr.setBorder(new LineBorder(new Color(0, 0, 0)));
			rdbtnGrandTotalinr.setBounds(619, 20, 143, 23);
			rdbtnGrandTotalinr.addActionListener(radioListener);
			 add(rdbtnGrandTotalinr);
			
			rangeSlider = new RangeSlider();
			rangeSlider.setEnabled(false);
			rangeSlider.setMinorTickSpacing(500000);
			rangeSlider.setPaintTicks(true);
			rangeSlider.setPaintLabels(true);
			rangeSlider.setValue(2500000);
			rangeSlider.setUpperValue(2500000);
			rangeSlider.setMaximum(5000000);
			rangeSlider.setBounds(619, 51, 143, 20);
			 add(rangeSlider);
			
			btnGroup = new ButtonGroup();
			btnGroup.add(rdbtnInvoiceNo);
			btnGroup.add(rdbtnInvoiceDate);
			btnGroup.add(rdbtnMessers);
			btnGroup.add(rdbtnChallanNo);
			btnGroup.add(rdbtnGrandTotalinr);
			
			btnSearch = new JButton("Search");
			btnSearch.setBounds(772, 20, 143, 23);
			btnSearch.addActionListener(searchListener);
			 add(btnSearch);
			
			btnReset = new JButton("Reset");
			btnReset.setBounds(772, 50, 143, 23);
			btnReset.addActionListener(resetListener);
			 add(btnReset);

	}

	private ActionListener radioListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String rdbtn = e.getActionCommand();
				if(rdbtn.equals("Invoice No.")) {
					datePickerImpl.setEnabled(false);
					txtInvoiceNo.setEnabled(true);
					txtMessers.setEnabled(false);
					txtChallanNo.setEnabled(false);
					rangeSlider.setEnabled(false);
					txtInvoiceNo.requestFocusInWindow();
				}
				if(rdbtn.equals("Invoice Date")) {
					datePickerImpl.setEnabled(true);
					txtInvoiceNo.setEnabled(false);
					txtMessers.setEnabled(false);
					txtChallanNo.setEnabled(false);
					rangeSlider.setEnabled(false);
					datePickerImpl.requestFocusInWindow();
				}
				if(rdbtn.equals("Messers")) {
					txtInvoiceNo.setEnabled(false);
					txtMessers.setEnabled(true);
					txtChallanNo.setEnabled(false);
					rangeSlider.setEnabled(false);
					txtMessers.requestFocusInWindow();
				}
				if(rdbtn.equals("Challan No.")) {
					datePickerImpl.setEnabled(false);
					txtInvoiceNo.setEnabled(false);
					txtMessers.setEnabled(false);
					txtChallanNo.setEnabled(true);
					rangeSlider.setEnabled(false);
					txtChallanNo.requestFocusInWindow();
				}
				if(rdbtn.equals("Grand Total (INR)")) {
					datePickerImpl.setEnabled(false);
					txtInvoiceNo.setEnabled(false);
					txtMessers.setEnabled(false);
					txtChallanNo.setEnabled(false);
					rangeSlider.setEnabled(true);
					rangeSlider.requestFocusInWindow();
				}
			}
			
		};
		
		private ActionListener resetListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int reply =JOptionPane.showConfirmDialog(null, 
						"Are you sure you want to reset the fields?",
						"Reset", JOptionPane.YES_NO_OPTION);
				if(reply == JOptionPane.YES_OPTION) {
					loadNewFields();
					Viewer.r.dePopulateList();
				}
			}
		};
		
		private ActionListener searchListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String rdbtn = getSelectedButtonText(btnGroup);
				Object value[] = new Object[2];
				value[1] = null;
				int mode = 0;
				if(!rdbtn.equals("")) {
					String sql = "select * from Invoice where ";
					if(rdbtn.equals("Invoice No.")) {
						sql = sql + "Bill_No like ?";
						value[0] = txtInvoiceNo.getText();
						mode = Invoice_No;
					}
					if(rdbtn.equals("Invoice Date")) {
						Date d = new Date(datePickerImpl.getDate().getTime());
						value[0] = (Object)d;
						sql = sql + "Bill_Date = ?";
						mode = Invoice_Date;
					}
					if(rdbtn.equals("Messers")) {
						sql = sql + "Messers like ?";
						value[0] = txtMessers.getText();
						mode = Messers;
					}
					if(rdbtn.equals("Challan No.")) {
						sql = sql + "Challan_No like ?";
						value[0] = txtChallanNo.getText();
						mode = Challan_No;
					}
					if(rdbtn.equals("Grand Total (INR)")) {
						sql = sql + "Grand_Total between ? and ?";
						value[0] =  intToDouble(rangeSlider.getValue());
						value[1] =  intToDouble(rangeSlider.getUpperValue());
						mode = Grand_Total;
					}
					ArrayList<Invoice> invoices = InvoiceGenerator.db.getInvoices(sql, value, mode);
					if(invoices.size() == 0) {
						if(mode != Grand_Total)
							JOptionPane.showMessageDialog(null, "Invoice(s) with " + rdbtn + " : " 
									+ value[0] + " doesn't exist in the system!", 
									"Error", JOptionPane.ERROR_MESSAGE);
						else 
							JOptionPane.showMessageDialog(null, "Invoice(s) with " + rdbtn + " : " 
									+ value[0] + " and " + value[1] + " doesn't exist in the system!", 
									"Error",JOptionPane.ERROR_MESSAGE);
					} else {
						Viewer.r.populateTable(invoices);		
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Please provide the value for any one of the fields!", 
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		};
		
		private void loadNewFields() {
			// TODO Auto-generated method stub
			btnGroup.clearSelection();	
			txtInvoiceNo.setText("");
			txtMessers.setText("");
			txtChallanNo.setText("");
			rangeSlider.setValue(2500000);
			rangeSlider.setUpperValue(2500000);
			try {
				datePickerImpl.setDate(CurrentDate.getCurrentSQLDate());
				lockFields();
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.toString(),"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		private void lockFields() {
			// TODO Auto-generated method stub
			txtInvoiceNo.setEnabled(false);
			txtMessers.setEnabled(false);
			txtChallanNo.setEnabled(false);
			rangeSlider.setEnabled(false);
			datePickerImpl.setEnabled(false);
		}

		private String getSelectedButtonText(ButtonGroup buttonGroup) {
	        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements();
	        		buttons.hasMoreElements();) {
	            AbstractButton button = buttons.nextElement();
	            if (button.isSelected()) {
	                return button.getText();
	            }
	        }
	        return "";
	    }
		
		private double intToDouble(int x) {
			double y = 0;
			String t = Integer.toString(x);
			t = t + ".00";
			y = Double.parseDouble(t);
			return y;
		}
}
