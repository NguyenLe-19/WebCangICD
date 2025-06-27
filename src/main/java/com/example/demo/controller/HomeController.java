package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Award;
import com.example.demo.model.LegalDocument;

@Controller
public class HomeController {
	@RequestMapping("Home")
	public String getHome(Model model) {
		model.addAttribute("pageTitleKey", "page.title.home");
		model.addAttribute("currentPage", "home");

		List<Award> awards = List.of(new Award("award.1.title", "Tháng 11, 2022", "award.1.description"),
				new Award("award.2.title", "Tháng 12, 2023", "award.2.description"),
				new Award("award.3.title", "Tháng 5, 2021", "award.3.description"));
		model.addAttribute("awards", awards);

		return "index";
	}

	@RequestMapping("legal-documents")
	public String getLegalDoc(Model model) {
		model.addAttribute("currentPage", "legal-document");
		List<LegalDocument> legalDocs = List.of(
				new LegalDocument("39/2018/TT-BTC", "20-04-2018", "legal.mof", "legal.doc1.summary"),
				new LegalDocument("68/2016/NĐ-CP", "01-07-2016", "legal.gov", "legal.doc2.summary"));
		model.addAttribute("legalDocs", legalDocs);
		return "legal-documents";
	}

	@RequestMapping("About")
	public String getAbout(Model model) {

		model.addAttribute("currentPage", "about");
		model.addAttribute("pageTitleKey", "page.title.about");
		return "about";
	}

	@RequestMapping("Service")
	public String getService(Model model) {
		model.addAttribute("currentPage", "service");
		model.addAttribute("pageTitleKey", "page.title.service");
		return "service";
	}

	@RequestMapping("/awards")
	public String getAwards(Model model) {
		List<Award> awards = List.of(new Award("award.1.title", "Tháng 11, 2022", "award.1.description"),
				new Award("award.2.title", "Tháng 12, 2023", "award.2.description"),
				new Award("award.3.title", "Tháng 5, 2021", "award.3.description"));
		model.addAttribute("awards", awards);
		model.addAttribute("currentPage", "awards");
		model.addAttribute("pageTitleKey", "page.title.awards");
		return "awards"; // awards.html
	}

	@RequestMapping("contact")
	public String getContact(Model model) {
		model.addAttribute("currentPage", "contact");
	    model.addAttribute("pageTitleKey", "page.title.contact");
		return "contact";
	}

}
