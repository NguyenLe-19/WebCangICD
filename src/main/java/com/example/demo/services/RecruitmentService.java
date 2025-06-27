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

    private final Path jsonFilePath;

    public RecruitmentService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.jsonFilePath = Paths.get(System.getProperty("user.dir"), "uploads", "recruitment.json");
    }

    public List<RecruitmentInfo> getRecruitments() {
        try {
            File file = jsonFilePath.toFile();
            if (!file.exists()) {
                return List.of();
            }
            return objectMapper.readValue(file, new TypeReference<List<RecruitmentInfo>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean saveRecruitments(List<RecruitmentInfo> recruitments) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFilePath.toFile(), recruitments);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

