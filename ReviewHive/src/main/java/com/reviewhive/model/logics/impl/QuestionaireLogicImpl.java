package com.reviewhive.model.logics.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reviewhive.common.util.ApplicationPropertiesRead;
import com.reviewhive.model.dao.AnswerDao;
import com.reviewhive.model.dao.QuestionDao;
import com.reviewhive.model.dao.QuestionaireDao;
import com.reviewhive.model.dao.entity.AnswerEntity;
import com.reviewhive.model.dao.entity.QuestionDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionEntity;
import com.reviewhive.model.dao.entity.QuestionaireDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionaireEntity;
import com.reviewhive.model.logics.QuestionaireLogic;

/**
 * @author Julius P. Basas
 * @added 12/19/2024
 */
@Service
public class QuestionaireLogicImpl implements QuestionaireLogic{
	
	@Autowired
	private QuestionaireDao questionaireDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private AnswerDao answerDao;

	/**
	 * Save Questionaire
	 * @param entity
	 */
	@Override
	public void saveQuestionaire(QuestionaireEntity entity) {
		
		questionaireDao.save(entity);
	}

	/**
	 * Get All Questionaire
	 * @param page
	 */
	@Override
	public List<QuestionaireDetailsEntity> getAllQuestionaireByPage(int page) {
		
		int limit = Integer.valueOf(ApplicationPropertiesRead.getProperty("questionaire.limit"));
		
		int offset = (page-1)*Integer.valueOf(limit);
		
		return questionaireDao.getAllQuestionaireByPage(limit, offset);

	}

	/**
	 * Get Questionaire by its id
	 * @param id
	 * @return QuestionaireEntity
	 */
	@Override
	public QuestionaireEntity getQuestionaireById(int id) {
	
		return questionaireDao.getQuestionaireById(id);
	}

	/**
	 * Update Questionaire Status
	 * @param status
	 * @param id
	 */
	@Override
	public void updateQuestionaireStatus(boolean status, int id) {
		
		questionaireDao.updateQuestionaireStatus(status, id);
	}

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
	@Override
	public void updateQuestionaireAll(String questionaireName, String questionaireDescription,
			String questionaireAbbreviation, int questionaireCategoryId, String questionaireColor,
			boolean questionaireStatus, Timestamp updateDate, int id) {
		
		questionaireDao.updateQuestionaireAll(questionaireName, 
				questionaireDescription, 
				questionaireAbbreviation, 
				questionaireCategoryId, 
				questionaireColor, 
				questionaireStatus, 
				updateDate, 
				id);
		
		
	}

	/**
	 * Update Questionaire Delete
	 * @param status
	 * @param id
	 */
	@Override
	public void updateQuestionaireDelete(int id) {
		
		questionaireDao.updateQuestionaireDelete(id);
	}

	/**
	 * Save All Questionaire Questions
	 * @param entities
	 */
	@Override
	public void saveAllQuestionaireQuestion(List<QuestionEntity> entities) {
		
		questionDao.saveAll(entities);
		
		//return entities;
	}

	/**
	 * Save Questionaire Question
	 * @param entity
	 */
	@Override
	public int saveQuestionaireQuestion(QuestionEntity entity) {
		
		questionDao.save(entity);
		
		return entity.getId();
		
	}

	/**
	 * Save Questionaire Question Answers
	 * @param entites
	 */
	@Override
	public void saveAllQuestionaireQuestionAnswers(List<AnswerEntity> entities) {
		
		answerDao.saveAll(entities);
	}

	/**
	 * Save Questionaire Question Answer
	 * @param entity
	 */
	@Override
	public void saveQuestionaireQuestionAnswer(AnswerEntity entity) {
		
		answerDao.save(entity);
	}

	/**
	 * Get Questionaire Questions & Answers by Questionaire Id
	 * @param questionaireId
	 * @return
	 */
	@Override
	public List<QuestionDetailsEntity> getQuestionaireQuestions(int questionaireId) {
		
		return questionDao.getQuestionDetailsByQuestionaireId(questionaireId);
	}

	@Override
	public void deleteQuestionsByQuestionaireId(int questionaireId) {
		
		questionDao.deleteQuestionByQuestionaireId(questionaireId);
	}

	@Override
	public void deleteAnswersByQuestionaireId(int questionaireId) {
		
		answerDao.deleteAnswerByQuestionaireId(questionaireId);
	}

	@Override
	public void deleteQuestionbyId(int questionId) {
		
		questionDao.deleteQuestionById(questionId);
	}

	@Override
	public void deleteAnswersByQuestionId(int questionId) {
		
		answerDao.deleteAnswerByQuestionId(questionId);
	}

	@Override
	public void updateQuestionById(String question, String questionImage, int questionId) {
		
		questionDao.updateQuestionById(question, questionImage, questionId);
	}

	@Override
	public void deleteAnswerById(int answerId) {
		
		answerDao.deleteAnswerById(answerId);
	}

	@Override
	public void updateAnswerById(String answer, Boolean isCorrect, int answerId) {
		
		answerDao.updateQuestionById(answer, isCorrect, answerId);
	}

}
