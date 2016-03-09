package org.learn.quizzes;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {
	public static final String QUIZ_INDEX = "quizIndex";
	
	private QuizzesRepository quizzesRepo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quizzesRepo =  QuizzesRepository.getInstance(this);
        List<Quiz> quizzes = quizzesRepo.getQuizzes();
        ListView listview = (ListView) findViewById(R.id.listViewQuizzes);
        List<String> list = new ArrayList<String>(quizzes.size());
        for (Quiz quiz:quizzes) {
          list.add(quiz.getSubject());
        }
        QuizzesListAdapter adapter = new QuizzesListAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                int position, long id) {
              Intent go = new Intent(MainActivity.this,QuizActivity.class);
              go.putExtra(QUIZ_INDEX, (int)id);
              startActivity(go);
            }

          });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private class QuizzesListAdapter extends ArrayAdapter<String> {

        public QuizzesListAdapter(Context context, int textViewResourceId,
            List<String> objects) {
          super(context, textViewResourceId, objects);
        }

        @Override
        public long getItemId(int position) {
          return position;
        }

        @Override
        public boolean hasStableIds() {
          return true;
        }

      }
    
}
