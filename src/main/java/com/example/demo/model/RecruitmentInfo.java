package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentInfo {
	private boolean active;
    private String titleKey;
    private String descriptionKey;
    private String noteKey;

    
}

