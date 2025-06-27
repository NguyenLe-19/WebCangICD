package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.UploadedFile;
import com.example.demo.services.FileScannerService;


import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FileController {

	@Value("${file.upload-dir}")
	private String uploadDir;

	@GetMapping("/admin/upload")
	public String showUploadForm() {
		return "upload";
	}



//	@PostMapping("/admin/upload")
//	public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
//		if (file.isEmpty()) {
//			model.addAttribute("message", "Chưa chọn file!");
//			return "upload";
//		}
//
//		String originalFileName = file.getOriginalFilename();
//		String storedFileName = UUID.randomUUID() + "_" + originalFileName;
//
//		Path uploadPath = Paths.get(uploadDir);
//		if (!Files.exists(uploadPath)) {
//			Files.createDirectories(uploadPath);
//		}
//		Path filePath = uploadPath.resolve(storedFileName);
//		Files.copy(file.getInputStream(), filePath);
//
//		JsonFileStorage jsonStorage = new JsonFileStorage();
//		List<UploadedFile> files = jsonStorage.readFiles();
//
//		UploadedFile uploaded = new UploadedFile();
//		uploaded.setId(UUID.randomUUID().toString());
//		uploaded.setFileName(originalFileName);
//		uploaded.setFileType(file.getContentType());
//		uploaded.setUploadPath(filePath.toString());
//		uploaded.setUploadDate(new Date());
//
//		files.add(uploaded);
//		jsonStorage.saveFiles(files);
//
//		model.addAttribute("message", "Tải lên thành công: " + originalFileName);
//		return "redirect:/admin/upload";
//	}

//	@PostMapping("/admin/upload")
//	public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
//		if (file.isEmpty()) {
//			model.addAttribute("message", "Chưa chọn file!");
//			return "upload";
//		}
//
//		String originalFileName = file.getOriginalFilename();
//		String contentType = file.getContentType();
//
//		// Đường dẫn cố định trên máy local, file phải được admin copy vào đây thủ công
//		String fixedFolder = "D:/FTPsite/web/icd/wwwroot/download";
//		String fixedPath = fixedFolder + originalFileName;
//
//		UploadedFile uploaded = new UploadedFile();
//		uploaded.setFileName(originalFileName);
//		uploaded.setFileType(contentType);
//		uploaded.setUploadPath(fixedPath);
//		uploaded.setUploadDate(new Date());
//		uploaded.setId(UUID.randomUUID().toString());
//
//		// Lưu metadata vào file JSON
//		JsonFileStorage storage = new JsonFileStorage();
//		List<UploadedFile> files = storage.readFiles();
//		files.add(uploaded);
//		storage.saveFiles(files);
//
//		model.addAttribute("message", "Thông tin file đã lưu. Vui lòng upload file vật lý vào " + fixedFolder);
//		return "redirect:/admin/upload";
//	}

	@GetMapping("/files")
	public String listFiles(Model model) {
	    FileScannerService scanner = new FileScannerService();
	    List<UploadedFile> files = scanner.scanFiles();
	    model.addAttribute("files", files);
	    return "download";
	}


	@GetMapping("/files/{id}/download")
	public void download(@PathVariable Long id, HttpServletResponse response) throws IOException {
	    FileScannerService scanner = new FileScannerService();
	    List<UploadedFile> files = scanner.scanFiles();

	    if (id <= 0 || id > files.size()) {
	        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        response.getWriter().write("File không tồn tại!");
	        return;
	    }

	    UploadedFile file = files.get( (int) (id - 1));
	    Path filePath = Path.of(file.getUploadPath());

	    if (!Files.exists(filePath)) {
	        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        response.getWriter().write("File không tồn tại!");
	        return;
	    }

	    response.setContentType(file.getFileType());
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");
	    Files.copy(filePath, response.getOutputStream());
	    response.getOutputStream().flush();
	}



}
