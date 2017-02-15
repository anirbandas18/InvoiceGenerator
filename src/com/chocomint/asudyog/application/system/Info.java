package com.chocomint.asudyog.application.system;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.jdesktop.swingx.JXImageView;

public class Info extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4845406133790932348L;

	/**
	 * Create the panel.
	 */
	public Info() {
		setBorder(null);
		setSize(new Dimension(320, 203));
		setLayout(null);
		
		JXImageView imageView = new JXImageView();
		imageView.setImage(Toolkit.getDefaultToolkit().getImage(Info.class.
				getResource("/com/chocomint/asudyog/icons/chocomint.png")));
		imageView.setBorder(new LineBorder(new Color(0, 0, 0)));
		imageView.setMinimumSize(new Dimension(290, 97));
		imageView.setSize(new Dimension(290, 97));
		imageView.setBounds(10, 98, 300, 93);
		add(imageView);
		
		JXImageView imageView_1 = new JXImageView();
		imageView_1.setImage(Toolkit.getDefaultToolkit().getImage(
				Info.class.getResource("/com/chocomint/asudyog/icons/asudyog.png")));
		imageView_1.setBounds(10, 11, 32, 32);
		add(imageView_1);
		
		JLabel lblInvoiceGeneratorV = new JLabel("Invoice Generator v1.0");
		lblInvoiceGeneratorV.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInvoiceGeneratorV.setBounds(52, 11, 187, 19);
		add(lblInvoiceGeneratorV);
		
		JLabel label = new JLabel("2015");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(264, 11, 46, 19);
		add(label);
		
		JLabel lblAsUdyog = new JLabel("AS Udyog");
		lblAsUdyog.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAsUdyog.setBounds(51, 29, 203, 14);
		add(lblAsUdyog);
		
		JLabel lblDevelopedByChocomint = new JLabel("Developed by Chocomint Inc.");
		lblDevelopedByChocomint.setHorizontalAlignment(SwingConstants.CENTER);
		lblDevelopedByChocomint.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDevelopedByChocomint.setBounds(10, 73, 300, 14);
		add(lblDevelopedByChocomint);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 54, 300, 8);
		add(separator);

	}
}
