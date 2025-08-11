package com.example.insight_flow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.insight_flow.Helper.ExcelHelper;

@RestController
@RequestMapping("/api/excel")
public class InputController {
	
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
		ExcelHelper.isExcelFile(file);
		if(ExcelHelper.isExcelFile(file) == true) {
			return ResponseEntity.ok().body("Success");
		}
		return ResponseEntity.badRequest().body("not xlsx file");
	}
	
}
