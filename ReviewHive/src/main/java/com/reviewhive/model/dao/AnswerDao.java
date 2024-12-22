package com.reviewhive.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reviewhive.model.dao.entity.AnswerEntity;

/**
 * @author Julius P. Basas
 * @added 12/17/2024
 */
@Service
@Repository
@Transactional
public interface AnswerDao extends JpaRepository<AnswerEntity, Integer> {
	
	/*
	 * Query for deleting a answer
	 */
	public static final String DELETE_ANSWER = "DELETE FROM AnswerEntity a WHERE a.questionaireId = :questionaireId";
	
	/**
	 * Delete Answer
	 * @param questionaireId
	 */
	@Modifying
	@Query(value=DELETE_ANSWER)
	public void deleteAnswerByQuestionaireId(int questionaireId);
}
