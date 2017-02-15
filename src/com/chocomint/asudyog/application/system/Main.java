package com.chocomint.asudyog.application.system;

import java.awt.EventQueue;
import java.util.prefs.Preferences;

import com.chocomint.asudyog.util.AppLogger;
import com.chocomint.asudyog.util.DirectoryHandler;
import com.chocomint.asudyog.util.SetLAF;

public class Main {
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				AppLogger log = new AppLogger();
				Preferences pref = Preferences.userRoot().
						node("Invoice Generator");
				try {
					SetLAF.setLAF();
					String create = pref.get("create", "");
					if(create.equals("false") || create.equals("")) {
						log.loginfo("First time run");
						boolean r = DirectoryHandler.makeInvoices();
						if(r) {
							log.loginfo("Application started successfully!");
							InvoiceGenerator ig = new InvoiceGenerator(log);
							ig.startApp();
						} else {
							DirectoryHandler.removeInvoices();
							DirectoryHandler.removeLogs();
							pref.put("create", "false");
							log.logerror("First time run falied");
						}
					} else {
						log.loginfo("Application started successfully!");
						InvoiceGenerator ig = new InvoiceGenerator(log);
						ig.startApp();
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.logwarn(e);
				}
			}
		});
	}
}
