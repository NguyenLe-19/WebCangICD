package com.example.demo.services;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.model.UploadedFile;

public class FileScannerService {

    private final String folderPath = "D:/FTPsite/web/icd/wwwroot/download";

    public List<UploadedFile> scanFiles() {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        List<UploadedFile> fileList = new ArrayList<>();

        if (files != null) {
            long id = 1;
            for (File file : files) {
                if (file.isFile()) {
                    UploadedFile uploadedFile = new UploadedFile();
                    uploadedFile.setId(id++);
                    uploadedFile.setFileName(file.getName());
                    uploadedFile.setFileType(getMimeType(file)); // xác định mime type
                    uploadedFile.setUploadPath(file.getAbsolutePath());
                    uploadedFile.setUploadDate(new Date(file.lastModified()));
                    fileList.add(uploadedFile);
                }
            }
        }

        return fileList;
    }

    private String getMimeType(File file) {
        try {
            return Files.probeContentType(file.toPath());
        } catch (Exception e) {
            return "application/octet-stream";
        }
    }
}

