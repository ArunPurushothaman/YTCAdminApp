/**
 * 
 */
package com.ytcadmin.service.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * @author Cognizant
 *
 */
public class ExcelGenerator {

	private static final String COMMA_DELIMITER = ",";
	
	private static final String NEW_LINE_SEPARATOR = "\n";
/*	
	public static void main(String args[]){
		System.out.println("test");
		new ExcelGenerator().generateExcel(new BaseDao(), "");
	}*/
	/**
	 * @param args
	 * @return 
	 */
	
	
	
	public byte[] writeCsvFile(String fileName, List<Object[]> dataList, String[] headerColumns) {

		StringBuilder fileWriter = new StringBuilder();
		try {
			for (int i = 0; i < headerColumns.length; i++) {
				fileWriter.append(headerColumns[i].toString());
				fileWriter.append(COMMA_DELIMITER);
			}
			fileWriter.append(NEW_LINE_SEPARATOR);
			for (Iterator<Object[]> iterator = dataList.iterator(); iterator.hasNext();) {
				Object[] type = (Object[]) iterator.next();
				for (int i = 0; i < type.length; i++) {
					fileWriter.append(null != type[i] ? type[i].toString() : "");
					fileWriter.append(COMMA_DELIMITER);
				}
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			// Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);
		} catch (Exception e) {
			throw e;
		}
		return fileWriter.toString().getBytes();
	}

	public byte[] generateExcelSAP(String fileName, String[] headerColumns, List<Object[]> returnedObj,
			Map<String, List<Integer>> formatDetails) {
		int rownum = 0;
		byte[] excel = null;

		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(fileName);
		XSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		XSSFDataFormat df = workbook.createDataFormat();
		XSSFCellStyle currency = workbook.createCellStyle();
		XSSFCellStyle zero_currency = workbook.createCellStyle();
		XSSFCellStyle right_align_format = workbook.createCellStyle();
		currency.setDataFormat(df.getFormat("#,##0.00"));
		currency.setAlignment(HorizontalAlignment.RIGHT);
		zero_currency.setDataFormat(df.getFormat("$0"));
		zero_currency.setAlignment(HorizontalAlignment.RIGHT);
		right_align_format.setAlignment(HorizontalAlignment.RIGHT);
		List<Integer> amountList = formatDetails.get("AMOUNT");
		List<Integer> rightAlignList = formatDetails.get("RIGHT");
		
		Row titleRow = sheet.createRow(rownum++);
		for (int i = 0; i < headerColumns.length; i++) {
			Cell cell = titleRow.createCell(i);
			cell.setCellValue(headerColumns[i]);
			cell.setCellStyle(headerStyle);
		}
		
		if (!returnedObj.isEmpty()) {

			
			for (Iterator<Object[]> iterator = returnedObj.iterator(); iterator.hasNext();) {
				Object[] type = (Object[]) iterator.next();
				Row row = sheet.createRow(rownum++);
				for (int i = 0; i < type.length; i++) {
					Object object = type[i];
					Cell cell = row.createCell(i);
					if (null != object) {
						if (amountList.contains(i)) {
							cell.setCellStyle(currency);
							if (Double.valueOf(object.toString()) == 0.00) {
								cell.setCellStyle(zero_currency);
							}
							cell.setCellValue(Double.valueOf(object.toString()));
						} else if (rightAlignList.contains(i)) {
							cell.setCellStyle(right_align_format);
							cell.setCellValue((object.toString()));
						} else {
							cell.setCellValue((object.toString()));
						}
					}
				}
			}
		}

		try {
			autoSizeColumns(workbook);
			workbook.write(byteArrayOut);
			excel = byteArrayOut.toByteArray();
			byteArrayOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return excel;
	}
	
	public byte[] generateExcelList(String fileName, List<Object> dataList, String[] headerColumns) {

		int rownum = 0; 
		byte[] excel=null;
		ArrayList<Integer> dollar=new ArrayList<Integer>();
		ArrayList<Integer> unit=new ArrayList<Integer>();
		int amtIndex=-1;
		
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(fileName);
		XSSFCellStyle headerStyle=workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		XSSFDataFormat df = workbook.createDataFormat();
	    XSSFCellStyle currency = workbook.createCellStyle();
	    XSSFCellStyle zero_currency = workbook.createCellStyle();
	    XSSFCellStyle percentage = workbook.createCellStyle();
	    XSSFCellStyle units = workbook.createCellStyle();
	    currency.setDataFormat(df.getFormat("$#,##0.00"));
	    zero_currency.setDataFormat(df.getFormat("$0"));
	    percentage.setDataFormat(df.getFormat("##0.00%"));
	    units.setDataFormat(df.getFormat("#,##0"));

	    Row titleRow=sheet.createRow(rownum++);
		for (int i = 0; i <headerColumns.length; i++) {
			Cell cell=titleRow.createCell(i);
			if(headerColumns[i].endsWith("1")){
				unit.add(i);
				headerColumns[i]=headerColumns[i].substring(0, headerColumns[i].length()-1);
			}else if(headerColumns[i].endsWith("2")){
				dollar.add(i);
				headerColumns[i]=headerColumns[i].substring(0, headerColumns[i].length()-1);
			}
			else if(headerColumns[i].endsWith("3")){
				headerColumns[i]=headerColumns[i].substring(0, headerColumns[i].length()-1);
				amtIndex=i;
			}
			cell.setCellValue(headerColumns[i]);
			cell.setCellStyle(headerStyle);
		}
	    
        if(!dataList.isEmpty()){     

    		
	        for (Iterator<Object> iterator = dataList.iterator(); iterator.hasNext();) {
	        	Object[] type = (Object[]) iterator.next();
	        	Row row = sheet.createRow(rownum++);
	        	for (int i = 0; i < type.length; i++) {
					Object object = type[i];
					Cell cell=row.createCell(i);
					if(null!=object){
						if(unit.contains(i)){
							cell.setCellStyle(units);
							cell.setCellValue(Double.valueOf(object.toString()));
						}else if(dollar.contains(i)){
							cell.setCellStyle(currency);
							if(Double.valueOf(object.toString())==0.00){
								cell.setCellStyle(zero_currency);
							}
							cell.setCellValue(Double.valueOf(object.toString()));
						}else{							
							
							if(amtIndex==i){
								if("$".equalsIgnoreCase(type[i+1].toString())){
									cell.setCellStyle(currency);
									if(Double.valueOf(object.toString())==0.00){
										cell.setCellStyle(zero_currency);
									}
									cell.setCellValue(Double.valueOf(object.toString()));
								}else{
									cell.setCellStyle(percentage);
									cell.setCellValue(Double.valueOf(object.toString())/100);
								}
							}else{
								cell.setCellValue((object.toString()));
							}
						}
					}
					
		        	//cell.setCellValue(cellValue);
				}
	        	
	        }
      
        
        }
        
        
		
		try {
			autoSizeColumns(workbook);
			/*FileOutputStream out = 
					new FileOutputStream(new File("C:/Excel/"+fileName+".xlsx"));
			workbook.write(out);	*/	
			
			workbook.write(byteArrayOut);
			excel=byteArrayOut.toByteArray();
			 byteArrayOut.close();
			System.out.println("Excel written successfully..");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return excel;

	}
		
	public void autoSizeColumns(XSSFWorkbook workbook) {
	    int numberOfSheets = workbook.getNumberOfSheets();
	    for (int i = 0; i < numberOfSheets; i++) {
	    	XSSFSheet sheet = workbook.getSheetAt(i);
	        if (sheet.getPhysicalNumberOfRows() > 0) {
	            Row row = sheet.getRow(0);
	            Iterator<Cell> cellIterator = row.cellIterator();
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                int columnIndex = cell.getColumnIndex();
	                sheet.autoSizeColumn(columnIndex);
	            }
	        }
	    }
	}
	
	public byte[] generateExcel(String fileName, String[] headerColumns, List<Object[]> returnedObj) {
		int rownum = 0; 
		byte[] excel=null;

		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(fileName);
		XSSFCellStyle headerStyle=workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		XSSFDataFormat df = workbook.createDataFormat();
	    XSSFCellStyle currency = workbook.createCellStyle();
	    XSSFCellStyle amount = workbook.createCellStyle();
	    currency.setDataFormat(df.getFormat("_($* #,##0.00_);_($* (#,##0.00);_($* "+"-"+"??_);_(@_)"));
	    amount.setDataFormat(df.getFormat("#,##0.00"));
		XSSFCellStyle cytdStyle=workbook.createCellStyle();
		cytdStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		cytdStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cytdStyle.setDataFormat(df.getFormat("_($* #,##0.00_);_($* (#,##0.00);_($* "+"-"+"??_);_(@_)"));
		
		Row titleRow=sheet.createRow(rownum++);
		for (int i = 0; i < headerColumns.length; i++) {
			Cell cell=titleRow.createCell(i);	
			cell.setCellValue(headerColumns[i]);
			cell.setCellStyle(headerStyle);
			
		}
		
        if(!returnedObj.isEmpty()){     

    		
	        for (Iterator<Object[]> iterator = returnedObj.iterator(); iterator.hasNext();) {
	        	Object[] type = (Object[]) iterator.next();
	        	Row row = sheet.createRow(rownum++);
	        	for (int i = 0; i < type.length; i++) {
					Object object = type[i];
					Cell cell=row.createCell(i);
					if (null != object) {
						 cell.setCellValue((object.toString()));						
					}					
				}
	        }
        }
        
		try {
			autoSizeColumns(workbook);
			/*FileOutputStream out = 
					new FileOutputStream(new File("C:/Excel/"+fileName+".xlsx"));
			workbook.write(out);	*/	
			
			workbook.write(byteArrayOut);
			excel=byteArrayOut.toByteArray();
			 byteArrayOut.close();
			System.out.println("Excel written successfully..");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return excel;
	}
}
