package org.learn.quizzes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;


public class ScoreActivity extends Activity {
	
	private QuizzesRepository quizzesRepo;
	private int quizIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);
        quizzesRepo =  QuizzesRepository.getInstance(this);
        Intent intentObject = getIntent();
        quizIndex = intentObject.getIntExtra(MainActivity.QUIZ_INDEX, -1);
        if(quizIndex>-1){
        	Quiz quiz = quizzesRepo.getQuiz(quizIndex);
        	String subject = quiz.getSubject();
        	TextView viewSubject = (TextView)findViewById(R.id.textViewSubject);
        	viewSubject.setText(subject);
        	TextView viewScore = (TextView)findViewById(R.id.textViewScore);
        	
        	viewScore.setText(quiz.getCorrectResponses()+" / "+quiz.getQuestions().size());
        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
