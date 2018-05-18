package com.tiredex.yoko.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.tiredex.yoko.exceptions.TiredexAppException;
import com.tiredex.yoko.exceptions.TiredexUnhandledException;

/**
 * @author AlainG
 * @version $Revision: 1 $   $Date: 4/16/15 $
 *
 */
public class EmailSenderUtils {
	
    private static final String CLASS_NAME = EmailSenderUtils.class.getSimpleName();
    
	
    
	private static final String EMAIL_SENDER_DETAILS_BUNDLE = "com.tiredex.yoko.utils.EmailSenderDetailBundle";

	private static final String MKTG_MAT_REQ_EMAIL_ADDR_FROM_YTC_P_PROP_KEY = "MKTG_MAT_REQ_EMAIL_ADDR_FROM_YTC_P";
	private static final String MKTG_MAT_REQ_EMAIL_ADDR_TO_YTC_P_PROP_KEY = "MKTG_MAT_REQ_EMAIL_ADDR_TO_YTC_P";
	private static final String MKTG_MAT_REQ_EMAIL_ADDR_FROM_YTC_T_PROP_KEY = "MKTG_MAT_REQ_EMAIL_ADDR_FROM_YTC_T";
	private static final String MKTG_MAT_REQ_EMAIL_ADDR_TO_YTC_T_PROP_KEY = "MKTG_MAT_REQ_EMAIL_ADDR_TO_YTC_T";

	private static PropertyFileUtils senderDetailBundle = new PropertyFileUtils(EMAIL_SENDER_DETAILS_BUNDLE);
	
	
	
	/**
	 * This Method uses Java mail Api for sending mail.
	 * @param userEmail java.lang.String
	 * @param subject java.lang.String
	 * @param emailText java.lang.String
	 * @exception java.rmi.TiredexUnhandledException The exception description.
	 * @exception com.tiredex.yoko.exceptions.TiredexAppException The exception description.
	 */
	public static void sendEmail(String userEmail, String subject, String emailText) throws TiredexUnhandledException, TiredexAppException 
	{
		sendEmail(userEmail, subject, emailText, "");
	}
	
	
	
	/**
	 * This Method uses Java mail Api for sending mail.
	 * @param userEmail java.lang.String
	 * @param subject java.lang.String
	 * @param emailText java.lang.String
	 * @param senderType java.lang.String Should be P or T or an Internet email address
	 * @exception java.rmi.TiredexUnhandledException The exception description.
	 * @exception com.tiredex.yoko.exceptions.TiredexAppException The exception description.
	 */
	public static void sendEmail(String userEmail, String subject, String emailText, String senderType) throws TiredexUnhandledException, TiredexAppException 
	{
		final String METHOD_NAME = "sendEmail(4 args)";

	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        userEmail = [" + userEmail + ']');
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        subject = [" + subject + ']');
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        emailText = [" + emailText + ']');
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        senderType = [" + senderType + ']');

		String fromAddress = "";
		String ipAddress = "";
		try
		{
			String param = null;
			try {
//				PropertyResourceBundle senderDetailBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(EMAIL_SENDER_DETAILS_BUNDLE);

				if (senderType == null) {
					senderType = "";
				}
				
				senderType = senderType.trim();
				if (senderType.length() <= 1) {
					param = "MAILFROM" + senderType.toUpperCase();
//				    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving property [" + param + ']');
//					fromAddress = senderDetailBundle.getString(param);
//				    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, param + " = [" + fromAddress + ']');
					fromAddress = senderDetailBundle.getProperty(param);
				}
				else {
					fromAddress = senderType;
				    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Using [" + fromAddress + "] as 'from' email address.");
				}

				param = "IPADDRESS";
//			    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving property [" + param + ']');
//				ipAddress = senderDetailBundle.getString(param);
//			    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, param + " = [" + ipAddress +']');
			    ipAddress = senderDetailBundle.getProperty(param);
			}
			catch (Exception e)
			{
				String msg = "Error in reading the " + param + " parameter from the " + EMAIL_SENDER_DETAILS_BUNDLE + " property file : " + e;
				TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
				throw e;
			}
			Properties props = new Properties();
			props.put("mail.smtp.host", ipAddress);

		    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "creating mail session on host [" + ipAddress +']');
			Session session = Session.getDefaultInstance(props, null);

		    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "building mail message...");
			Message msg = new MimeMessage(session);
			Address toAddrs[] = new InternetAddress[1];
			toAddrs[0] = new InternetAddress(userEmail);
			Address fromAddr = new InternetAddress(fromAddress);
			msg.setFrom(fromAddr);
			msg.setRecipients(RecipientType.TO, toAddrs);
			msg.setSubject(subject);
			msg.setContent(emailText, "text/plain");
			msg.setSentDate(new Date());

		    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "sending mail message...");
			Transport.send(msg);
		    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "message sent");
		}
		catch (Exception e)
		{
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "Exception caught: ", e);
			throw new TiredexUnhandledException(e.getMessage());
		}
//		catch (MessagingException e)
//		{
//			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "Exception caught: " + e);
//			throw new TiredexUnhandledException(e.getMessage());
//		}

	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");

	}
	
	
	
	/**
	 * This Method uses Java mail Api for sending mail.
	 * @param userEmail java.lang.String
	 * @param subject java.lang.String
	 * @param emailText java.lang.String
	 * @param senderType java.lang.String Should be P or T or an Internet email address
	 * @param startDate java.util.Date
	 * @param endDate java.util.Date
	 * @exception java.rmi.TiredexUnhandledException The exception description.
	 * @exception com.tiredex.yoko.exceptions.TiredexAppException The exception description.
	 */
	public static void sendEmailMeeting(String userEmail, String subject, String emailText, String senderType, Date startDate, Date endDate) throws TiredexUnhandledException, TiredexAppException 
	{
		final String METHOD_NAME = "sendEmailMeeting(6 args)";

	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    arguments:");
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        userEmail = [" + userEmail + ']');
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        subject = [" + subject + ']');
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        emailText = [" + emailText + ']');
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "        senderType = [" + senderType + ']');

		String fromAddress = "";
		String ipAddress = "";
		try
		{
			String param = null;
			try {
//				PropertyResourceBundle senderDetailBundle = (PropertyResourceBundle)PropertyResourceBundle.getBundle(EMAIL_SENDER_DETAILS_BUNDLE);

				if (senderType == null) {
					senderType = "";
				}
				
				senderType = senderType.trim();
				if (senderType.length() <= 1) {
					param = "MAILFROM" + senderType.toUpperCase();
//				    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving property [" + param + ']');
//					fromAddress = senderDetailBundle.getString(param);
//				    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, param + " = [" + fromAddress + ']');
					fromAddress = senderDetailBundle.getProperty(param);
				}
				else {
					fromAddress = senderType;
				    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Using [" + fromAddress + "] as 'from' email address.");
				}

				param = "IPADDRESS";
//			    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "retrieving property [" + param + ']');
//				ipAddress = senderDetailBundle.getString(param);
//			    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, param + " = [" + ipAddress +']');
			    ipAddress = senderDetailBundle.getProperty(param);
			}
			catch (Exception e)
			{
				String msg = "Error in reading the " + param + " parameter from the " + EMAIL_SENDER_DETAILS_BUNDLE + " property file : " + e;
				TiredexLogger.logError(CLASS_NAME, METHOD_NAME, msg);
				throw e;
			}
			Properties props = new Properties();
			props.put("mail.smtp.host", ipAddress);

		    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "creating mail session on host [" + ipAddress +']');
			Session session = Session.getDefaultInstance(props, null);

		    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "building mail message...");
		    MimeMessage msg = new MimeMessage(session);
			Address toAddrs[] = new InternetAddress[1];
			toAddrs[0] = new InternetAddress(userEmail);
			Address fromAddr = new InternetAddress(fromAddress);
			msg.setFrom(fromAddr);
			msg.setRecipients(RecipientType.TO, toAddrs);
			msg.setSubject(subject);
			msg.setContent(emailText, "text/html");
			msg.setSentDate(new Date());
			msg.addHeaderLine("method=REQUEST");
			msg.addHeaderLine("charset=UTF-8");
			msg.addHeaderLine("component=VEVENT");

			
            StringBuffer sb = new StringBuffer();
            
            
             //SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			 //Date startDate=format.parse("04/17/2018 10:00");
			 //Date endDate=format.parse("04/17/2018 10:20");

			
			 SimpleDateFormat iCalendarDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
			 

            StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n" +
                    "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" +
                    "VERSION:2.0\n" +
                    "METHOD:REQUEST\n" +
                    "BEGIN:VEVENT\n" +
                    //"ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:"+userEmail+"\n" +
                    //"ORGANIZER:MAILTO:"+fromAddress+"\n" +
                    "DTSTART:"+iCalendarDateFormat.format(startDate)+"\n" +
                    "DTEND:"+iCalendarDateFormat.format(endDate)+"\n" +
                    "LOCATION:HR Conference Room\n" +
                    "TRANSP:OPAQUE\n" +
                    "SEQUENCE:0\n" +
                    "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n" +
                    " 000004377FE5C37984842BF9440448399EB02\n" +
                    "DTSTAMP:"+iCalendarDateFormat.format(new Date())+"\n" +
                    "CATEGORIES:Meeting\n" +
                    "DESCRIPTION:"+emailText+"\n\n" +
                    "SUMMARY:Meeting request\n" +
                    "PRIORITY:5\n" +
                    "CLASS:PUBLIC\n" +
                    "BEGIN:VALARM\n" +
                    "TRIGGER:-PT10M\n" +
                    "ACTION:DISPLAY\n" +
                    "DESCRIPTION:Reminder\n" +
                    "END:VALARM\n" +
                    "END:VEVENT\n" +
                    "END:VCALENDAR");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            messageBodyPart.setHeader("Content-ID", "calendar_message");
            messageBodyPart.setDataHandler(new DataHandler(
                    new ByteArrayDataSource(buffer.toString(), "text/calendar")));// very important
            Multipart multipart = new MimeMultipart();

            // Add part one
            multipart.addBodyPart(messageBodyPart);

            // Put parts in message
            msg.setContent(multipart);					
			
			
		    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "sending mail message...");
			Transport.send(msg);
		    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "message sent");
		}
		catch (Exception e)
		{
			TiredexLogger.logException(CLASS_NAME, METHOD_NAME, "Exception caught: ", e);
			throw new TiredexUnhandledException(e.getMessage());
		}
//		catch (MessagingException e)
//		{
//			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "Exception caught: " + e);
//			throw new TiredexUnhandledException(e.getMessage());
//		}

	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");

	}

	/**
	 * Gets the Consumer Marketing Materials Request email address which will appear in the FROM section of the email
	 * @return returns a String
	 */
	public static String getMktgMatReqEmailAddrFromYtcCons() throws TiredexUnhandledException {
		final String METHOD_NAME = "getMktgMatReqEmailAddrFromYtcCons";

	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");

		String addr = null;
		
		try {
//			addr = getEmailPropValue("MKTG_MAT_REQ_EMAIL_ADDR_FROM_YTC_P");
			addr = senderDetailBundle.getProperty(MKTG_MAT_REQ_EMAIL_ADDR_FROM_YTC_P_PROP_KEY);
			
		}
		catch (Exception e) 
		{
			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "Exception caught: " + e + "; raising TiredexUnhandledException");
			throw new TiredexUnhandledException(e.toString());
		}

	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning address String = [" + addr + ']');
		return addr;
	}

	
	
	/**
	 * Gets the Consumer Marketing Materials Request email address which will receive the order
	 * @return returns a String
	 */
	public static String getMktgMatReqEmailAddrToYtcCons() throws TiredexUnhandledException {
		final String METHOD_NAME = "getMktgMatReqEmailAddrToYtcCons";

	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");

		String addr = null;
		
		try {
//			addr = getEmailPropValue("MKTG_MAT_REQ_EMAIL_ADDR_TO_YTC_P");
			addr = senderDetailBundle.getProperty(MKTG_MAT_REQ_EMAIL_ADDR_TO_YTC_P_PROP_KEY);
		}
		catch (Exception e) 
		{
			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "Exception caught: " + e + "; raising TiredexUnhandledException");
			throw new TiredexUnhandledException(e.toString());
		}

	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning address String = [" + addr + ']');
		return addr;
	}



	/**
	 * Gets the Commercial Marketing Materials Request email address which will appear in the FROM section of the email
	 * @return Returns a String
	 */
	public static String getMktgMatReqEmailAddrFromYtcComm() throws TiredexUnhandledException {
		final String METHOD_NAME = "getMktgMatReqEmailAddrFromYtcComm";

	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");

		String addr = null;
		
		try {
//			addr = getEmailPropValue("MKTG_MAT_REQ_EMAIL_ADDR_FROM_YTC_T");
			addr = senderDetailBundle.getProperty(MKTG_MAT_REQ_EMAIL_ADDR_FROM_YTC_T_PROP_KEY);
		}
		catch (Exception e) 
		{
			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "Exception caught: " + e + "; raising TiredexUnhandledException");
			throw new TiredexUnhandledException(e.toString());
		}

	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning address String = [" + addr + ']');
		return addr;
	}

	/**
	 * Gets the Commercial Marketing Materials Request email address which will receive the order
	 * @return returns a String
	 */
	public static String getMktgMatReqEmailAddrToYtcComm() throws TiredexUnhandledException {
		final String METHOD_NAME = "getMktgMatReqEmailAddrToYtcComm";

	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "Start method");

		String addr = null;
		
		try {
//			addr = getEmailPropValue("MKTG_MAT_REQ_EMAIL_ADDR_TO_YTC_T");
			addr = senderDetailBundle.getProperty(MKTG_MAT_REQ_EMAIL_ADDR_TO_YTC_T_PROP_KEY);
		}
		catch (Exception e) 
		{
			TiredexLogger.logError(CLASS_NAME, METHOD_NAME, "Exception caught: " + e + "; raising TiredexUnhandledException");
			throw new TiredexUnhandledException(e.toString());
		}

	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "End method");
	    TiredexLogger.logMessage(CLASS_NAME, METHOD_NAME, "    returning address String = [" + addr + ']');
		return addr;
	}
}
