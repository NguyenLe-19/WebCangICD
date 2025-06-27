package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.RecruitmentFormWrapper;
import com.example.demo.model.RecruitmentInfo;
import com.example.demo.services.RecruitmentService;

@Controller
@RequestMapping("/cms/recruitment")
public class CmsRecruitmentController {

    private final RecruitmentService recruitmentService;

    public CmsRecruitmentController(RecruitmentService recruitmentService) {
        this.recruitmentService = recruitmentService;
    }

    // Hiển thị danh sách và form chỉnh sửa
    @GetMapping
    public String showRecruitmentForm(Model model) {
        List<RecruitmentInfo> recruitments = recruitmentService.getRecruitments();
        RecruitmentFormWrapper wrapper = new RecruitmentFormWrapper();
        wrapper.setRecruitments(recruitments);
        model.addAttribute("recruitmentFormWrapper", wrapper);
        return "cms/recruitment-form";
    }


    // Xử lý lưu danh sách tuyển dụng gửi từ form
    @PostMapping("/save")
    public String saveRecruitment(@ModelAttribute("recruitments") RecruitmentFormWrapper wrapper, Model model) {
        List<RecruitmentInfo> recruitments = wrapper.getRecruitments();

        boolean success = recruitmentService.saveRecruitments(recruitments);
        if (success) {
            model.addAttribute("message", "Lưu thành công!");
        } else {
            model.addAttribute("message", "Lỗi khi lưu dữ liệu!");
        }
        model.addAttribute("recruitments", recruitments);
        return "cms/recruitment-form";
    }
}

