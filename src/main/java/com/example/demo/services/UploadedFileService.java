package com.example.demo.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.UploadedFile;

public interface UploadedFileService extends JpaRepository<UploadedFile, Long>{

}
