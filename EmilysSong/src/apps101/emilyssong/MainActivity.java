package apps101.emilyssong;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	
	private static final String HTTP_WWW_ZONIZ_COM = "http://www.zoniz.com";
	private static final String HTTP_WWW_GEBS_RO = "http://www.gebs.ro";
	private MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("Banana","onCreate!"); // Put this inside your onCreate method 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	
	@Override
	protected void onResume() {
		Log.e("Banana","onResume"); // Put this inside your onResume method  
		mp  = MediaPlayer.create(this, R.raw.deep_purple_a_gipsys_kiss);
		mp.start();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.e("Banana","onPause!"); // You get the idea - Now test it! 
		mp.stop();
		mp.release();
		mp = null;
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		Log.e("Banana","onDestroy!"); // You get the idea - Now test it! 
		
		super.onDestroy();
	}
	@Override
	protected void onStop() {
		Log.e("Banana","onStop!"); // You get the idea - Now test it! 
		super.onDestroy();
	}
	
	@Override
	protected void onStart() {
		Log.e("Banana","onStart"); // You get the idea - Now test it! 
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void openFB(View v){
		String url = HTTP_WWW_GEBS_RO;
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
		
	}
	
	public void openBC(View v){
		String url = HTTP_WWW_ZONIZ_COM;
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
		
	}
}
