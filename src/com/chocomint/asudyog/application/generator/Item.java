package com.chocomint.asudyog.application.generator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import org.jdesktop.swingx.prompt.PromptSupport;

import com.chocomint.asudyog.application.system.InvoiceGenerator;

public class Item extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3899996727417286885L;
	/**
	 * 
	 */
	private JLabel lblSerialNo;
	private JLabel label_1;
	private JTextField txtDescription;
	private JTextField txtQuantity;
	private JTextField txtRateinr;
	private JTextField txtPer;
	private JTextField txtValueinr;
	private JTextField txtVat;
	private JTextField txtTazinr;
	private JTextField txtSubTotalinr;
	private JButton btnCalculateItemCost;
	private JButton btnAddItem;
	private JButton btnDiscardItem;
	private JButton btnNewItem;
	private HashMap<String, Boolean> marker;
	private ArrayList<JComponent> c;
	private static int serial_no;
	protected static final int ADD = 1, EDIT = 2;
	
	public Item() {
		serial_no = 1;
		marker = new HashMap<String, Boolean>(5);
		c =  new ArrayList<JComponent>(8);
		initialize();
		toggleFields(false);
		btnAddItem.setEnabled(false);
		btnDiscardItem.setEnabled(false);
		btnNewItem.setEnabled(false);
		InvoiceGenerator.goods.newItem();
	}

	protected void setNewItem() {
		Vector<Object> v = InvoiceGenerator.goods.getItem();
		toggleFields(true);
		Iterator<JComponent> i = c.iterator();
		for(Object x : v) {
			JComponent y = i.next();
			if (y instanceof JTextComponent) {
				if(x instanceof String) {
					((JTextComponent) y).setText((String)x);
				} else if (x instanceof Integer) {
					((JTextComponent) y).setText(Integer.toString((int)x));
				} else if (x instanceof Double) {
					((JTextComponent) y).setText(Double.toString((double)x));
				}
		    } else if (y instanceof JLabel) {
		        ((JLabel) y).setText(Integer.toString((int)x));
		    }
		}
		toggleFields(false);
		btnCalculateItemCost.setEnabled(true);
		btnAddItem.setEnabled(true);
		btnDiscardItem.setEnabled(true);
		btnNewItem.setEnabled(false);
		txtDescription.requestFocusInWindow();
	}
	
	protected void loadNewItem(boolean f) { 
		if(f ==  true) {
			serial_no = 1;
		}
		label_1.setText(Integer.toString(serial_no));
		unmarkFields();
		toggleFields(true);
		txtDescription.setText("");
		txtPer.setText("");
		txtQuantity.setText("");
		txtRateinr.setText("");
		txtSubTotalinr.setText("");
		txtTazinr.setText("");
		txtVat.setText("");
		txtValueinr.setText("");
		toggleFields(false);
		changeButton(ADD);
		btnCalculateItemCost.setEnabled(true);
		btnAddItem.setEnabled(false);
		btnDiscardItem.setEnabled(false);
		btnNewItem.setEnabled(false);
		InvoiceGenerator.goods.newItem();
	}
	
	protected void lockFields(boolean f) { 
		txtValueinr.setEnabled(f);
		txtTazinr.setEnabled(f);
		txtSubTotalinr.setEnabled(f);
		btnCalculateItemCost.setEnabled(f);
		btnAddItem.setEnabled(f);
		btnDiscardItem.setEnabled(f);
		btnNewItem.setEnabled(f);
	}
	
	
	private void toggleFields(boolean f) {
		txtValueinr.setFocusable(f);
		txtTazinr.setFocusable(f);
		txtSubTotalinr.setFocusable(f);
		txtValueinr.setEditable(f);
		txtTazinr.setEditable(f);
		txtSubTotalinr.setEditable(f);
	}
	
	private void toGoods(int MODE) {
		if(MODE == ADD) {
			InvoiceGenerator.goods.setSerialNo(serial_no);
		} else {
			InvoiceGenerator.goods.setSerialNo(Integer.parseInt(label_1.getText()));
		}
		InvoiceGenerator.goods.setDescription(txtDescription.getText());
		InvoiceGenerator.goods.setQuantity(Integer.parseInt(txtQuantity.getText()));
		InvoiceGenerator.goods.setRate(Double.parseDouble(txtRateinr.getText()));
		InvoiceGenerator.goods.setPer(txtPer.getText());
		InvoiceGenerator.goods.setValue(Double.parseDouble(txtValueinr.getText()));
		InvoiceGenerator.goods.setVAT(Double.parseDouble(txtVat.getText()));
		InvoiceGenerator.goods.setTax(Double.parseDouble(txtTazinr.getText()));
		InvoiceGenerator.goods.setSubTotal(Double.parseDouble(txtSubTotalinr.getText()));
	}
	
	private ActionListener calculateItemListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			boolean result = validateGoods();
			if(result == true) {
				unmarkFields();
				toggleFields(true);
				int q = Integer.parseInt(txtQuantity.getText());
				double r = Double.parseDouble(txtRateinr.getText());
				double v = q * r;
				v = Math.round(v*100)/100.0d;
				txtValueinr.setText(Double.toString(v));
				double vt = Double.parseDouble(txtVat.getText());
				txtVat.setText(Double.toString(vt));
				double t = v + (v*(vt/100));
				t = Math.round(t*100)/100.0d;
				txtTazinr.setText(Double.toString(t));
				double st = v + t;
				st = Math.round(st*100)/100.0d;
				txtSubTotalinr.setText(Double.toString(st));
				toggleFields(false);
				btnCalculateItemCost.setEnabled(false);
				btnDiscardItem.setEnabled(true);
				btnAddItem.setEnabled(true);
				btnAddItem.requestFocusInWindow();
			} else {
				markFields();
				JOptionPane.showMessageDialog(null, "Invalid data! Please rectify the field(s) marked in red",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	};
	
	private void addGoods(int MODE) {
		InvoiceGenerator.goods.newItem();
		toGoods(MODE);
		Vector<Object> v = InvoiceGenerator.goods.getItem();
		if(MODE == ADD) {
			InvoiceGenerator.goods.addItem();
			InvoiceGenerator.invoice.setTotal_items(InvoiceGenerator.goods.computeTotalItems());
			Generator.l.setTotalItems(Integer.toString(InvoiceGenerator.invoice.getTotal_items()));
			Generator.l.addToList(v);
		}
		if(MODE == EDIT) {
			int pos = Generator.l.getCurrentRow();
			InvoiceGenerator.goods.setItem(pos);
			Generator.l.deleteFromList();
			Generator.l.updateList(pos, v);
		}
		InvoiceGenerator.invoice.setGrand_total(InvoiceGenerator.goods.computeGrandTotal());
		Generator.l.setGrandTotal(Double.toString(InvoiceGenerator.invoice.getGrand_total()));
		btnCalculateItemCost.setEnabled(false);
		btnAddItem.setEnabled(false);
		btnDiscardItem.setEnabled(false);
		btnNewItem.setEnabled(true);
		btnNewItem.requestFocusInWindow();
	}
	
	private ActionListener addUpdateItemListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(btnAddItem.getText().equals("Add Item")) {
				boolean r = validateGoods();
				if(r == true) {
					unmarkFields();
					addGoods(ADD);
				} else {
					markFields();
					JOptionPane.showMessageDialog(null, "Invalid data! Please rectify the field(s) marked in red",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				// code for updating table
				boolean r = validateGoods();
				if(r == true) {
					int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this item in invoice?",
							"Confirm Action", JOptionPane.YES_NO_OPTION);
					if(reply == JOptionPane.YES_OPTION) {
						unmarkFields();
						addGoods(EDIT);
					}
				} else {
					markFields();
					JOptionPane.showMessageDialog(null, "Invalid data! Please rectify the field(s) marked in red",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
	};
	
	private void markFields() {
		Color c = Color.red;
		for(Map.Entry<String, Boolean> x : marker.entrySet()) {
			if(x.getKey().equals("Description") && x.getValue() == false) {
				txtDescription.setBackground(c);
			}
			if(x.getKey().equals("Quantity") && x.getValue() == false) {
				txtQuantity.setBackground(c);
			}
			if(x.getKey().equals("Rate") && x.getValue() == false) {
				txtRateinr.setBackground(c);
			}
			if(x.getKey().equals("Per") && x.getValue() == false) {
				txtPer.setBackground(c);
			}
			if(x.getKey().equals("VAT") && x.getValue() == false) {
				txtVat.setBackground(c);
			}
		}
	}
	
	private void unmarkFields() {
		Color c = Color.white;
		txtDescription.setBackground(c);
		txtQuantity.setBackground(c);
		txtRateinr.setBackground(c);
		txtPer.setBackground(c);
		txtVat.setBackground(c);
	}
	
	private ActionListener discardItemListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to discard this item from invoice?",
					"Confirm Action", JOptionPane.YES_NO_OPTION);
			if(reply == JOptionPane.YES_OPTION) {
				loadNewItem(false);
			}
		}
		
	};
	
	private ActionListener newItemListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(btnAddItem.getText().equals("Update Item")) {
				btnAddItem.setText("Add Item");
			} else {
				serial_no++;
			}
			loadNewItem(false);
			InvoiceGenerator.goods.newItem();
			label_1.setText(Integer.toString(serial_no));
			txtDescription.requestFocusInWindow();
		}
		
	};
	
	private boolean validateGoods() {
		boolean flag = true;
		boolean f[] = new boolean[5];
		if(txtDescription.getText().equals("")) {
			f[0] = false;
		} else {
			f[0] = true;
		}
		if(txtQuantity.getText().equals("") || Item.isNumeric(txtQuantity.getText()) == false) {
			f[1] = false;
		} else {
			if(Double.parseDouble(txtQuantity.getText())>0) {
				f[1] = true;
			} else {
				f[1] = false;
			}
		}
		if(txtRateinr.getText().equals("") || Item.isNumeric(txtRateinr.getText()) == false) {
			f[2] = false;
		} else {
			if(Double.parseDouble(txtRateinr.getText())>0) {
				f[2] = true;
			} else {
				f[2] = false;
			}
		}
		if(txtPer.getText().equals("") || Item.isNumeric(txtPer.getText()) == true) {
			f[3] = false;
		} else {
			f[3] = true;
		}
		if(txtVat.getText().equals("") || Item.isNumeric(txtVat.getText()) == false) {
			f[4] = false;
		} else {
			if(Double.parseDouble(txtVat.getText())>0) {
				f[4] = true;
			} else {
				f[4] = false;
			}
		}
		
		marker.put("Description", f[0]);
		marker.put("Quantity", f[1]);
		marker.put("Rate", f[2]);
		marker.put("Per", f[3]);
		marker.put("VAT", f[4]);
		
		for(boolean x : f) {
			if(x == false) {
				flag = false;
				break;
			} else {
				flag = true;
			}
		}
		return flag;
	}
	
	public static boolean isNumeric(String inputData) {
		  return inputData.matches("[-+]?\\d+(\\.\\d+)?");
		}
	
	private void initialize() {
		setBorder(new TitledBorder(
				null, "Goods", TitledBorder.LEADING, TitledBorder.TOP, 
				null, Color.BLACK));
		setBounds(10, 228, 325, 297);
		setLayout(null);
		
		lblSerialNo = new JLabel("Serial No. : ");
		lblSerialNo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSerialNo.setBorder(null);
		lblSerialNo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSerialNo.setBounds(10, 20, 181, 19);
		add(lblSerialNo);
		
		label_1 = new JLabel("");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setBorder(null);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(201, 20, 114, 19);
		add(label_1);
		
		txtDescription = new JTextField();
		PromptSupport.setPrompt("Description", txtDescription);
		txtDescription.setBounds(10, 50, 181, 20);
		txtDescription.setColumns(10);
		add(txtDescription);
		
		txtQuantity = new JTextField();
		PromptSupport.setPrompt("Quantity", txtQuantity);
		txtQuantity.setColumns(10);
		txtQuantity.setBounds(10, 81, 181, 20);
		 add(txtQuantity);
		
		txtRateinr = new JTextField();
		PromptSupport.setPrompt("Rate (INR)", txtRateinr);
		txtRateinr.setColumns(10);
		txtRateinr.setBounds(10, 112, 181, 20);
		 add(txtRateinr);
		
		txtPer = new JTextField();
		PromptSupport.setPrompt("Per", txtPer);
		txtPer.setColumns(10);
		txtPer.setBounds(10, 143, 181, 20);
		 add(txtPer);
		
		txtValueinr = new JTextField();
		txtValueinr.setEnabled(false);
		PromptSupport.setPrompt("Value (INR)", txtValueinr);
		txtValueinr.setColumns(10);
		txtValueinr.setBounds(10, 174, 181, 20);
		 add(txtValueinr);
		
		txtVat = new JTextField();
		PromptSupport.setPrompt("VAT @ %", txtVat);
		txtVat.setColumns(10);
		txtVat.setBounds(10, 205, 181, 20);
		 add(txtVat);
		
		txtTazinr = new JTextField();
		PromptSupport.setPrompt("Tax (INR)", txtTazinr);
		txtTazinr.setEnabled(false);
		txtTazinr.setColumns(10);
		txtTazinr.setBounds(10, 236, 181, 20);
		 add(txtTazinr);
		
		txtSubTotalinr = new JTextField();
		PromptSupport.setPrompt("Sub Total (INR)", txtSubTotalinr);
		txtSubTotalinr.setEnabled(false);
		txtSubTotalinr.setColumns(10);
		txtSubTotalinr.setBounds(10, 267, 181, 20);
		add(txtSubTotalinr);
		
		btnCalculateItemCost = new JButton("Calculate Item Cost");
		btnCalculateItemCost.addActionListener(calculateItemListener);
		btnCalculateItemCost.setMargin(new Insets(2, 5, 2, 5));
		btnCalculateItemCost.setBounds(201, 49, 114, 52);
		 add(btnCalculateItemCost);
		
		btnAddItem = new JButton("Add Item");
		btnAddItem.addActionListener(addUpdateItemListener);
		btnAddItem.setEnabled(false);
		btnAddItem.setMargin(new Insets(2, 5, 2, 5));
		btnAddItem.setBounds(201, 111, 114, 52);
		 add(btnAddItem);
		
		btnDiscardItem = new JButton("Discard Item");
		btnDiscardItem.addActionListener(discardItemListener);
		btnDiscardItem.setMargin(new Insets(2, 5, 2, 5));
		btnDiscardItem.setEnabled(false);
		btnDiscardItem.setBounds(201, 173, 114, 52);
		 add(btnDiscardItem);
		
		btnNewItem = new JButton("New Item");
		btnNewItem.addActionListener(newItemListener);
		btnNewItem.setMargin(new Insets(2, 5, 2, 5));
		btnNewItem.setEnabled(false);
		btnNewItem.setBounds(201, 235, 114, 52);
		 add(btnNewItem);
		
		c.add(label_1);
		c.add(txtDescription);
		c.add(txtQuantity);
		c.add(txtRateinr);
		c.add(txtPer);
		c.add(txtValueinr);
		c.add(txtVat);
		c.add(txtTazinr);
		c.add(txtSubTotalinr);
		
		marker.put("Description", true);
		marker.put("Quantity", true);
		marker.put("Rate", true);
		marker.put("Per", true);
		marker.put("VAT", true);
	}
	
	protected void decrementSerialNo() {
		serial_no--;
		label_1.setText(Integer.toString(serial_no));
	}
	
	protected void changeButton(int mode) {
		if(mode == EDIT) {
			btnAddItem.setText("Update Item");
		} else if(mode == ADD) {
			btnAddItem.setText("Add Item");
		}
	}

}