package com.example.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.model.UploadedFile;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonFileStorage {
    private final Path jsonFilePath = Paths.get("uploads/files.json");
    private final ObjectMapper mapper = new ObjectMapper();

    public List<UploadedFile> readFiles() throws IOException {
        if (!Files.exists(jsonFilePath)) {
            return new ArrayList<>();
        }
        return mapper.readValue(jsonFilePath.toFile(), new TypeReference<List<UploadedFile>>() {});
    }

    public void saveFiles(List<UploadedFile> files) throws IOException {
        if (!Files.exists(jsonFilePath.getParent())) {
            Files.createDirectories(jsonFilePath.getParent());
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFilePath.toFile(), files);
    }
}
