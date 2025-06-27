package com.example.demo.utils;

import com.example.demo.model.UploadedFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileMetadataUtil {
	private static final ObjectMapper mapper = new ObjectMapper();

	public static void writeToJson(List<UploadedFile> files, String jsonPath) throws IOException {
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonPath), files);
	}

	public static List<UploadedFile> readFromJson(String jsonPath) throws IOException {
		File jsonFile = new File(jsonPath);
		if (!jsonFile.exists())
			return null;
		return mapper.readValue(jsonFile,
				mapper.getTypeFactory().constructCollectionType(List.class, UploadedFile.class));
	}
}
