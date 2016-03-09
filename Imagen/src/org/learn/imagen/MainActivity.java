package org.learn.imagen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextPaint;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int REQUEST_CODE = 1;
	private static final String TAG = MainActivity.class.getSimpleName();
	private Bitmap bitmap;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate!");
        setContentView(R.layout.activity_main);
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Open a gallery with a photo previous taken
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType("image/*");
				
				startActivityForResult(Intent.createChooser(i,"Select..."), REQUEST_CODE);
				//startActivity(i);
			}
		});
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
    		Uri uri = data.getData();
    		Log.d(TAG, uri.toString());
    		Toast.makeText(this.getApplicationContext(), uri.toString(), Toast.LENGTH_LONG).show();
    		InputStream in = null;
			try {
				in = getContentResolver().openInputStream(uri);
				Options options = new Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(in,null,options );
				int w = options.outWidth;
				int h = options.outHeight;
				Log.d(TAG,"Bitmap raw size:"+w+" x "+h);
				in.close();
				int displayW = getResources().getDisplayMetrics().widthPixels;
				int displayH = getResources().getDisplayMetrics().heightPixels;
				
				
				int sample = 1;
				while(w > displayW*sample || h > displayH * sample){
					sample = sample * 2;
				}
				Log.d(TAG,"Sampling at "+sample);
				options.inJustDecodeBounds = false;
				options.inSampleSize = sample;
				in = getContentResolver().openInputStream(uri);
				Bitmap bm = BitmapFactory.decodeStream(in,null,options );
				in.close();
				if(bitmap != null){// for old devices
					bitmap.recycle();
				}
				bitmap = Bitmap.createBitmap(bm.getWidth(),bm.getHeight(),Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(bitmap);
				canvas.drawBitmap(bm,0, 0,null);
				TextPaint tp = new TextPaint();
				tp.setTextSize(bm.getHeight()/2);
				tp.setColor(0x0800000ff);
				canvas.drawText("Gotcha", 0, bm.getHeight()/2, tp);
				
				bm.recycle();// release resources
				
				
				
	    		((ImageView)findViewById(R.id.imageView1)).setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				Log.e(TAG,"Decoding bitmap",e);
			} catch (IOException e) {
				Log.e(TAG,"Decoding bitmap",e);
			}finally{
				if(in!=null){
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
    		
    	}
    }
    
    public void saveAndShare(View v){
    	if(bitmap != null){
    		File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    		Log.d(TAG,"saveAndShare path="+path);
    		path.mkdirs();
    		String fileName = "Imagen_"+System.currentTimeMillis()+".jpg";
    		
    		File file = new File(path,fileName);
    		FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				bitmap.compress(CompressFormat.JPEG, 100, fos);
				fos.close();
			} catch (FileNotFoundException e) {
				Log.e(TAG,"saveAndShare",e);
				return;
			} catch (IOException e) {
				Log.e(TAG,"saveAndShare",e);
				return;
			}finally{
				if(fos!=null){
					try {
						fos.close();
					} catch (IOException e) {
						
					}
				}
			}
			Uri uri = Uri.fromFile(file);
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			intent.setData(uri);
			sendBroadcast(intent);
			
			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("image/jpeg");
			share.putExtra(Intent.EXTRA_STREAM, uri);
			startActivity(Intent.createChooser(share, "Share using ..."));
    		
    		
    	}
    }
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    
    
}
