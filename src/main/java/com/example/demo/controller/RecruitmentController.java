package com.example.demo.controller;


import com.example.demo.model.RecruitmentInfo;
import com.example.demo.services.RecruitmentService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    public RecruitmentController(RecruitmentService recruitmentService) {
        this.recruitmentService = recruitmentService;
    }

    @GetMapping("/recruitment")
    public String recruitmentPage(Model model) {
        List<RecruitmentInfo> list = recruitmentService.getRecruitments();

        // In kiểm tra để biết có dữ liệu hay không
        System.out.println("Recruitment loaded: " + list.size());

        model.addAttribute("recruitments", list);
        return "recruitment"; // Tên template .html
    }
}






