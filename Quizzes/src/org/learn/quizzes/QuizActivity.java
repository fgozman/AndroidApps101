package org.learn.quizzes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.learn.quizzes.Question.Type;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class QuizActivity extends Activity {
	private static final String QUESTION_INDEX = "questionIndex";
	private QuizzesRepository quizzesRepo;
	private Question question;
	private int questionIndex;
	private int quizIndex;
	private Set<String> responses=new HashSet<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);
        quizzesRepo =  QuizzesRepository.getInstance(this);
        Intent intentObject = getIntent();
        quizIndex = intentObject.getIntExtra(MainActivity.QUIZ_INDEX, -1);
        questionIndex = intentObject.getIntExtra(QUESTION_INDEX, 0);
        if(quizIndex>-1){
        	Quiz quiz = quizzesRepo.getQuiz(quizIndex);
        	String subject = quiz.getSubject();
        	setTitle(getTitle()+": "+subject);;
        	question = quiz.getQuestions().get(questionIndex);
        	// clear responses if any from previous tests
        	question.setResponses(responses);
        	TextView textViewQuestion = (TextView)findViewById(R.id.textViewQuestion);
        	textViewQuestion.setText(question.getName()+" ("+(questionIndex+1)+"/"+quiz.getQuestions().size()+")");
        	TextView textViewStatement = (TextView)findViewById(R.id.textViewStatement);
        	Spanned spannedStatement = Html.fromHtml(question.getStatement());
        	textViewStatement.setText(spannedStatement);
        	buildVariantsView((ViewGroup)findViewById(R.id.layoutVariants), question);
        }
        
    }


	public void buildVariantsView(ViewGroup container, Question question) {
		switch(question.getType()){
		case SINGLE:{
			RadioGroup v = (RadioGroup)View.inflate(this,R.layout.question_single,null);
			container.addView(v);
			List<String> variants = question.getVariants();
			for(int i = 0; i < variants.size();i++){
				RadioButton rdbtn = (RadioButton)View.inflate(this, R.layout.variant_single, null);
				rdbtn.setId(i+1);// the id will be the answer
				rdbtn.setText(Html.fromHtml(variants.get(i)));
				v.addView(rdbtn);
			}
			break;
		}
		case MULTIPLE:{
			LinearLayout v = (LinearLayout)View.inflate(this, R.layout.question_multiple,null);
			container.addView(v);
			List<String> variants = question.getVariants();
			for(int i = 0; i < variants.size();i++){
				CheckBox checkbox = (CheckBox)View.inflate(this, R.layout.variant_multiple, null);
				checkbox.setId(i+1);// the id will be the answer
				checkbox.setText(Html.fromHtml(variants.get(i)));
				v.addView(checkbox);
			}
			break;
		}
		case ENTER:{
			EditText editText = (EditText)View.inflate(this, R.layout.variant_enter, null);
			editText.setId(1);// this will be only for reference
			container.addView(editText);
			break;
		}
		}
		
	}
	
	public void onVariant(View view){
		switch(question.getType()){
		case SINGLE:{
			RadioButton radio = (RadioButton)view;
			String resp = radio.getId()+"";
			responses.clear();
			if(radio.isChecked()){
				responses.add(resp);
			}
			
			break;
		}
		case MULTIPLE:{
			CheckBox checkBox = (CheckBox)view;
			String resp = checkBox.getId()+"";
			if(checkBox.isChecked()){
				responses.add(resp);
			}else{
				responses.remove(resp);
			}
			
			break;
		}
		default:// do nothing
		}
		
	}
	public void onDone(View view){
		if(question.getType() == Type.ENTER){
			// in this case we should take the text
			Editable edit = ((EditText)findViewById(1)).getText();
			if(edit!=null){
				responses.add(edit.toString().trim());
			}
		}
		question.setResponses(responses);
		
		if(questionIndex<(quizzesRepo.getQuestionsNumber(quizIndex)-1)){
			Intent go = new Intent(this,QuizActivity.class);
			go.putExtra(MainActivity.QUIZ_INDEX, quizIndex);
	        go.putExtra(QUESTION_INDEX, questionIndex+1);
	        startActivity(go);
	        finish();
		}else{
			Intent go = new Intent(this,ScoreActivity.class);
			go.putExtra(MainActivity.QUIZ_INDEX, quizIndex);
	        startActivity(go);
	        finish();
		}
		
	}
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    
}
