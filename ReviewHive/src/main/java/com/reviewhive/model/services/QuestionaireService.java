package com.reviewhive.model.services;

import java.io.IOException;

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
	
	/**
	 * To save the questionaire question
	 * @param inDto
	 */
	public void saveQuestionaireQuestion(QuestionaireDto inDto) throws IOException ;
	
	/**
	 * Get Questionaire Questions & Answers
	 * @param inDto
	 * @return
	 */
	public QuestionaireDto getQuestionaireQuestions(QuestionaireDto inDto);
}
