package com.example.insight_flow.Helper;

import org.springframework.web.multipart.MultipartFile;

public class ExcelHelper {
	
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	
	public static boolean isExcelFile(MultipartFile file) {
		
		String contentType = file.getContentType();
		String fileName = file.getOriginalFilename();
		
		if(TYPE.equals(contentType)) {
			System.out.println("File type is XLSX");
		}
		System.out.println("File name is : "+ fileName);
		
		return TYPE.equals(fileName);
	}

}
