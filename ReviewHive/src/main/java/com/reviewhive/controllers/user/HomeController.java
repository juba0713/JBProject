package com.reviewhive.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.reviewhive.model.dto.QuestionaireDto;
import com.reviewhive.model.services.QuestionaireService;

@Controller
public class HomeController {
	
	@Autowired
	private QuestionaireService questionaireService;

	@GetMapping("/")
	public String showHomePage(@ModelAttribute QuestionaireDto webDto) {
		
		QuestionaireDto outDto = questionaireService.getQuestionaireForUser();
		
		webDto.setQuestionaireUser(outDto.getQuestionaireUser());
		
		return "home";
	}
	
	@GetMapping("/questionaire")
	public String showQuestionaire(@RequestParam("id") int id, @ModelAttribute QuestionaireDto webDto) {
		
		webDto.setId(id);
		
		QuestionaireDto outDto = questionaireService.getQuestionaireById(webDto);
		
		webDto.setQuestionaire(outDto.getQuestionaire());
		
		return "questionaire";
	}
}
