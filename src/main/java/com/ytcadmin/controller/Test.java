package com.ytcadmin.controller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Test {
	static Logger logger = Logger.getLogger(Test.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SimpleDateFormat format=new SimpleDateFormat("hh:mm a");
		try {			
/*			InputStream is = Test.class.getResourceAsStream("/log4j.properties"); 
			Properties p = new Properties(); 
			p.load(is); 
			PropertyConfigurator.configure(p); 
			File f=new File("log4j.properties");
			System.out.println(f.getAbsolutePath());
			logger.error((format.parse("10:35 PM").getTime()));
			Time t=new Time(format.parse("10:35 PM").getTime());
			logger.info(format.format(t));*/
			Date date=new Date(2018,1,1);
			Calendar test=DateUtils.toCalendar(date);
			System.out.println(date);
			
			Calendar cal = Calendar.getInstance();
			System.out.println(cal.getTime());
			cal.clear();
			System.out.println(cal.getTime());
			cal.setTime(date);
			System.out.println(date.getYear());
			//cal.setTimeInMillis(date.getTime());
			System.out.println(cal.getTime());
			
			 SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
			 Date eventDate= format1.parse("2018/04/17 10:00 PM");
			 java.util.Date evenDate= (java.util.Date) new java.util.Date(eventDate.getTime()+(20*60000));
			 System.out.println(evenDate);
			
			 SimpleDateFormat iCalendarDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
			 
			 System.out.println(iCalendarDateFormat.format(eventDate));
			 String s=null;
			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
