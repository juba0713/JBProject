package com.reviewhive.model.logics;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.reviewhive.model.dao.entity.QuestionaireDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionaireEntity;

/**
 * @author Julius P. Basas
 * @added 12/19/2024
 */
@Service
public interface QuestionaireLogic {
	
	/**
	 * Save Questionaire
	 * @param entity
	 */
	public void saveQuestionaire(QuestionaireEntity entity);
	
	/**
	 * Get All Questionaire
	 * @param page
	 */
	public List<QuestionaireDetailsEntity> getAllQuestionaireByPage(int page);
	
	/**
	 * Get Questionaire by its id
	 * @param id
	 * @return QuestionaireEntity
	 */
	public QuestionaireEntity getQuestionaireById(int id);
	
	/**
	 * Update Questionaire Status
	 * @param status
	 * @param id
	 */
	public void updateQuestionaireStatus(boolean status, int id);
	
	/**
	 * Update Questionaire All
	 * @param questionaireName
	 * @param questionaireDescription
	 * @param questionaireAbbreviation
	 * @param questionaireCategoryId
	 * @param questionaireColor
	 * @param questionaireStatus
	 * @param updateDate
	 * @param id
	 */
	public void updateQuestionaireAll(String questionaireName, 
			String questionaireDescription,
			String questionaireAbbreviation,
			int questionaireCategoryId,
			String questionaireColor,
			boolean questionaireStatus,
			Timestamp updateDate,
			int id);
	
	/**
	 * Update Questionaire Delete
	 * @param status
	 * @param id
	 */
	public void updateQuestionaireDelete(int id);
}
