/**
* 
 */
package com.ytcadmin.service.util;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ytcadmin.dal.IDataAccessLayer;
import com.ytcadmin.dal.model.DalAbsenceReasonMaster;
import com.ytcadmin.dal.model.DalDepartmentMaster;
import com.ytcadmin.dal.model.DalEmployeeAbsenceCall;
import com.ytcadmin.dal.model.DalEmployeeMaster;
import com.ytcadmin.dal.model.DalShiftMaster;
import com.ytcadmin.dal.model.DalSickReasonMaster;

public class PdfGenerator {
	
	static Logger logger = Logger.getLogger(PdfGenerator.class);

                /**
                * @param args
                * @return 
                 */

                public byte[] generatePdf(DalEmployeeAbsenceCall dalEmployeeAbsenceCall, IDataAccessLayer baseDao) {
                				logger.info("Inside generatePdf method "+dalEmployeeAbsenceCall.getEmployeeName());
                                Document document = new Document();
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                SimpleDateFormat format=new SimpleDateFormat("hh:mm a");
                                try
                                {
                                                PdfWriter writer = PdfWriter.getInstance(document, stream);
                                                document.open();
                                                Font f1 = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
                                                Font f2 = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
                                                f1.setColor(BaseColor.BLUE);
                                                f2.setColor(BaseColor.DARK_GRAY);
                                                document.add(new Paragraph("YTMM Employee Call/Report-Off Form",f1));
                                                PdfPTable custTable = new PdfPTable(3); // 3 columns.
                                                custTable.setWidthPercentage(100); //Width 100%
                                                custTable.setSpacingBefore(10f); //Space before table
                                                custTable.setSpacingAfter(10f); //Space after table
                                                //Set Column widths
                                                float[] columnWidths = {1f,1f,1f};
                                                custTable.setWidths(columnWidths);

                                                PdfPCell cell = new PdfPCell(new Paragraph("Employee Name: "+baseDao.getById(DalEmployeeMaster.class,dalEmployeeAbsenceCall.getEmployeeId()).getFullName()));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                                                cell.setMinimumHeight(30f);
                                                cell.setColspan(3);
                                                custTable.addCell(cell);

                                                cell = new PdfPCell(new Paragraph("Time of Call: "+format.format(dalEmployeeAbsenceCall.getTimeOfCall())));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                                cell.setMinimumHeight(30f);
                                                custTable.addCell(cell);

                                                cell = new PdfPCell(new Paragraph("Date of Absence: "+convertDateToString(dalEmployeeAbsenceCall.getAbsenceDate().getTime(),"MM/dd/yyyy")));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                                cell.setMinimumHeight(30f);
                                                custTable.addCell(cell);
                                                
                                                cell = new PdfPCell(new Paragraph("Number of days: "+dalEmployeeAbsenceCall.getAbsenceDays()));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                                cell.setMinimumHeight(30f);
                                                custTable.addCell(cell);
                                                
                                                cell = new PdfPCell(new Paragraph("Manager/Supervisor: "+baseDao.getById(DalEmployeeMaster.class, Integer.parseInt(dalEmployeeAbsenceCall.getManager())).getFullName()));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                                                cell.setMinimumHeight(30f);
                                                cell.setColspan(3);
                                                custTable.addCell(cell);
                                                
                                                cell = new PdfPCell(new Paragraph("Shift Assigned: "+baseDao.getById(DalShiftMaster.class, Integer.parseInt(dalEmployeeAbsenceCall.getShiftId())).getShiftName()));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                                cell.setMinimumHeight(30f);
                                                cell.setColspan(2);
                                                custTable.addCell(cell);


                                                cell = new PdfPCell(new Paragraph("Department Assigned: "+baseDao.getById(DalDepartmentMaster.class, Integer.parseInt(dalEmployeeAbsenceCall.getDepartmentId())).getDepartmentDesc()));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                                cell.setMinimumHeight(30f);
                                                cell.setColspan(2);
                                                custTable.addCell(cell);                                             
                                                                                         

                                              
                                                cell = new PdfPCell(new Paragraph("Caller's Phone Number: "+dalEmployeeAbsenceCall.getPhoneNo()));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                                                cell.setMinimumHeight(30f);
                                                cell.setColspan(3);
                                                custTable.addCell(cell);


                                                cell = new PdfPCell(new Paragraph("Reason for Absence: "+baseDao.getById(DalAbsenceReasonMaster.class, Integer.parseInt(dalEmployeeAbsenceCall.getAbsenceReason())).getReason()));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                                cell.setMinimumHeight(30f);
                                                cell.setColspan(2);
                                                custTable.addCell(cell);
                                                
                                                cell = new PdfPCell(new Paragraph("Sick Reason: "+baseDao.getById(DalSickReasonMaster.class, Integer.parseInt(dalEmployeeAbsenceCall.getSickReason())).getReason()));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                                cell.setMinimumHeight(30f);
                                                cell.setColspan(2);
                                                custTable.addCell(cell);
                                                
                                               /* cell = new PdfPCell(new Paragraph("Absent Points: "+dalEmployeeAbsenceCall.getPoint()));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                                cell.setMinimumHeight(30f);
                                                custTable.addCell(cell);*/
                                                
                                                cell = new PdfPCell(new Paragraph("Comments: "+dalEmployeeAbsenceCall.getComments()));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                                                cell.setMinimumHeight(30f);
                                                cell.setColspan(3);
                                                custTable.addCell(cell);

                                                cell = new PdfPCell(new Paragraph("Call Taken By: "+dalEmployeeAbsenceCall.getCallTakenBy()));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                                cell.setMinimumHeight(30f);
                                                cell.setColspan(2);
                                                custTable.addCell(cell);


                                               
                                                cell = new PdfPCell(new Paragraph("Extension: "+dalEmployeeAbsenceCall.getExtn()));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                                cell.setMinimumHeight(30f);
                                                custTable.addCell(cell);

                                                cell = new PdfPCell(new Paragraph("Date: "+convertDateToString(dalEmployeeAbsenceCall.getDate().getTime(),"MM/dd/yyyy")));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                                cell.setMinimumHeight(30f);
                                                cell.setColspan(2);
                                                custTable.addCell(cell);


                                                cell = new PdfPCell(new Paragraph("Time: "+format.format(dalEmployeeAbsenceCall.getTime())));
                                                cell.setBorderColor(BaseColor.BLACK);
                                                cell.setPaddingLeft(10);
                                                cell.setVerticalAlignment(Element.ALIGN_LEFT);
                                                cell.setMinimumHeight(30f);
                                                custTable.addCell(cell);
                                                

                                                document.add(custTable);
                                                document.add(new Paragraph("Note: The employee should not be asked to disclose confidential medical information(i.e.:Diagnosis or Prognosis)."
                                                		+ " HR will follow up to determine whether absence is an FMLA qualifying event",f1));
                                                document.close();
                                                writer.close();       
                                } catch (DocumentException e)
                                {
                                	logger.error("Error",e);
                                }
                                logger.info("Pdf generated");
                                return stream.toByteArray();
                }
                
                
                public static String convertDateToString(Date inputDate, String format){
            		String convertedDateString = null;
            		
            		if(inputDate != null && format != null){
            			SimpleDateFormat sdf = new SimpleDateFormat(format);
            			//convertedDateString = sdf.format(new Date());
            			convertedDateString = sdf.format(inputDate);
            		}
            		return convertedDateString;
            	}
}
