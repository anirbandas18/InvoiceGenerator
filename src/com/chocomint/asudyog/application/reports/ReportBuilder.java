package com.chocomint.asudyog.application.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import com.chocomint.asudyog.application.system.InvoiceGenerator;
import com.chocomint.asudyog.util.Constants;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class ReportBuilder {
	public static String buildReport(ResultSet rs) {
		if(rs != null) {
			String templatePath = ReportBuilder.class.getResource("../../").toString();
			templatePath = templatePath.substring(6).replace("%20", " ") + "reporttemplate/report2.jrxml";
			String invoicePath = Constants.invoicesPath; 
			try {
				JasperReport jasperReport = JasperCompileManager.compileReport(templatePath);
				JRResultSetDataSource jasperReports = new JRResultSetDataSource(rs);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, jasperReports);
				File invoiceDir = new File(invoicePath);
				File invoice = File.createTempFile("Tax Invoice - " + InvoiceGenerator.invoiceNumber,
						".pdf", invoiceDir);
				JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(invoice));
				return invoice.getName();
			} catch (JRException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.toString(),
						"Error", JOptionPane.ERROR_MESSAGE);
				return "";
			}
			catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.toString(),
						"Error", JOptionPane.ERROR_MESSAGE);
				return "";
			}
		} else {
			return "";
		}
	}
}
