package apps101.jababook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class SurpriseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent newsIntent = new Intent();
		if (Math.random() > 0.5) {
			newsIntent.setClass(this, NewsActivity.class);
		} else {
			newsIntent.setClass(this, MainActivity.class);
		}
		startActivity(newsIntent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.surprise, menu);
		return true;
	}

}
