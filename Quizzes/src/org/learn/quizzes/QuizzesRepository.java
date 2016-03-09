package org.learn.quizzes;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class QuizzesRepository {
	private List<Quiz> qList;
	private static QuizzesRepository quizzesInstance;
	private QuizzesRepository(){}
	public static final QuizzesRepository getInstance(Context context){
		if(quizzesInstance == null){
			quizzesInstance = new QuizzesRepository();
			quizzesInstance.loadQuizzes(context);
		}
		return quizzesInstance;
	}
	private void loadQuizzes(Context context) {
		
		InputStream in = null;
        BufferedInputStream inBuff = null;
        Charset utf8 = Charset.forName("utf-8");
        StringBuilder jsonBuffer = null;
        try{
        	in = context.getAssets().open("quizzes.json");
        	inBuff = new BufferedInputStream(in);
        	jsonBuffer = new StringBuilder(inBuff.available());
        	byte[] bytes = new byte[512];
        	int byteCount = -1;
        	while((byteCount = inBuff.read(bytes))!=-1){
        		jsonBuffer.append(new String(bytes,0,byteCount,utf8));
        	}
        } catch (IOException e) {
        	e.printStackTrace();
        	Log.e(QuizzesRepository.class.getSimpleName(),"loadQuizzes:"+e.toString());
			throw new RuntimeException(e);
		}finally{
			if(inBuff!=null){
				try {
					inBuff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				
        	if(in!=null){
        		try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        
        try {
			JSONArray quizzesJson = new JSONArray(jsonBuffer.toString());
			qList = createQuizzes(quizzesJson);
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e(QuizzesRepository.class.getSimpleName(),"loadQuizzes:"+e.toString());
			throw new RuntimeException(e);
		}
	}
	private List<Quiz> createQuizzes(JSONArray jsonQuizzes) throws JSONException{
		int len = jsonQuizzes.length();
		List<Quiz> qList = new ArrayList<Quiz>(len);
		for(int i=0;i<len;i++){
			JSONObject quizJson = jsonQuizzes.getJSONObject(i);
			Quiz q = createQuiz(quizJson);
			qList.add(q);
		}
		return qList;
	}
	private Quiz createQuiz(JSONObject quizJson) throws JSONException {
		Quiz q = new Quiz();
		q.setSubject(quizJson.getString("subject"));
		JSONArray jsonQuestions = quizJson.getJSONArray("questions"); 
		int len = jsonQuestions.length();
		List<Question> questList = new ArrayList<Question>(len);
		q.setQuestions(questList);
		for(int i = 0; i < len; i++){
			Question quest = createQuestion(jsonQuestions.getJSONObject(i));
			questList.add(quest);
		}
		return q;
		
	}
	private Question createQuestion(JSONObject jsonObject) throws JSONException {
		Question quest = new Question();
		quest.setName(jsonObject.getString("name"));
		quest.setStatement(jsonObject.getString("statement"));
		JSONArray jsonAnsw =  jsonObject.getJSONArray("answers");
		int len = jsonAnsw.length();
		Set<String> answers = new HashSet<String>(len);
		quest.setAnswers(answers);
		for(int i=0;i<len;i++){
			answers.add(jsonAnsw.getString(i));
		}
		String type = jsonObject.getString("type");	
		if(type.equals("single")){
			quest.setType(Question.Type.SINGLE);
			addVariants(jsonObject, quest);
		}else if(type.equals("multiple")){
			quest.setType(Question.Type.MULTIPLE);
			addVariants(jsonObject, quest);
		}else if(type.equals("enter")){
			quest.setType(Question.Type.ENTER);
		}else{
			throw new JSONException("Invalid question type: "+type);
		}
		
		return quest;
	}
	private void addVariants(JSONObject jsonObject, Question quest)
			throws JSONException {
		JSONArray jsonVariants = jsonObject.getJSONArray("variants");
		int len = jsonVariants.length();
		List<String> variants = new ArrayList<String>(len);
		for(int i=0; i<len;i++){
			variants.add(jsonVariants.getString(i));
		}
		quest.setVariants(variants);
	}
	public List<Quiz> getQuizzes(){
		return qList;
	}
	
	public int getQuizzesNumber(){
		return qList.size();
	}
	
	public Quiz getQuiz(int i) {
		return qList.get(i);
	}
	
	public Question getQuestion(int quizIndex, int questIndex) {
		return qList.get(quizIndex).getQuestions().get(questIndex);
	}
	
	public int getQuestionsNumber(int quizIndex) {
		return qList.get(quizIndex).getQuestions().size();
	}
	
	
}
