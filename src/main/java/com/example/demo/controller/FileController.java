package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
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
import com.example.demo.services.UploadedFileService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FileController {

	@Value("${file.upload-dir}")
	private String uploadDir;

	private final UploadedFileService fileRepo;

	public FileController(UploadedFileService fileRepo) {
		this.fileRepo = fileRepo;
	}

	@GetMapping("/admin/upload")
	public String showUploadForm() {
		return "upload";
	}

	@PostMapping("/admin/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
		if (file.isEmpty()) {
			model.addAttribute("message", "Chưa chọn file!");
			return "upload";
		}

		String originalFileName = file.getOriginalFilename();
		String storedFileName = UUID.randomUUID() + "_" + originalFileName;

		Path uploadPath = Paths.get(uploadDir);
		Path filePath = uploadPath.resolve(storedFileName);

		// Kiểm tra thư mục uploads tồn tại, nếu chưa có thì tạo
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		// Lưu file, nếu file đã tồn tại thì ghi đè (hoặc bạn có thể đổi tên khác)
		Files.copy(file.getInputStream(), filePath);

		UploadedFile uploaded = new UploadedFile();
		uploaded.setFileName(originalFileName);
		uploaded.setFileType(file.getContentType());
		uploaded.setUploadPath("uploads/" + storedFileName);
		uploaded.setUploadDate(new Date());

		fileRepo.save(uploaded);

		// Trả về lại form upload và thêm message thành công
		model.addAttribute("message", "Tải lên thành công: " + originalFileName);

		// Giữ form để admin upload tiếp file khác (không redirect, vẫn render trang
		// upload)
		return "redirect:/admin/upload";
	}

	@GetMapping("/files")
	public String listFiles(Model model) {
		model.addAttribute("files", fileRepo.findAll());
		return "download";
	}

	@GetMapping("/files/{id}/download")
	public void download(@PathVariable Long id, HttpServletResponse response) throws IOException {
		UploadedFile file = fileRepo.findById(id).orElseThrow();
		Path filePath = Paths.get(file.getUploadPath()); // VD: uploads/abc_xyz.pdf

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
