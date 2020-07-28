package com.fg.tree.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fg.tree.constant.CommonConstant;
import com.fg.tree.model.MstBatch;
import com.fg.tree.model.ReportDetailsVO;
import com.fg.tree.repository.BatchRepository;
import com.fg.tree.repository.LoginHistoryRepository;

@Service
public class CreateReportService {


	@Autowired
	BatchRepository batchRepository;

	@Autowired
	LoginHistoryRepository loginhistortRepository;

	public void generateReport() throws FileNotFoundException, ParseException {


	//	System.out.println("------Scheduler is Running----------------");
		
		List<MstBatch > batchdates =  batchRepository.getDates();
		//System.out.println("Size of pending list::"+batchdates.size());
		
	if(batchdates.size()>0)	{
		for (MstBatch mstBatch : batchdates) {


			
			System.out.println("StartDateinScheduler:::::"+mstBatch.getStartDate());

			System.out.println("Inside pending  List");
			/*System.out.println("StartDateinScheduler:::::"+mstBatch.getStartDate());

			System.out.println("EndDateinScheduler:::::"+mstBatch.getEndDate());
			System.out.println("UserIdinScheduler:::::"+mstBatch.getUserId());*/
			List<ReportDetailsVO> reportData;
					if(mstBatch.getUserId()==0) {
						reportData = loginhistortRepository.getReportDetails(mstBatch.getStartDate(), mstBatch.getEndDate());
					}else {
						reportData = loginhistortRepository.getReportDetailswithUserId(mstBatch.getStartDate(), mstBatch.getEndDate(),mstBatch.getUserId());
					}
			
			System.out.println("reportdatainScheduler :: reportData size :::::"+reportData.size());
			System.out.println("reportdatainScheduler :: reportData :::::"+reportData);
			if(reportData.size() > 0 && reportData != null) {
				//creates workbook
				XSSFWorkbook workbook = new XSSFWorkbook();

				//Creates sheet
				XSSFSheet sheet = workbook.createSheet("Report Data");

				CreationHelper createHelper = workbook.getCreationHelper();

				//Header columns
				String[] columns = {"LoginId", "First Name", "Last Name","Email","User Type","Phone Number","Lastlogin Date","Lastlogin Time","Folder accessed","Filename","Action","systemIP"};

				//Create cell Font
				Font headerFont = workbook.createFont();
				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 12);
				headerFont.setColor(IndexedColors.BLACK.getIndex());

				//Creating cellStyle with font  
				CellStyle headerCellStyle = workbook.createCellStyle();
				headerCellStyle.setFont(headerFont);
				headerCellStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
				headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				headerCellStyle.setBorderBottom(BorderStyle.THICK);
				headerCellStyle.setBorderTop(BorderStyle.THICK);
				headerCellStyle.setBorderLeft(BorderStyle.THICK);
				headerCellStyle.setBorderRight(BorderStyle.THICK);
				headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);




				Row headerRow = sheet.createRow(0);
				for(int i = 0; i < columns.length; i++) {
					Cell cell = headerRow.createCell(i);
					cell.setCellValue(columns[i]);
					cell.setCellStyle(headerCellStyle);
				}  

				int rownum = 1;


				//creating style of date and time
				CellStyle dateCellStyle = workbook.createCellStyle();
				CellStyle timeCellStyle = workbook.createCellStyle();
				dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
				timeCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("h:mm:ss"));
				dateCellStyle .setBorderBottom(BorderStyle.THIN);
				dateCellStyle.setBorderTop(BorderStyle.THIN);
				dateCellStyle.setBorderLeft(BorderStyle.THIN);
				dateCellStyle.setBorderRight(BorderStyle.THIN);
				dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				timeCellStyle.setBorderBottom(BorderStyle.THIN);
				timeCellStyle.setBorderTop(BorderStyle.THIN);
				timeCellStyle.setBorderLeft(BorderStyle.THIN);
				timeCellStyle.setBorderRight(BorderStyle.THIN);
				timeCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);



				CellStyle eachCellStyle = workbook.createCellStyle();
				eachCellStyle.setBorderBottom(BorderStyle.THIN);
				eachCellStyle.setBorderTop(BorderStyle.THIN);
				eachCellStyle.setBorderLeft(BorderStyle.THIN);
				eachCellStyle.setBorderRight(BorderStyle.THIN);
				eachCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);



				for(ReportDetailsVO reportvalue:  reportData) {
					Row row = sheet.createRow(rownum++);
		
					Cell cell = row.createCell(0);
					cell.setCellValue(reportvalue.getUsername());
					cell.setCellStyle(eachCellStyle);

					Cell cell1 = row.createCell(1);
					cell1.setCellValue(reportvalue.getFirstname());
					cell1.setCellStyle(eachCellStyle);

					Cell cell2 = row.createCell(2);
					cell2.setCellValue(reportvalue.getLastname());
					cell2.setCellStyle(eachCellStyle);

					Cell cell3 = row.createCell(3);
					cell3.setCellValue(reportvalue.getEmailId());
					cell3.setCellStyle(eachCellStyle);

					Cell cell4 = row.createCell(4);
					cell4.setCellValue(reportvalue.getRoleName());
					cell4.setCellStyle(eachCellStyle);

					Cell cell5 = row.createCell(5);
					cell5.setCellValue(reportvalue.getContact());
					cell5.setCellStyle(eachCellStyle);

					Cell logindate = row.createCell(6);
					logindate.setCellValue(reportvalue.getLogindate());
					logindate.setCellStyle(dateCellStyle);



					Cell logintime = row.createCell(7);
					logintime.setCellValue(reportvalue.getLogintime());
					logintime.setCellStyle(timeCellStyle);

					Cell cell8 = row.createCell(8);
					cell8.setCellValue(reportvalue.getDocpath());
					cell8.setCellStyle(eachCellStyle);

					Cell cell9 = row.createCell(9);
					cell9.setCellValue(reportvalue.getFilename());
					cell9.setCellStyle(eachCellStyle);

					Cell cell10 = row.createCell(10);
					cell10.setCellValue(reportvalue.getAction());
					cell10.setCellStyle(eachCellStyle);
					
					
					Cell cell11 = row.createCell(11);
					cell11.setCellValue(reportvalue.getLoggedInUsersystemIP());
					cell11.setCellStyle(eachCellStyle);
					
				}
				for(int i = 0; i < columns.length; i++) {
					sheet.autoSizeColumn(i);
				}
				
				
				
				
				
				String filename;
				FileOutputStream fileOut;
				if(mstBatch.getUserId()==0) {
					fileOut = new FileOutputStream(CommonConstant.REPORT_PATH +"Report-Data_"+mstBatch.getStartDate()+"_"+mstBatch.getEndDate()+".xlsx");
					filename = "Report-Data_"+mstBatch.getStartDate()+"_"+mstBatch.getEndDate()+".xlsx";
					
					
				}else {
				fileOut = new FileOutputStream(CommonConstant.REPORT_PATH +"Report-Data_"+mstBatch.getStartDate()+"_"+mstBatch.getEndDate()+"_"+mstBatch.getUserId()+".xlsx");
				filename = "Report-Data_"+mstBatch.getStartDate()+"_"+mstBatch.getEndDate()+"_"+mstBatch.getUserId()+".xlsx";
				}
				
				/*File reportFile = new File(CommonConstant.REPORT_PATH +"Report-Data_"+mstBatch.getStartDate()+"_"+mstBatch.getEndDate()+".xlsx");

				FileOutputStream fileOut = new FileOutputStream(reportFile);
				*/
	
			//	System.out.println("File Path::::::------------>"+CommonConstant.REPORT_PATH +"Report-Data_"+mstBatch.getStartDate()+"_"+mstBatch.getEndDate()+".xlsx");
				String finalpath = CommonConstant.REPORT_PATH;
				
				
				

				try {
					workbook.write(fileOut);
					fileOut.close();
					workbook.close();
				} catch (IOException e) {

					e.printStackTrace();
				}


				batchRepository.updateStatusofBatch(mstBatch.getStartDate(),mstBatch.getEndDate(),finalpath,CommonConstant.BATCH_STATUS,filename);
				System.out.println("Update the status after generation of report::::::------->");

			}else {

				System.out.println("No data found between those dates.....!!!!!");	

				batchRepository.updateStatusofBatch(mstBatch.getStartDate(),mstBatch.getEndDate(),CommonConstant.BATCH_STATUS_NO_DATA,CommonConstant.BATCH_STATUS_NO_DATA,CommonConstant.BATCH_STATUS_NO_DATA);

			}

		}
	}else {
		
		//System.out.println("No pending list.....");
	}
	
		
	}


}
