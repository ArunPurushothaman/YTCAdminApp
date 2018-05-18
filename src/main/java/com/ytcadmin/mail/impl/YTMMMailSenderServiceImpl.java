package com.ytcadmin.mail.impl;

import java.util.Map.Entry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.ytcadmin.common.model.EmailDetails;
import com.ytcadmin.constant.EmailConstant;

public class YTMMMailSenderServiceImpl{
	
	public void sendMail(EmailDetails emailDetails){

		Properties props = new Properties();
		props.put("mail.smtp.auth", "false");
		//props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", emailDetails.getHost());
		props.put("mail.smtp.port", emailDetails.getPort());
		//props.put("mail.smtp.user", emailDetails.getUserName());
		//props.put("mail.smtp.password", emailDetails.getPassword());

		//Session session = Session.getInstance(props, null);
		  /*new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });*/
		
		 Session session = Session.getInstance(props,  
				    new javax.mail.Authenticator() {  
				      protected PasswordAuthentication getPasswordAuthentication() {  
				    return new PasswordAuthentication(emailDetails.getUserName(),emailDetails.getPassword());  
				      }  
				    }); 

		try {
			
			  MimeMessage message = new MimeMessage(session);
	            message.addHeaderLine("method=REQUEST");
	            message.addHeaderLine("charset=UTF-8");
	            message.addHeaderLine("component=VEVENT");

	            message.setFrom(new InternetAddress(emailDetails.getFromAddress()));
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDetails.getToAddress().get(0)));
	            message.setSubject("Outlook Meeting Request Using JavaMail");
	            
	            
	             SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				 Date startDate=format.parse("2018/04/15 22:30");
				 Date endDate=format.parse("2018/04/15 22:40");
				
				 SimpleDateFormat iCalendarDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
				 

	            StringBuffer sb = new StringBuffer();

	            StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n" +
	                    "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" +
	                    "VERSION:2.0\n" +
	                    "METHOD:REQUEST\n" +
	                    "BEGIN:VEVENT\n" +
	                    //"ATTENDEE;ROLE=REQ-PARTICIPANT:MAILTO:ArunThomas.Purushothaman@yokohamatire.com\n" +
	                    //"ORGANIZER:MAILTO:"+emailDetails.getFromAddress()+"\n" +
	                    "DTSTART:"+iCalendarDateFormat.format(startDate)+"\n" +
	                    "DTEND:"+iCalendarDateFormat.format(endDate)+"\n" +
	                    "LOCATION:Conference room\n" +
	                    "TRANSP:OPAQUE\n" +
	                    "SEQUENCE:0\n" +
	                    "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n" +
	                    " 000004377FE5C37984842BF9440448399EB02\n" +
	                    "DTSTAMP:"+iCalendarDateFormat.format(new Date())+"\n" +
	                    "CATEGORIES:Meeting\n" +
	                    "DESCRIPTION:This the description of the meeting.\n\n" +
	                    "SUMMARY:Test meeting request\n" +
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
	            message.setContent(multipart);

	            // send message
	            Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
