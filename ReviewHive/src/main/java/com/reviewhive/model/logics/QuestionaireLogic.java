package com.reviewhive.model.logics;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.reviewhive.model.dao.entity.AnswerEntity;
import com.reviewhive.model.dao.entity.QuestionDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionEntity;
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
	
	/**
	 * Save All Questionaire Questions
	 * @param entites
	 */
	public void saveAllQuestionaireQuestion(List<QuestionEntity> entites);
	
	/**
	 * Save Questionaire Question
	 * @param entity
	 */
	public int saveQuestionaireQuestion(QuestionEntity entity);
	
	/**
	 * Save All Questionaire Question Answers
	 * @param entites
	 */
	public void saveAllQuestionaireQuestionAnswers(List<AnswerEntity> entites);
	
	/**
	 * Save Questionaire Question Answer
	 * @param entity
	 */
	public void saveQuestionaireQuestionAnswer(AnswerEntity entity);
	
	/**
	 * Get Questionaire Questions & Answers by Questionaire Id
	 * @param questionaireId
	 * @return
	 */
	public List<QuestionDetailsEntity> getQuestionaireQuestions(int questionaireId);
	
	public void deleteQuestionsByQuestionaireId(int questionaireId);
	public void deleteAnswersByQuestionaireId(int questionaireId);
	public void deleteQuestionbyId(int questionId);
	public void deleteAnswersByQuestionId(int questionId);
	public void deleteAnswerById(int answerId);
	
	public void updateQuestionById(String question, String questionImage, int questionId);
	public void updateAnswerById(String answer, Boolean isCorrect, int answerId);
	
	
}
