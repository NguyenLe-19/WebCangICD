package com.example.demo.services;

import com.example.demo.model.RecruitmentInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service("recruitmentService")
public class RecruitmentService {

	private final ObjectMapper objectMapper;

	public RecruitmentService(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public List<RecruitmentInfo> getRecruitments() {
		try {
			Path path = Paths.get(System.getProperty("user.dir"), "uploads", "recruitment.json");
			File file = path.toFile();
			System.out.println("📄 Đường dẫn file JSON: " + file.getAbsolutePath());

			if (!file.exists()) {
				System.out.println("⚠️ File không tồn tại.");
				return List.of();
			}

			List<RecruitmentInfo> list = objectMapper.readValue(file, new TypeReference<List<RecruitmentInfo>>() {
			});
			System.out.println("✅ Đã đọc " + list.size() + " mục tuyển dụng.");
			return list;

		} catch (IOException e) {
			e.printStackTrace();
			return List.of();
		}
	}

}
