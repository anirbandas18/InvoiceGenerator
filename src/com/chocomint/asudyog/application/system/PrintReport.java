package com.chocomint.asudyog.application.system;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageable;

public class PrintReport {
	public static boolean printPDF(String fileName) {
		try {
			PrinterJob job = PrinterJob.getPrinterJob();
			PDDocument document = PDDocument.load(
	    		System.getProperty("user.dir") + "//Invoices//" + fileName);
			job.setPrintable(new PDPageable(document, job));
			job.setJobName(fileName);
			boolean r = job.printDialog();
			if(r == true) {
				job.print();
			}
			document.close();
			return true;
		} catch (NullPointerException | IllegalArgumentException
				| PrinterException e) {
			JOptionPane.showMessageDialog(null, e.toString(), 
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.toString(), 
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}
}
