package com.reviewhive.model.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reviewhive.common.constant.CommonConstant;
import com.reviewhive.common.util.ApplicationPropertiesRead;
import com.reviewhive.common.util.DateFormatUtil;
import com.reviewhive.model.dao.entity.AnswerEntity;
import com.reviewhive.model.dao.entity.QuestionDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionEntity;
import com.reviewhive.model.dao.entity.QuestionaireDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionaireEntity;
import com.reviewhive.model.dto.QuestionaireDto;
import com.reviewhive.model.logics.QuestionaireLogic;
import com.reviewhive.model.objects.AnswerObj;
import com.reviewhive.model.objects.QuestionObj;
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

	/**
	 * Get Questionaire by its id
	 * @param inDto
	 * @return
	 */
	@Override
	public QuestionaireDto getQuestionaireById(QuestionaireDto inDto) {
		
		QuestionaireDto outDto = new QuestionaireDto();
		
		QuestionaireEntity questionaire = questionaireLogic.getQuestionaireById(inDto.getId());
		
		outDto.setQuestionaire(questionaire);		
		
		return outDto;
	}

	/**
	 * Update Questionaire
	 * @param inDto
	 */
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

	/**
	 * To save the questionaire question
	 * @param inDto
	 * @throws IOException 
	 */
	@Override
	public void saveQuestionaireQuestion(QuestionaireDto inDto) throws IOException {
		
		//List<QuestionEntity> questions = new ArrayList<>();
		
		Path uploadPath = Paths.get(ApplicationPropertiesRead.getProperty("question.image.path"));

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		if(inDto.getQuestions().size() != 0) {
			
			questionaireLogic.deleteQuestionsByQuestionaireId(inDto.getId());
			questionaireLogic.deleteAnswersByQuestionaireId(inDto.getId());
			
			for(QuestionObj questionObj : inDto.getQuestions()) {
				
				QuestionEntity question = new QuestionEntity();
				question.setQuestionaireId(inDto.getId());
				question.setQuestionType(questionObj.getQuestionType());
				question.setQuestion(questionObj.getQuestion());
				question.setIsOpen(true);
				question.setCreatedDate(DateFormatUtil.currentDate());
				question.setUpdatedDate(DateFormatUtil.currentDate());
				question.setQuestionImage(questionObj.getQuestionImage().getOriginalFilename());
				question.setDeleteFlg(false);
				
				int questionId = questionaireLogic.saveQuestionaireQuestion(question);
				
				MultipartFile questionImage = questionObj.getQuestionImage();

		        if (questionImage != null && !questionImage.isEmpty()) {
		   
		            Path questionFolderPath = uploadPath.resolve(String.valueOf(questionId));

		            // Recreate the folder
		            Files.createDirectories(questionFolderPath);
		            
		            String originalFilename = questionImage.getOriginalFilename();
		            String extension = "";
		            if (originalFilename != null && originalFilename.contains(".")) {
		                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
		            }

		            // Define the file path within the subfolder
		            Path filePath = questionFolderPath.resolve(originalFilename);

		            // Save the image
		            Files.copy(questionImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		        }
				
				if(questionObj.getQuestionType().equals(CommonConstant.MULTIPLE_CHOICE)) {
					
					List<AnswerEntity> answers = new ArrayList<>();
					
					if(questionObj.getAnswers() != null && questionObj.getAnswers().size() != 0) {
						
						for(AnswerObj answerObj : questionObj.getAnswers()) {
							
							AnswerEntity answer = new AnswerEntity();
							answer.setQuestionId(questionId);
							answer.setQuestionaireId(inDto.getId());
							answer.setAnswer(answerObj.getAnswer());
							answer.setIsCorrect(answerObj.getIsCorrect());
							answer.setIsOpen(true);							
							answer.setCreatedDate(DateFormatUtil.currentDate());
							answer.setUpdatedDate(DateFormatUtil.currentDate());
							answer.setDeleteFlg(false);
							
							answers.add(answer);
						}
						
						questionaireLogic.saveAllQuestionaireQuestionAnswers(answers);
					}
				}
				
				if(questionObj.getQuestionType().equals(CommonConstant.TRUE_FALSE)) {
					
					List<AnswerEntity> answers = new ArrayList<>();
					
					if(questionObj.getAnswers().size() != 0) {
						
						for(AnswerObj answerObj : questionObj.getAnswers()) {
							
							AnswerEntity answer = new AnswerEntity();
							answer.setQuestionId(questionId);
							answer.setQuestionaireId(inDto.getId());
							answer.setAnswer(answerObj.getAnswer());
							answer.setIsCorrect(answerObj.getIsCorrect());
							answer.setIsOpen(true);							
							answer.setCreatedDate(DateFormatUtil.currentDate());
							answer.setUpdatedDate(DateFormatUtil.currentDate());
							answer.setDeleteFlg(false);
							
							answers.add(answer);
						}
						
						questionaireLogic.saveAllQuestionaireQuestionAnswers(answers);
					}
				}
				
				if(questionObj.getQuestionType().equals(CommonConstant.IDENTIFICATION)) {
					
					AnswerEntity answer = new AnswerEntity();
					answer.setQuestionId(questionId);
					answer.setQuestionaireId(inDto.getId());
					answer.setAnswer(questionObj.getAnswers().get(0).getAnswer());
					answer.setIsCorrect(questionObj.getAnswers().get(0).getIsCorrect());
					answer.setIsOpen(true);							
					answer.setCreatedDate(DateFormatUtil.currentDate());
					answer.setUpdatedDate(DateFormatUtil.currentDate());
					answer.setDeleteFlg(false);
					
					questionaireLogic.saveQuestionaireQuestionAnswer(answer);
				}
				
				//questions.add(question);
			}
		}
	}

	/**
	 * Get Questionaire Questions & Answers
	 * @param inDto
	 * @return
	 */
	@Override
	public QuestionaireDto getQuestionaireQuestions(QuestionaireDto inDto) {
		
		QuestionaireDto outDto = new QuestionaireDto();
		
		List<QuestionDetailsEntity> retrievedQuestions = questionaireLogic.getQuestionaireQuestions(inDto.getId());
		
		outDto.setRetrievedQuestions(retrievedQuestions);
		
		return outDto;
	}

}
