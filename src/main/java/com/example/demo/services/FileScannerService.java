package com.example.demo.services;

import org.springframework.scheduling.annotation.Scheduled;
import com.example.demo.model.UploadedFile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileScannerService {
	
	 @Scheduled(fixedDelay = 5000) //5s
	    public void scheduledRefresh() throws IOException {
	        refreshMetadata();
	    }

    private final String folderPath = "D:/FTPsite/web/icd/wwwroot/download";
    

    @Value("${file.metadata-json-path}")
    private String metadataJsonPath;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<UploadedFile> cachedFiles = new ArrayList<>();

    @PostConstruct
    public void init() throws IOException {
        // Khi khởi động: scan folder và ghi metadata vào json
        refreshMetadata();
    }

    public synchronized List<UploadedFile> scanFiles() {
        // Trả về cached files đọc từ json
        return cachedFiles;
    }

    // Scan thư mục và cập nhật metadata file json
    public synchronized void refreshMetadata() throws IOException {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IOException("Upload folder không tồn tại hoặc không phải thư mục: " + folderPath);
        }

        File[] files = folder.listFiles();
        List<UploadedFile> fileList = new ArrayList<>();

        if (files != null) {
            long id = 1;
            for (File file : files) {
                if (file.isFile()) {
                    UploadedFile uploadedFile = new UploadedFile();
                    uploadedFile.setId(id++);
                    uploadedFile.setFileName(file.getName());
                    uploadedFile.setFileType(getMimeType(file));
                    uploadedFile.setUploadPath(file.getAbsolutePath());
                    uploadedFile.setUploadDate(new Date(file.lastModified()));
                    fileList.add(uploadedFile);
                }
            }
        }

        // Lưu vào files.json
        File metadataFile = new File(metadataJsonPath);
        metadataFile.getParentFile().mkdirs(); // tạo thư mục cha nếu chưa có
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(metadataFile, fileList);

        // Cập nhật cache
        cachedFiles = fileList;
    }

    // Đọc metadata từ file JSON (nếu cần)
    public synchronized void loadMetadataFromJson() throws IOException {
        File metadataFile = new File(metadataJsonPath);
        if (metadataFile.exists()) {
            cachedFiles = objectMapper.readValue(metadataFile, new TypeReference<List<UploadedFile>>() {});
        } else {
            cachedFiles = new ArrayList<>();
        }
    }

    private String getMimeType(File file) {
        try {
            String mimeType = Files.probeContentType(file.toPath());
            return mimeType != null ? mimeType : "application/octet-stream";
        } catch (Exception e) {
            return "application/octet-stream";
        }
    }
}

