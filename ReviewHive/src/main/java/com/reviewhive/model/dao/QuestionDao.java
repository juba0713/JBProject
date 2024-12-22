package com.reviewhive.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reviewhive.model.dao.entity.AnswerDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionEntity;

/**
 * @author Julius P. Basas
 * @added 12/17/2024
 */
@Service
@Repository
@Transactional
public interface QuestionDao extends JpaRepository<QuestionEntity, Integer>{
	
	/*
	 * Query for Getting Questions Details By Id
	 */
	public static final String GET_QUESTION_DETAILS_BY_ID = "SELECT q.id,\r\n"
			+ "	q.questionaire_id,\r\n"
			+ "	q.question,\r\n"
			+ "	q.question_type,"
			+ " q.question_image \r\n"
			+ "FROM m_question q\r\n"
			+ "WHERE q.questionaire_id = :questionaireId\r\n"
			+ "AND q.is_open = true\r\n"
			+ "AND q.delete_flg = false";
	
	public static final String GET_QUESTION_ANSWERS_BY_QUESTION_ID = "SELECT a.id,\r\n"
			+ "	a.question_id,\r\n"
			+ "	a.answer,\r\n"
			+ "	a.is_correct,"
			+ " a.answer_image\r\n"
			+ "FROM t_answer a\r\n"
			+ "WHERE a.question_id = :questionId\r\n"
			+ "AND a.is_open = true\r\n"
			+ "AND a.delete_flg = false";
	
	/**
	 * Get Questions By Questionaire Id
	 * @param questionaireId
	 * @return
	 * @throws DataAccessException
	 */
	@Query(value=GET_QUESTION_DETAILS_BY_ID, nativeQuery=true)
	public List<Object[]> getQuestionDetailsByQuestionaireIdRaw(int questionaireId) throws DataAccessException;
	
	/**
	 * Get Answer By Question Id
	 * @param questionId
	 * @return
	 * @throws DataAccessException
	 */
	@Query(value=GET_QUESTION_ANSWERS_BY_QUESTION_ID, nativeQuery=true)
	public List<Object[]> getAnswerDetailsByQuestionIdRaw(int questionId) throws DataAccessException;
	
	/**
	 * Get Question Details along with the answers
	 * @param questionaireId
	 * @return
	 */
	default List<QuestionDetailsEntity> getQuestionDetailsByQuestionaireId(int questionaireId){
		
		List<QuestionDetailsEntity> questions = new ArrayList<>();
		
		List<Object[]> questionsRaw = getQuestionDetailsByQuestionaireIdRaw(questionaireId);
		
		for(Object[] qObject : questionsRaw) {
			
			List<AnswerDetailsEntity> answers = new ArrayList<>();
			
			QuestionDetailsEntity question = new QuestionDetailsEntity(qObject);
			
			List<Object[]> answersRaw = getAnswerDetailsByQuestionIdRaw(question.getId());
			
			for(Object[] aObject : answersRaw) {
				
				AnswerDetailsEntity answer = new AnswerDetailsEntity(aObject);
				
				answers.add(answer);
			}
			
			question.setAnswers(answers);
			
			questions.add(question);
		}
		
		return questions;
	}
	
	/*
	 * Query for deleting a question
	 */
	public static final String DELETE_QUESTION = "DELETE FROM QuestionEntity q WHERE q.questionaireId = :questionaireId";
	
	/**
	 * Delete Questions
	 * @param questionaireId
	 */
	@Modifying
	@Query(value=DELETE_QUESTION)
	public void deleteQuestionByQuestionaireId(int questionaireId);
}
