package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.RecruitmentInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentFormWrapper {
    private List<RecruitmentInfo> recruitments;

}
