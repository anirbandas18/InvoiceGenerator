package com.chocomint.asudyog.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.FileAppender;

import com.chocomint.asudyog.application.system.InvoiceGenerator;


public class AppLogger {
   private static final Logger logger = Logger.getLogger(InvoiceGenerator.class);
   public void loginfo(Object e) {
	   logger.info(e);
   }
   public void logerror(Object e) {
	   logger.error(e);
   }
   public void logwarn(Object e) {
	   logger.warn(e);
   }
   public AppLogger() {
	   try {
		   		org.apache.log4j.PropertyConfigurator.configure("log4j.properties");
		   		PatternLayout layout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
		   		Date j = new Date();
		   		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		   		String fileName = sdf.format(j);
		   		FileAppender appender = new FileAppender(layout,"Logs//Log - " + 
		   		fileName + ".out",false);
				logger.addAppender(appender);
				logger.setLevel(Level.DEBUG);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
   }
}
