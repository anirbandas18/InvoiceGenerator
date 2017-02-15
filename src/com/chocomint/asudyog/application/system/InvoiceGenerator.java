package com.chocomint.asudyog.application.system;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Date;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.chocomint.asudyog.application.entities.Goods;
import com.chocomint.asudyog.application.entities.Invoice;
import com.chocomint.asudyog.application.generator.Generator;
import com.chocomint.asudyog.application.reports.ReportBuilder;
import com.chocomint.asudyog.application.reports.ReportViewer;
import com.chocomint.asudyog.application.viewer.Viewer;
import com.chocomint.asudyog.util.AppLogger;
import com.chocomint.asudyog.util.Constants;
import com.chocomint.asudyog.util.CurrentDate;

public class InvoiceGenerator {
	
	/**
	 * Anirban Das
	 */
	private JFrame frmInvoiceGenerator;
	private Toolkit tool;
	private JTabbedPane tabbedPane;
	private JMenuBar menuBar;
	private JMenu file, invoice_, help;
	private JMenuItem new_bill, generate, print, exit, about, view;
	private Generator g;
	private Viewer v;
	public static boolean isNewInvoice;
	public static DBConnectivity db;
	public static Goods goods;
	public static Invoice invoice;
	public static String fileName, invoiceNumber;
	public static AppLogger log;
	
	public InvoiceGenerator(AppLogger log) {
		InvoiceGenerator.log = log;
		makeNewInvoice();
		createComponents();
		setUpComponents();
		addComponents();
	}
	
	private void createComponents() {
		frmInvoiceGenerator = new JFrame("Invoice Generator - AS Udyog");
		tool = Toolkit.getDefaultToolkit();
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		menuBar = new JMenuBar();
		file = new JMenu("File");
		invoice_ = new JMenu("Invoice");
		help = new JMenu("Help");
		generate = new JMenuItem("Generate");
		view = new JMenuItem("View");
		new_bill = new JMenuItem("New Invoice");
		print = new JMenuItem("Print");
		exit = new JMenuItem("Exit");
		about = new JMenuItem("About");
		db = new DBConnectivity();
		g = new Generator();
		v = new Viewer();
	}
	
	private void setUpComponents() {
		frmInvoiceGenerator.setMinimumSize(new Dimension(977, 640));
		frmInvoiceGenerator.setMaximumSize(new Dimension(977, 640));
		frmInvoiceGenerator.setResizable(false);
		frmInvoiceGenerator.setSize(new Dimension(974, 640));
		frmInvoiceGenerator.setIconImage(tool.getImage(InvoiceGenerator.class.
						getResource("/com/chocomint/asudyog/icons/asudyog.png")));
		frmInvoiceGenerator.setBounds(100, 100, 450, 300);
		frmInvoiceGenerator.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmInvoiceGenerator.getContentPane().setLayout(null);
		frmInvoiceGenerator.setLocationRelativeTo(null);
		frmInvoiceGenerator.setJMenuBar(menuBar);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setBounds(10, 11, 950, 563);
		view.setEnabled(false);
		print.setEnabled(false);
		db.connect(true);
	}
	
	private void addComponents() {
		file.add(new_bill);
		file.add(exit);
		invoice_.add(generate);
		invoice_.add(view);
		invoice_.add(print);
		help.add(about);
		menuBar.add(file);
		menuBar.add(invoice_);
		menuBar.add(help);
		tabbedPane.addTab("Generator", g);
		tabbedPane.addTab("Viewer", v);
		frmInvoiceGenerator.getContentPane().add(tabbedPane);
		frmInvoiceGenerator.addWindowListener(windowListener);
		new_bill.addActionListener(newBillListener);
		generate.addActionListener(generateListener);
		print.addActionListener(printListener);
		exit.addActionListener(exitListener);
		about.addActionListener(aboutListener);
		view.addActionListener(viewListener);
	}
	
	public void startApp() {
		g.loadNewInvoice();
		frmInvoiceGenerator.setVisible(true);
	}
	
	private void exitAction() {
		// TODO Auto-generated method stub
		int reply = JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?",
				"Confirm Exit", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION)
		{
			db.connect(false);
			frmInvoiceGenerator.dispose();
			System.exit(0);
		}	
	}
	
	
	private WindowListener windowListener = new WindowListener() {

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			exitAction();
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private ActionListener exitListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			exitAction();
		}
		
	};
	
	private ActionListener generateListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			boolean success = g.validateFields();
			success = true;
			if(success) {
				int r = db.inputData(invoice, goods);
				if(r == 1) {
					fileName = ReportBuilder.buildReport(db.getInvoiceData(invoiceNumber));
					if(fileName.equals("") != true) {
						invoice.setFile_Name(fileName);
						int x = db.updateInvoiceFileName(fileName, invoiceNumber);
						if(x == 0) {
							String y = "File name updation to database failed!";
							JOptionPane.showMessageDialog(null, y, "Error", JOptionPane.ERROR_MESSAGE);
							log.logerror(y);
						} else {
							isNewInvoice = false;
							view.setEnabled(true);
							print.setEnabled(true);
							generate.setEnabled(false);
							String e = fileName + " generated successfully!";
							JOptionPane.showMessageDialog(null, e,
									"Information", JOptionPane.INFORMATION_MESSAGE);
							log.loginfo(e);
							ReportViewer.displayReport(fileName);
							g.lockFields(false);
						}
					} else {
						String s = fileName + " generation failed!";
						JOptionPane.showMessageDialog(null, s ,"Error", JOptionPane.ERROR_MESSAGE);
						log.logerror(s);
					}
				} else {
					String s = "Error updating database!";
					JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
					log.logerror(s);
				}
			} else {
				String s = "Invalid Fields! "
						+ "Please rectify the fields marked in red";
				JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
				log.logwarn(s);
			}
		}
	};
	
	private ActionListener newBillListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			makeNewInvoice();
			g.lockFields(true);
			g.loadNewInvoice();
			generate.setEnabled(true);
			print.setEnabled(false);
			view.setEnabled(false);
		}

	};

	private void makeNewInvoice() {
		invoice = new Invoice();
		goods = new Goods();
		invoiceNumber = Constants.getNewBill_No();
		isNewInvoice = true;
		invoice.setBill_date(Date.valueOf(CurrentDate.getCurrentDateString()));
		invoice.setBill_no(invoiceNumber);
	}
	
	private ActionListener viewListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			ReportViewer.displayReport(fileName);
		}
	};
	
	private ActionListener printListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			PrintReport.printPDF(fileName);
		}
	};
	
	private ActionListener aboutListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			AboutDialog.show();
		}
	};
}
