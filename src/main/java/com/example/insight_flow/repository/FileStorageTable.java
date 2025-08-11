package com.example.insight_flow.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.insight_flow.Model.FileDetails;

public interface FileStorageTable extends JpaRepository<FileDetails,Integer>{
//	// Find files by status
//    List<FileDetails> findByStatus(String status);
//    
//    // Find files by file type
//    List<FileDetails> findByFileType(String fileType);
//    
//    // Find files uploaded within date range
//    List<FileDetails> findByUploadDateBetween(LocalDateTime start, LocalDateTime end);
//    
//    // Find by original file name
//    Optional<FileDetails> findByOriginalFileName(String originalFileName);
}
