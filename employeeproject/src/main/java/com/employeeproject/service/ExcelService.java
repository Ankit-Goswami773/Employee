package com.employeeproject.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.employeeproject.entity.EmployeeDetails;
import com.employeeproject.entity.OneMonthAttendance;
import com.employeeproject.repository.EmployeeDetailsRepository;

@Service
public class ExcelService {

	@Autowired
	private EmployeeDetailsRepository detailsRepository;
	
	public void exportToExcel(List<OneMonthAttendance> attendance, long empId) throws IOException {
		ByteArrayOutputStream outputStream = null;
		ByteArrayResource resource = null;
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Attendence");
			Row row = sheet.createRow(0);

			// Define header cell style
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			Cell cell = row.createCell(0);
			cell.setCellValue("Attendence status");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(1);
			cell.setCellValue("Date");
			cell.setCellStyle(headerCellStyle);

			cell = row.createCell(2);
			cell.setCellValue("Day");
			cell.setCellStyle(headerCellStyle);

			for (int i = 0; i < attendance.size(); i++) {
				Row dataRow = sheet.createRow(i + 1);

				dataRow.createCell(0).setCellValue(attendance.get(i).getAttendanceStatus());

				

				dataRow.createCell(1).setCellValue(attendance.get(i).getDate()+"");
				dataRow.createCell(2).setCellValue(attendance.get(i).getDay());

				sheet.autoSizeColumn(0);
				sheet.autoSizeColumn(1);
				sheet.autoSizeColumn(2);
				outputStream = new ByteArrayOutputStream();
				workbook.write(outputStream);
				byte[] byteArray = outputStream.toByteArray();

				resource = new ByteArrayResource(byteArray);

			}
			
         Optional<EmployeeDetails> optional = detailsRepository.findById(empId);
                  if(optional.isPresent()) 
                  {
                	 EmployeeDetails employeeDetails = optional.get(); 
        	  sendEmail(employeeDetails.getEmailId(), resource);
          }
                  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null)
				outputStream.close();
		}

	}

	private void sendEmail(String emailId, ByteArrayResource resource) throws MessagingException {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("appemail78@gmail.com");
		mailSender.setPassword("tsknpquxfnxxnzlv");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("appemail78@gmail.com");
		helper.setTo(emailId);
		helper.setText("Attendence sheet");
		helper.setSubject("Attendence Info");
		helper.addAttachment("Invoice.xlsx", resource);
		mailSender.send(message);

	}

}
