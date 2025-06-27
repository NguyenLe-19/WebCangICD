package com.example.demo.model;


import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadedFile {
	private Long id;          
    private String fileName;
    private String fileType;
    private String uploadPath;// Đường dẫn cố định trên máy server
    private Date uploadDate;

}

