package org.learn.quizzes;

import java.util.List;

public class Quiz {
	private String subject;
	private List<Question> questions;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	@Override
	public String toString() {
		return "Quiz [subject=" + subject + ", questions=" + questions + "]";
	}
	
	public int getCorrectResponses(){
		int correct = 0;
		for(Question q:questions){
			if(q.isResponseCorrect()){
				correct++;
			}
		}
		
		return correct;
	}
}
