package org.learn.quizzes;

import java.util.List;
import java.util.Set;

public class Question {
	private String name;
	private String statement;
	private Type type;
	private List<String> variants;
	private Set<String> answers;
	private Set<String> responses;
	
	public static enum Type{
		SINGLE,MULTIPLE,ENTER
	}
	
	public boolean isResponseCorrect(){
		return answers.equals(responses);
	}

	public Set<String> getResponses() {
		return responses;
	}

	public void setResponses(Set<String> responses) {
		this.responses = responses;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<String> getVariants() {
		return variants;
	}

	public void setVariants(List<String> variants) {
		this.variants = variants;
	}

	public Set<String> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<String> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "Question [name=" + name + ", statement=" + statement
				+ ", type=" + type + ", variants=" + variants + ", answers="
				+ answers + "]";
	}
	
	
}
