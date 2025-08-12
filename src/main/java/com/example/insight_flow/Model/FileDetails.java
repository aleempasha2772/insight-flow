package com.example.insight_flow.Model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;


@Entity
public class FileDetails {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String fileName;
	private List<String> sheetNames;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<String> getSheetNames() {
		return sheetNames;
	}
	public void setSheetNames(List<String> sheetNames) {
		this.sheetNames = sheetNames;
	}
	private String originalFileName;
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	private String fileType;
	private LocalDateTime dateAndTimeUploaded;
	private Long fileSize;
	public FileDetails(String fileName, String originalFileName, String fileType, LocalDateTime dateAndTimeUploaded,
			Long fileSize, String mimeType, String status) {
		super();
		this.fileName = fileName;
		this.originalFileName = originalFileName;
		this.fileType = fileType;
		this.dateAndTimeUploaded = dateAndTimeUploaded;
		this.fileSize = fileSize;
		this.mimeType = mimeType;
		this.status = status;
	}
	public FileDetails() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "FileDetails [fileName=" + fileName + ", originalFileName=" + originalFileName + ", fileType=" + fileType
				+ ", dateAndTimeUploaded=" + dateAndTimeUploaded + ", fileSize=" + fileSize + ", mimeType=" + mimeType
				+ ", status=" + status + "]";
	}
	private String mimeType;
	private String status = "UPLOADED";
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public LocalDateTime getDateAndTimeUploaded() {
		return dateAndTimeUploaded;
	}
	public void setDateAndTimeUploaded(LocalDateTime localDateTime) {
		this.dateAndTimeUploaded = localDateTime;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

}
