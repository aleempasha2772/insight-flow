package com.example.insight_flow.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.insight_flow.Model.FileDetails;
import com.example.insight_flow.Model.FileType;
import com.example.insight_flow.repository.FileStorageTable;

@Service
public class fileMetaDataService {
	
	@Autowired
	private FileStorageTable fileStorageTable;
	private final Tika tika = new Tika();
	
	/**
     * STEP 2: Extract file type from filename
     */

	private String extractFileType(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		
		if(fileName == null) {
            throw new IllegalArgumentException("Cannot determine file type");
		}
		int lastDotIndex = fileName.lastIndexOf('.');
		if(lastDotIndex == -1) {
			throw new IllegalArgumentException("File must have an extension");
		}
		
		String extension = fileName.substring(lastDotIndex+1).toLowerCase();
		try {
			FileType.fromExtension(extension);
			return extension;
		}catch(IllegalArgumentException E) {
            throw new IllegalArgumentException("Unsupported file type: " + extension + ". Supported types: CSV, XLSX, XLS");

		}
	}
	
	/**
     * STEP 3: Detect MIME type using Apache Tika
     */
	private String detectMimeType(MultipartFile file) {
		try {
			String mimeType  = tika.detect(file.getInputStream());
			return mimeType;
		}catch(IOException e) {
			return "application/octet-stream";
		}
	}
	
	
	/**
     * STEP 1: Validate uploaded file
     */
	private void validateFile(MultipartFile file) {
		if(file == null || file.isEmpty()) {
			throw new IllegalArgumentException("File cannot be empty");
		}
		
		if (file.getOriginalFilename() == null || file.getOriginalFilename().trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be empty");
        }
		
		long maxSize = 50*1024*1024;
		if(file.getSize()> maxSize) {
			throw new IllegalArgumentException("File size cannot exceed 50MB");
        }
        
	}
	
	
	/**
     * STEP 4: Insert file details into files_table
     */
	private FileDetails insertFileDetails(MultipartFile file, String fileExtension, String mimeType) {
		String originalFileName = file.getOriginalFilename();
		String fileName = generateUniqueFileName(originalFileName);
		List<String> sheetNames = getAllSheetNames(file);
		FileDetails fileDetails = new FileDetails();
		fileDetails.setFileName(fileName);
		fileDetails.setSheetNames(sheetNames);
		fileDetails.setOriginalFileName(originalFileName);
		fileDetails.setFileType(fileExtension.toUpperCase());
		fileDetails.setMimeType(mimeType);
		fileDetails.setFileSize(file.getSize());
		fileDetails.setDateAndTimeUploaded(LocalDateTime.now());
		fileDetails.setStatus("UPLOADED");
		
		
		FileDetails savedFile = fileStorageTable.save(fileDetails);
		
		return savedFile;
		
		
	}
	
	private String generateUniqueFileName(String originalFileName) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        int dotIndex = originalFileName.lastIndexOf('.');
        
        if (dotIndex > 0) {
            String name = originalFileName.substring(0, dotIndex);
            String extension = originalFileName.substring(dotIndex);
            return name + "_" + timestamp + extension;
        }
        
        return originalFileName + "_" + timestamp;
    }
	
	
	private List<String> getAllSheetNames(MultipartFile file){
		List<String> sheetNamesList = new ArrayList<>();
		String filename = file.getOriginalFilename();
		if(filename == null) {
			throw new IllegalArgumentException("File name is null");
		}
		try(InputStream inputStrem = file.getInputStream();
				Workbook workbook = filename.toLowerCase().endsWith(".xlsx")
				? new XSSFWorkbook(inputStrem): new HSSFWorkbook(inputStrem)){
			int numberOfSheets = workbook.getNumberOfSheets();
			for(int i = 0;i<numberOfSheets;i++) {
				sheetNamesList.add(workbook.getSheetName(i));
			}
			
		}catch(IOException e) {
			throw new RuntimeException("Error reading Excel file", e);
		}
		return sheetNamesList;
	}
	
	/*
	 * main services starts here
	 */
	public FileDetails uploadFile(MultipartFile file) {
		validateFile(file);
		String fileExtension = extractFileType(file);
		System.out.println("file extension : "+ fileExtension);
		String mimeType = detectMimeType(file);
		System.out.println("file mimeType : "+ mimeType);
		FileDetails fileDetails = insertFileDetails(file, fileExtension, mimeType);
		return fileDetails;
	}
	
	
	public List<FileDetails> getAllFilesMetadata(){
		List<FileDetails> fileDetailsList= fileStorageTable.findAll();
		return fileDetailsList;
	}
	
	
	
	
}
