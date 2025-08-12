package com.example.insight_flow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.insight_flow.Helper.ExcelHelper;
import com.example.insight_flow.Model.FileDetails;
import com.example.insight_flow.service.fileMetaDataService;

@RestController
@RequestMapping("/api/excel")
public class InputController {
	
	
	@Autowired
	private fileMetaDataService FileMetaDataService;
	
	@PostMapping("/upload")
	public ResponseEntity<FileDetails> uploadFile(@RequestParam("file") MultipartFile file){
		FileDetails fileDetails = FileMetaDataService.uploadFile(file);
		return ResponseEntity.ok().body(fileDetails);
	}
	
	
	@GetMapping("/get")
	public ResponseEntity<List<FileDetails>> getAllFilesMetadata(){
		List<FileDetails> fileDetails =  FileMetaDataService.getAllFilesMetadata();
		return ResponseEntity.ok().body(fileDetails);
	}
	
}
