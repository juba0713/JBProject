package com.reviewhive.model.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reviewhive.common.constant.CommonConstant;
import com.reviewhive.common.util.DateFormatUtil;
import com.reviewhive.model.dao.entity.CategoryDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionaireDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionaireEntity;
import com.reviewhive.model.dto.CategoryDto;
import com.reviewhive.model.dto.QuestionaireDto;
import com.reviewhive.model.logics.QuestionaireLogic;
import com.reviewhive.model.services.QuestionaireService;

/**
 * @author Julius P. Basas
 * @added 12/19/2024
 */
@Service
public class QuestionaireServiceImpl implements QuestionaireService {
	
	@Autowired
	private QuestionaireLogic questionaireLogic;

	/**
	 * Save Questionaire
	 * @param inDto
	 */
	@Override
	public void saveQuestionaire(QuestionaireDto inDto) {
		
		QuestionaireEntity questionaire = new QuestionaireEntity();
		questionaire.setCategoryId(inDto.getQuestionaireCategoryId());
		questionaire.setAbbreviation(inDto.getQuestionaireAbbreviation());
		questionaire.setQuestionaireName(inDto.getQuestionaireName());
		questionaire.setDescription(inDto.getQuestionaireDescription());
		questionaire.setHexColor(inDto.getQuestionaireColor());
		questionaire.setIsOpen(inDto.getQuestionaireStatus().equals("true") ? true : false);
		questionaire.setCreatedDate(DateFormatUtil.currentDate());
		questionaire.setUpdatedDate(DateFormatUtil.currentDate());
		questionaire.setDeleteFlg(false);
		
		questionaireLogic.saveQuestionaire(questionaire);
	}

	/**
	 * Get All Questionaire By Page
	 * @param inDto
	 * @return QuestionaireDto
	 */
	@Override
	public QuestionaireDto getAllQuestionaireByPage(QuestionaireDto inDto) {
		
		QuestionaireDto outDto = new QuestionaireDto();
		
		List<QuestionaireDetailsEntity> questionaires = questionaireLogic.getAllQuestionaireByPage(inDto.getPage());
		
		outDto.setQuestionaires(questionaires);
		
		return outDto;
	}

	@Override
	public QuestionaireDto getQuestionaireById(QuestionaireDto inDto) {
		
		QuestionaireDto outDto = new QuestionaireDto();
		
		QuestionaireEntity questionaire = questionaireLogic.getQuestionaireById(inDto.getId());
		
		outDto.setQuestionaire(questionaire);		
		
		return outDto;
	}

	@Override
	public void updateQuestionaire(QuestionaireDto inDto) {
		
		Boolean status = false;
		if(inDto.getUpdateType().equals(CommonConstant.UPDATE_STATUS) ||
			inDto.getUpdateType().equals(CommonConstant.UPDATE_ALL)){
			status = inDto.getQuestionaireStatus().equals("true") ? true : false;
		}
			
		switch(inDto.getUpdateType()) {
			
			case CommonConstant.UPDATE_STATUS:
				
				questionaireLogic.updateQuestionaireStatus(status, inDto.getId());
				break;
			case CommonConstant.UPDATE_ALL:
				
				questionaireLogic.updateQuestionaireAll(inDto.getQuestionaireName(), 
						inDto.getQuestionaireDescription(), 
						inDto.getQuestionaireAbbreviation(),
						inDto.getQuestionaireCategoryId(), 
						inDto.getQuestionaireColor(), 
						status, 
						DateFormatUtil.currentDate(), 
						inDto.getId());
				
				break;
			case CommonConstant.UPDATE_DELETE:
				
				questionaireLogic.updateQuestionaireDelete(inDto.getId());
				
				break;
		}
		
	}

}
