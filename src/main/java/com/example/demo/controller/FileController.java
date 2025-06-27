package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.example.demo.model.UploadedFile;
import com.example.demo.services.FileScannerService;


import jakarta.servlet.http.HttpServletResponse;


@Controller
public class FileController {

    private final FileScannerService fileScannerService;

    public FileController(FileScannerService fileScannerService) {
        this.fileScannerService = fileScannerService;
    }

    @GetMapping("/files")
    public String listFiles(Model model) {
        List<UploadedFile> files = fileScannerService.scanFiles();
        model.addAttribute("files", files);
        return "download";
    }

    @GetMapping("/files/{id}/download")
    public void download(@PathVariable Long id, HttpServletResponse response) throws IOException {
        List<UploadedFile> files = fileScannerService.scanFiles();

        if (id <= 0 || id > files.size()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("File không tồn tại!");
            return;
        }

        UploadedFile file = files.get((int) (id - 1));
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







