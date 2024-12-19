package com.reviewhive.model.services;

import org.springframework.stereotype.Service;

import com.reviewhive.model.dto.QuestionaireDto;

/**
 * @author Julius P. Basas
 * @added 12/19/2024
 */
@Service
public interface QuestionaireService {
	
	/**
	 * Save Questionaire
	 * @param inDto
	 */
	public void saveQuestionaire(QuestionaireDto inDto);
	
	/**
	 * Get All Questionaire By Page
	 * @param inDto
	 * @return QuestionaireDto
	 */
	public QuestionaireDto getAllQuestionaireByPage(QuestionaireDto inDto);
	
	/**
	 * Get Questionaire by its id
	 * @param inDto
	 * @return
	 */
	public QuestionaireDto getQuestionaireById(QuestionaireDto inDto);
	
	/**
	 * Update Questionaire
	 * @param inDto
	 */
	public void updateQuestionaire(QuestionaireDto inDto);
}
