package com.chocomint.asudyog.application.generator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.chocomint.asudyog.application.system.InvoiceGenerator;
import com.chocomint.asudyog.util.JTableModel;
import com.chocomint.asudyog.util.PopClickListener;


public class List extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4999325293905277978L;

	private JLabel lblTotalNoOf;
	private JLabel lblNoOfItems;
	private JLabel lblGrandTotalinr;
	private JLabel lblTotal;
	private JTable table_1;
	private DefaultTableModel model;
	private JPopupMenu popUp;
	private JMenuItem edit, delete;
	
	public List() {
		initialize();
		createComponents();
		addComponents();
	}
	
	private void createComponents() {
		popUp = new JPopupMenu();
		edit = new JMenuItem("Edit");
		delete = new JMenuItem("Delete");
	}
	
	private void addComponents() {
		edit.addActionListener(editListener);
		delete.addActionListener(deleteListener);
		popUp.add(edit);
		popUp.add(delete);
		table_1.addMouseListener(new PopClickListener(popUp, table_1));
	}
	
	private Vector<String> getListColumns() {
		Vector<String> v = new Vector<String>();
		v.add("Serial No.");
		v.add("Description");
		v.add("Quantity");
		v.add("Rate (INR)");
		v.add("Per");
		v.add("Value (INR)");
		v.add("VAT @ %");
		v.add("Tax (INR)");
		v.add("Sub Total (INR)");
		return v;
	}
	
	protected void loadNewList() {
		lblNoOfItems.setText("");
		lblTotal.setText("");
		while(model.getRowCount() != 0) {
			for(int i=0;i<model.getRowCount();i++) {
				model.removeRow(i);
			}
		}
	}
	
	protected void lockFields(boolean f) {
		table_1.setEnabled(f);
	}
	
	protected void addToList(Vector<Object> v) {
		model.addRow(v);
	}
	
	protected void updateList(int pos, Vector<Object> v) {
		model.insertRow(pos, v);
	}
	
	private ActionListener editListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			InvoiceGenerator.goods.newItem();
			InvoiceGenerator.goods.setSerialNo((int)table_1.getValueAt(
					table_1.getSelectedRow(), 0));
			InvoiceGenerator.goods.setDescription((String)table_1.getValueAt(table_1.
					getSelectedRow(), 1));
			InvoiceGenerator.goods.setQuantity((int)table_1.getValueAt(
					table_1.getSelectedRow(), 2));
			InvoiceGenerator.goods.setRate((double)table_1.getValueAt(
					table_1.getSelectedRow(), 3));
			InvoiceGenerator.goods.setPer((String)table_1.getValueAt(table_1.
					getSelectedRow(), 4));
			InvoiceGenerator.goods.setValue((double)table_1.getValueAt(
					table_1.getSelectedRow(), 5));
			InvoiceGenerator.goods.setVAT((double)table_1.getValueAt(
					table_1.getSelectedRow(), 6));
			InvoiceGenerator.goods.setTax((double)table_1.getValueAt(
					table_1.getSelectedRow(), 7));
			InvoiceGenerator.goods.setSubTotal((double)table_1.getValueAt(
					table_1.getSelectedRow(), 8));
			Generator.i.setNewItem();
			Generator.i.changeButton(Item.EDIT);
		}
	};
	
	private ActionListener deleteListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			int reply = JOptionPane.showConfirmDialog(null, "Are you sre you want to delete this item?", 
					"Delete", JOptionPane.YES_NO_OPTION);
			if(reply == JOptionPane.YES_OPTION) {
				InvoiceGenerator.goods.discardItem(table_1.getSelectedRow());
				deleteFromList();
				Generator.i.decrementSerialNo();
			}
		}
		
	};
	
	protected void deleteFromList() {
		model.removeRow(table_1.getSelectedRow());
	}
	
	protected boolean validateList() {
		if(table_1.getRowCount() == 0) {
			markField(false);
			return false;
		} else {
			markField(true);
			return true;
		}
	}
	
	private void setTableRendering() {
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		table_1.getColumn("Serial No.").setCellRenderer(centerRenderer);
		table_1.getColumn("Description").setCellRenderer(leftRenderer);
		table_1.getColumn("Quantity").setCellRenderer(centerRenderer);
		table_1.getColumn("Rate (INR)").setCellRenderer(centerRenderer);
		table_1.getColumn("Per").setCellRenderer(centerRenderer);
		table_1.getColumn("Value (INR)").setCellRenderer(centerRenderer);
		table_1.getColumn("VAT @ %").setCellRenderer(centerRenderer);
		table_1.getColumn("Tax (INR)").setCellRenderer(centerRenderer);
		table_1.getColumn("Sub Total (INR)").setCellRenderer(rightRenderer);
	}
	
	private void markField(boolean f) {
		Color c;
		if(f) {
			c = Color.white;
		} else {
			c = Color.red;
		}
		table_1.setBackground(c);
	}
	
	protected void setGrandTotal(String g) {
		lblTotal.setText(g);
	}
	
	protected void setTotalItems(String g) {
		lblNoOfItems.setText(g);
	}
	
	protected int getCurrentRow() {
		return table_1.getSelectedRow();
	}
	
	private void initialize() {
		setBorder(new TitledBorder(null, "List", TitledBorder.LEADING, 
				TitledBorder.TOP, null, Color.BLACK));
		setBounds(345, 228, 589, 296);
		setLayout(null);
		
		lblTotalNoOf = new JLabel("Total No. Of Items : ");
		lblTotalNoOf.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalNoOf.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalNoOf.setBorder(null);
		lblTotalNoOf.setBounds(10, 267, 112, 19);
		add(lblTotalNoOf);
		
		lblNoOfItems = new JLabel("");
		lblNoOfItems.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNoOfItems.setHorizontalAlignment(SwingConstants.LEFT);
		lblNoOfItems.setBorder(null);
		lblNoOfItems.setBounds(131, 267, 114, 19);
		add(lblNoOfItems);
		
		lblGrandTotalinr = new JLabel("Grand Total (INR) : ");
		lblGrandTotalinr.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGrandTotalinr.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGrandTotalinr.setBorder(null);
		lblGrandTotalinr.setBounds(336, 267, 114, 19);
		add(lblGrandTotalinr);
		
		lblTotal = new JLabel("Total\r\n");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBorder(null);
		lblTotal.setBounds(460, 267, 114, 19);
		add(lblTotal);
		
		model = new JTableModel(getListColumns(),0);
		
		table_1 = new JTable(model);
		table_1.setFillsViewportHeight(true);
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		setTableRendering();
		
		JScrollPane scrollPane = new JScrollPane(table_1);
		scrollPane.setBounds(12, 22, 564, 234);
		add(scrollPane);
	
	}
}