package com.example.demo.services;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service("propertiesService")
public class PropertiesService {

    // Đường dẫn file manages.properties (bạn nên chỉnh lại nếu file ở nơi khác)
    private final Path filePath;

    public PropertiesService() {
        // Ví dụ: đặt ngoài resources để runtime có thể ghi
        this.filePath = Paths.get(System.getProperty("user.dir"), "uploads", "manages.properties");
    }

    /**
     * Đọc các key tuyển dụng bắt đầu với "recruitment." từ file .properties
     */
    public Map<String, String> loadRecruitmentProperties() throws IOException {
        Properties props = new Properties();

        if (!Files.exists(filePath)) {
            // Tạo file nếu chưa tồn tại
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        }

        try (InputStream in = Files.newInputStream(filePath)) {
            props.load(new InputStreamReader(in, StandardCharsets.UTF_8));
        }

        // Lọc ra các key bắt đầu bằng "recruitment."
        return props.entrySet().stream()
                .filter(e -> e.getKey().toString().startsWith("recruitment."))
                .collect(Collectors.toMap(
                        e -> e.getKey().toString(),
                        e -> e.getValue().toString(),
                        (a, b) -> b,
                        LinkedHashMap::new // Giữ thứ tự
                ));
    }

    /**
     * Cập nhật nội dung các key tuyển dụng trong file .properties
     */
    public void updateRecruitmentProperties(Map<String, String> updatedValues) throws IOException {
        Properties props = new Properties();

        // Load tất cả properties hiện có để không ghi đè các key khác
        if (Files.exists(filePath)) {
            try (InputStream in = Files.newInputStream(filePath)) {
                props.load(new InputStreamReader(in, StandardCharsets.UTF_8));
            }
        }

        // Ghi đè giá trị mới cho các key recruitment.*
        for (Map.Entry<String, String> entry : updatedValues.entrySet()) {
            props.setProperty(entry.getKey(), entry.getValue());
        }

        // Ghi lại toàn bộ properties ra file
        try (OutputStream out = Files.newOutputStream(filePath)) {
            props.store(new OutputStreamWriter(out, StandardCharsets.UTF_8), "Cập nhật bởi CMS");
        }
    }

    /**
     * Trả về đường dẫn file để tiện kiểm tra/debug
     */
    public String getFilePath() {
        return filePath.toAbsolutePath().toString();
    }
}
