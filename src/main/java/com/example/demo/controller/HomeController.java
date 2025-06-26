package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping("Home")
	public String getHome(Model model) {
		 model.addAttribute("currentPage", "home");
		return "index";
	}
	
	@RequestMapping("legal-documents")
	public String getLegalDoc(Model model) {
		 model.addAttribute("currentPage", "legal-document");
		return "legal-documents";
	}
	
	@RequestMapping("About")
	public String getAbout(Model model) {
		 model.addAttribute("currentPage", "about");
		return "about";
	}
	
	@RequestMapping("Service")
	public String getService(Model model) {
		 model.addAttribute("currentPage", "service");
		return "service";
	}
	
	
}
