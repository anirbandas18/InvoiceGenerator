package com.chocomint.asudyog.application.generator;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.chocomint.asudyog.application.system.GenerateBarcode;
import com.chocomint.asudyog.application.system.InvoiceGenerator;
import com.chocomint.asudyog.util.Constants;

public class Header extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2095680592121685690L;
	private JLabel lblInvoiceNo, label, lblBillDate, lblVatNo, lblAsUdyog;
	
	public Header() {
		initialize();
	}
	
	private void initialize() {
		setBounds(10, 10, 925, 60);
		setLayout(null);
		
		lblInvoiceNo = new JLabel("Invoice No. : " + InvoiceGenerator.invoiceNumber);
		lblInvoiceNo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInvoiceNo.setHorizontalAlignment(SwingConstants.LEFT);
		lblInvoiceNo.setBounds(10, 10, 200, 22);
		add(lblInvoiceNo);
		
		label = new JLabel(GenerateBarcode.drawingBarcodeDirectToGraphics(InvoiceGenerator.invoiceNumber));
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setToolTipText(InvoiceGenerator.invoiceNumber);
		label.setBounds(10, 38, 200, 22);
		add(label);
		
		lblBillDate = new JLabel("Invoice Date : " + InvoiceGenerator.invoice.getBill_date().toString());
		lblBillDate.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBillDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBillDate.setBounds(729, 10, 180, 22);
		add(lblBillDate);
		
		lblVatNo = new JLabel("VAT No. : " + Constants.VAT_No.toString());
		lblVatNo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVatNo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVatNo.setBounds(729, 38, 180, 22);
		add(lblVatNo);
		
		lblAsUdyog = new JLabel("AS Udyog");
		lblAsUdyog.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAsUdyog.setHorizontalAlignment(SwingConstants.CENTER);
		lblAsUdyog.setBounds(369, 10, 200, 50);
		add(lblAsUdyog);
		
	}
}
