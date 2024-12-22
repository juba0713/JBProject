package com.reviewhive.model.objects;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class QuestionObj {
	
	private String questionType;
	
	private String question;
	
	private MultipartFile questionImage;
	
	private List<AnswerObj> answers;
	
	public boolean hasNullField() {
        return questionType == null || answers == null;
    }
}
