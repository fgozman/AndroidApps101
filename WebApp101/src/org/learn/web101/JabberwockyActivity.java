package org.learn.web101;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;

public class JabberwockyActivity extends Activity {
	private WebView webView1;
	private MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jabberwocky);
		webView1 = (WebView)findViewById(R.id.webView1);
		webView1.getSettings().setBuiltInZoomControls(true);
		webView1.loadUrl("file:///android_asset/jabberwocky.html");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jabberwocky, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			if(webView1.canGoBack()) {
				webView1.goBack();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	public void onDisplayImage(View v){
		webView1.loadUrl("file:///android_asset/375px-Jabberwocky.jpg");
	}
	
	public void onWikipedia(View v){
		String url = "http://en.wikipedia.org/wiki/Jabberwocky";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
		
	}
	@Override
	protected void onResume() {
		mp  = MediaPlayer.create(this, R.raw.klankbeeld_horror_ghost_low);
		mp.setLooping(true);
		mp.start();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		mp.stop();
		mp.release();
		mp = null;
		super.onPause();
	}
	
}
