package com.chocomint.asudyog.util;

import java.io.File;

import javax.swing.JOptionPane;

import com.chocomint.asudyog.application.system.InvoiceGenerator;



/*
 * Anirban Das
 */

public class DirectoryHandler
{
	private static File Invoices, Logs;
	
	public static boolean makeInvoices()
	{
		Invoices = new File(System.getProperty("user.dir") + "\\Invoices");
		if (!Invoices.exists()) 
		{
			boolean res = Invoices.mkdir();
			if(res == false)
			{
				String s = "Directory : Invoices could not be created!"
						+ " Application will not run";
				JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);  
				InvoiceGenerator.log.logerror(s);
			} else {
				InvoiceGenerator.log.loginfo("Directory : Invoices created");
			}
			return res;
		}
		else
		{
			return true;
		}
	}
	public static boolean removeInvoices()
	{
		if(Invoices.exists())
		{
			return Invoices.delete();
		}
		else
		{
			return true;
		}
	}
	public static boolean makeLogs()
	{
		Logs = new File(System.getProperty("user.dir") + "\\Logs");
		if (!Invoices.exists()) 
		{
			boolean res = Logs.mkdir();
			if(res == false)
			{
				String s = "Directory : Logs could not be created!"
						+ " Application will not run";
				JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);  
				InvoiceGenerator.log.logerror(s);
			} else {
				InvoiceGenerator.log.loginfo("Directory : Logs created");
			}
			return res;
		}
		else
		{
			return true;
		}
	}
	public static boolean removeLogs()
	{
		if(Logs.exists())
		{
			return Logs.delete();
		}
		else
		{
			return true;
		}
	}
}