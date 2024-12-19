package com.reviewhive.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reviewhive.common.constant.CommonConstant;
import com.reviewhive.common.constant.MessageConstant;
import com.reviewhive.model.dto.CategoryDto;
import com.reviewhive.model.dto.QuestionaireDto;
import com.reviewhive.model.services.CategoryService;
import com.reviewhive.model.services.QuestionaireService;

import jakarta.validation.Valid;

/**
 * @author Julius P. Basas
 * @added 12/19/2024
 */
@Controller
@RequestMapping("/admin")
public class AdminQuestionaireController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private QuestionaireService questionaireService;

	/**
	 * Show Category List Screen
	 * @return String
	 */
	@GetMapping("/questionaire-list")
	public String showQuestionairesScreen(
			Model model,
			@ModelAttribute QuestionaireDto webDto) {
		
		return "admin/questionaire-list";
	}
	
	/**
	 * Show Category Add Screen
	 * @return String
	 */
	@GetMapping("/questionaire-add")
	public String showQuestionaireAddScreen(@ModelAttribute QuestionaireDto webDto) {
		
		webDto.setCategories(categoryService.getAllCategory().getCategoriesSelection());
		
		return "admin/questionaire-add";
	}
	

	@PostMapping("/questionaire-add")
	public String postQuestionaireAddScreen(
			Model model,
			@Valid @ModelAttribute QuestionaireDto webDto,
			BindingResult result,
			RedirectAttributes ra) {
		
		System.out.println(webDto.getQuestionaireCategoryId());
		System.out.println(webDto.getQuestionaireAbbreviation());
		System.out.println(webDto.getQuestionaireName());
		System.out.println(webDto.getQuestionaireStatus());
		System.out.println(webDto.getQuestionaireDescription());
		System.out.println(webDto.getQuestionaireColor());
		
		if(result.hasErrors()) {
			
			return "admin/questionaire-add";
		}
		
		questionaireService.saveQuestionaire(webDto);
		
		String successMessage = webDto.getQuestionaireName() + MessageConstant.QUESTIONAIRE_ADDED;
		
		ra.addFlashAttribute("message", successMessage);
		
		return "redirect:/admin/questionaire-list";
	}
	
	/**
	 * Show Questionaire Add Screen
	 * @return String
	 */
	@GetMapping("/questionaire-edit")
	public String showQuestionaireEditScreen(			
			@ModelAttribute QuestionaireDto webDto) {
		
		webDto.setCategories(categoryService.getAllCategory().getCategoriesSelection());
		webDto.setQuestionaire(questionaireService.getQuestionaireById(webDto).getQuestionaire());
		
		return "admin/questionaire-edit";
	}
	
	/**
	 * Post Questionaire Edit Screen
	 * @param webDto
	 * @param ra
	 * @param result
	 * @return String
	 */
	@PostMapping("/questionaire-edit")
	public String postQuestionaireEditScreen(
			Model model,
			@ModelAttribute @Valid QuestionaireDto webDto,
			RedirectAttributes ra,
			BindingResult result) {
		
		if(result.hasErrors()) {
			return "admin/category-edit";
		}
		
		webDto.setUpdateType(CommonConstant.UPDATE_ALL);
		
		questionaireService.updateQuestionaire(webDto);
		
		String successMessage = webDto.getQuestionaireName() + MessageConstant.QUESTIONAIRE_EDITED;
		
		ra.addFlashAttribute("message", successMessage);
		
		return "redirect:/admin/questionaire-list";
	}
	
	@PostMapping("/questionaire-delete")
	public String postCategoryDeleteScreen(
			@ModelAttribute QuestionaireDto webDto,
			RedirectAttributes ra) {
		
		webDto.setUpdateType(CommonConstant.UPDATE_DELETE);
		
		questionaireService.updateQuestionaire(webDto);
		
		String successMessage = MessageConstant.QUESTIONAIRE_DELETED;
		
		ra.addFlashAttribute("message", successMessage);
		
		return "redirect:/admin/questionaire-list";
	}
	
	@GetMapping("/questionaire/question")
	public String showQuestionaireQuestionScreen() {
		
		return "admin/questionaire-question";
	}
}
