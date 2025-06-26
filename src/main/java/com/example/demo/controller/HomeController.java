package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.LegalDocument;

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
		 List<LegalDocument> legalDocs = List.of(
		            new LegalDocument("39/2018/TT-BTC", "20-04-2018", "legal.mof", "legal.doc1.summary"),
		            new LegalDocument("68/2016/NĐ-CP", "01-07-2016", "legal.gov", "legal.doc2.summary")
		        );
		        model.addAttribute("legalDocs", legalDocs);
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
