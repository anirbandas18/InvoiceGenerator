package com.chocomint.asudyog.application.viewer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.chocomint.asudyog.application.entities.Invoice;
import com.chocomint.asudyog.application.reports.ReportBuilder;
import com.chocomint.asudyog.application.reports.ReportViewer;
import com.chocomint.asudyog.application.system.InvoiceGenerator;
import com.chocomint.asudyog.util.Constants;
import com.chocomint.asudyog.util.JTableModel;
import com.chocomint.asudyog.util.PopClickListener;

public class Results extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4405740059656492481L;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	private JPopupMenu popUp;
	private JMenuItem view;
	private JMenuItem generate;
	private JMenuItem deleteRecord, deleteReport;
	private String invoiceNumber;
	
	/**
	 * Create the panel.
	 */
	public Results() {
		createComponents();
		addComponents();
		initialize();
	}
	
	private void createComponents() {
		popUp = new JPopupMenu();
		view = new JMenuItem("View");
		generate = new JMenuItem("Generate");
		deleteRecord = new JMenuItem("Delete Record");
		deleteReport = new JMenuItem("Delete Report");
	}
	
	private void addComponents() {
		view.addActionListener(viewListener);
		generate.addActionListener(generateListener);
		deleteRecord.addActionListener(deleteListener);
		deleteReport.addActionListener(deleteListener);
		popUp.add(view);
		popUp.add(generate);
		popUp.add(deleteRecord);
		popUp.add(deleteReport);
	}
	
	
	private Vector<String> getInvoiceColumns() {
		Vector<String> v = new Vector<String>();
		v.add("Invoice No.");
		v.add("Invoice Date");
		v.add("Messers");
		v.add("Buyer's VAT No.");
		v.add("Address");
		v.add("Buyer's Order No.");
		v.add("Date 1");
		v.add("Challan No.");
		v.add("Date 2");
		v.add("Total No. Of Items");
		v.add("Grand Total (INR)");
		return v;
	}
	
	private void initialize() {
		setBorder(new TitledBorder(null, "Results", TitledBorder.LEADING, 
				TitledBorder.TOP, null, Color.BLACK));
		setBounds(10, 92, 925, 435);
		setLayout(null);
		
		model = new JTableModel(getInvoiceColumns(),0);
		
		table = new JTable(model);
		table.addMouseListener(new PopClickListener(popUp, table));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		setTableRendering();
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 21, 905, 405);
		add(scrollPane);
	}
	
	private void setTableRendering() {
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		table.getColumn("Invoice No.").setCellRenderer(centerRenderer);
		table.getColumn("Invoice Date").setCellRenderer(centerRenderer);
		table.getColumn("Messers").setCellRenderer(leftRenderer);
		table.getColumn("Buyer's VAT No.").setCellRenderer(centerRenderer);
		table.getColumn("Address").setCellRenderer(leftRenderer);
		table.getColumn("Buyer's Order No.").setCellRenderer(centerRenderer);
		table.getColumn("Date 1").setCellRenderer(centerRenderer);
		table.getColumn("Challan No.").setCellRenderer(centerRenderer);
		table.getColumn("Date 2").setCellRenderer(centerRenderer);
		table.getColumn("Total No. Of Items").setCellRenderer(centerRenderer);
		table.getColumn("Grand Total (INR)").setCellRenderer(rightRenderer);
	}
	
	protected void populateTable(ArrayList<Invoice> invoice) {
		dePopulateList();
		for(Invoice x : invoice) {
			model.addRow(toVector(x));
		}
	}
	
	protected void dePopulateList() {
		int rows = model.getRowCount(); 
		for(int i = rows - 1; i >=0; i--)
		{
		   model.removeRow(i); 
		}
	}
	
	private Vector<Object> toVector(Invoice i) {
		Vector<Object> v = new Vector<Object>();
		v.add(i.getBill_no());
		v.add(i.getBill_date());
		v.add(i.getMessers());
		v.add(i.getBuyers_vat_no());
		v.add(i.getAddress());
		v.add(i.getBuyers_order_no());
		v.add(i.getDate_1());
		v.add(i.getChallan_no());
		v.add(i.getDate_2());
		v.add(i.getTotal_items());
		v.add(i.getGrand_total());
		return v;
	}
	
	private ActionListener viewListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			getInvoiceNo();
			String fileName = InvoiceGenerator.db.
					getFileName(invoiceNumber);
			File f = new File(Constants.invoicesPath + fileName);
			if(!f.exists() && !f.isDirectory()) {
				JOptionPane.showMessageDialog(null, "Invoice report doesn't exists in the system!\n"
						+ "Produce report first\nClick on View option to view the report after that",
						"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				ReportViewer.displayReport(fileName);
			}
		}
		
	};
	
	private ActionListener deleteListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getActionCommand().equals("Delete Record")) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete"
						+ " this record from the database?", "Confirm", JOptionPane.YES_NO_OPTION, 
						JOptionPane.WARNING_MESSAGE);
				if(reply == JOptionPane.YES_OPTION) {
					getInvoiceNo();
					int r = InvoiceGenerator.db.removeData(invoiceNumber);
					if(r != 0) {
						JOptionPane.showMessageDialog(null, 
								"Invoice record successfully deleted from the database!", 
								"Success", JOptionPane.INFORMATION_MESSAGE);
						model.removeRow(table.getSelectedRow());
					} else {
						JOptionPane.showMessageDialog(null, 
								"Invoice record deletion from database has failed!", 
								"Error", JOptionPane.ERROR_MESSAGE);
					} 
				}
			} else {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete"
						+ " this report from the system?", "Confirm", JOptionPane.YES_NO_OPTION, 
						JOptionPane.WARNING_MESSAGE);
				if(reply == JOptionPane.YES_OPTION) {
					getInvoiceNo();
					String fileName = InvoiceGenerator.db.getFileName(invoiceNumber);
					File f = new File(Constants.invoicesPath + fileName);
					boolean r = f.delete();
					if(r) {
						JOptionPane.showMessageDialog(null, 
								"Invoice report successfully deleted from the system!", 
								"Success", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, 
								"Invoice report deletion from system has failed!", 
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}		
			}
		}
	};
	
	private ActionListener generateListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			getInvoiceNo();
			String fileName = InvoiceGenerator.db.
					getFileName(invoiceNumber);
			if(!fileName.equals("")) {
				File f = new File(Constants.invoicesPath + fileName);
				if(f.exists() && !f.isDirectory()) {
					JOptionPane.showMessageDialog(null, "Invoice report already exists in the system!\n"
							+ "Report cannot be produced again\nClick on View option to view the report",
							"Error", JOptionPane.ERROR_MESSAGE);
				} else if(!f.exists() && !f.isDirectory()) {
					fileName = ReportBuilder.buildReport(InvoiceGenerator.db.
							getInvoiceData(invoiceNumber));
					if(fileName.equals("") != true) {
						int x = InvoiceGenerator.db.updateInvoiceFileName(fileName, invoiceNumber);
						if(x == 0) {
							JOptionPane.showMessageDialog(null, 
									"File name updation to database failed!", 
									"Error", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, 
									fileName + " generated successfully!\n Click on View option to "
									+ "view the report", "Success", JOptionPane.INFORMATION_MESSAGE);
							int reply = JOptionPane.showConfirmDialog(null, "Do you want to view this generated "
									+ "report?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
							if(reply == JOptionPane.YES_OPTION) {
								ReportViewer.displayReport(fileName);
							}
						} 
					} else {
						JOptionPane.showMessageDialog(null, 
								"Invoice report generation failed!", 
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}	
	};
	
	private void getInvoiceNo() {
		BigDecimal in = (BigDecimal)table.
				getValueAt(table.getSelectedRow(), 0);
		invoiceNumber = in.toPlainString();
	}
}
