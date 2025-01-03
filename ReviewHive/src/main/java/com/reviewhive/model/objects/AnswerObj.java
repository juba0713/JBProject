package com.reviewhive.model.objects;

import lombok.Data;

@Data
public class AnswerObj {
	
	private int answerId;

	private String answerNo;
	
	private String answer;
	
	private Boolean isCorrect;
	
	private Boolean hasModified;
	
	private Boolean hasDeleted;
	
	public boolean hasNullField() {
        return answer == null || isCorrect == null;
    }
}
