package org.learn.imagen;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity2 extends Activity implements OnClickListener{

    private static final String TAG = MainActivity2.class.getSimpleName();
	private static final String KEY_COUNT = "count";
	private SharedPreferences prefs;
	private TextView textView;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate!");
        prefs = getPreferences(MODE_PRIVATE);
        int count = prefs.getInt(KEY_COUNT, 0);
        
        
        count++;
        Editor editor = prefs.edit();
        editor.putInt(KEY_COUNT, count);
        editor.commit();
        textView = new TextView(this);
        textView.setTextSize(80);
        textView.setText("Count :"+count);
        Log.d(TAG,"Count is "+count);
        setContentView(textView);
//        setContentView(R.layout.activity_main);
        
        textView.setOnClickListener(this);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		//SystemClock.sleep(2000);
		
		textView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				finish();
				int clickCount = 20 + prefs.getInt("clicked", 0);
				prefs.edit().putInt("clicked", clickCount)
				.putBoolean("user", true).commit();
				textView.setTextColor(0xff00ff00);
				textView.setText("Click! "+clickCount);
			}
		}, 2000);
		
	}
}
