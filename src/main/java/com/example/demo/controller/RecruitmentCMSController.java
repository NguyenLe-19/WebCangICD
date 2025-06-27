package com.example.demo.controller;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.services.PropertiesService;

@Controller
@RequestMapping("/admin/recruitment-cms")
public class RecruitmentCMSController {

    private final PropertiesService propertiesService;

    public RecruitmentCMSController(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    @GetMapping
    public String showCMS(Model model) throws IOException {
        Map<String, String> recruitmentProps = propertiesService.loadRecruitmentProperties();
        model.addAttribute("recruitmentProps", recruitmentProps);
        return "admin/recruitment-cms";
    }

    @PostMapping
    public String updateCMS(@RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {
        try {
            // Chỉ giữ lại key bắt đầu với "recruitment."
            Map<String, String> filtered = allParams.entrySet().stream()
                .filter(e -> e.getKey().startsWith("recruitment."))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            propertiesService.updateRecruitmentProperties(filtered);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi ghi file!");
        }
        return "redirect:/admin/recruitment-cms";
    }
}

