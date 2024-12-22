package com.reviewhive.model.objects;

import lombok.Data;

@Data
public class AnswerObj {

	private String answerNo;
	
	private String answer;
	
	private Boolean isCorrect;
	
	public boolean hasNullField() {
        return answer == null || isCorrect == null;
    }
}
