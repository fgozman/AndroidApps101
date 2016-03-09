package org.learn.imagen;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity3 extends Activity {

    private static final String TAG = MainActivity3.class.getSimpleName();
	private Bitmap bitmap;
	private Canvas canvas;
	private ImageView imageView;
	private Paint paint;
	private Bitmap lenard;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate!");
        bitmap = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);
        lenard = BitmapFactory.decodeResource(getResources(), R.drawable.lenard_revelion_2013);
        canvas = new Canvas(bitmap);
        canvas.drawColor(0xffff6600);
        canvas.drawBitmap(lenard, 100, 100, null);
      
        paint = new Paint();
        paint.setColor(0xff000099);
        paint.setStrokeWidth(16);
		canvas.drawLine(0,0, 320, 480,paint );
        imageView  = new ImageView(this);
        imageView.setImageBitmap(bitmap);
        setContentView(imageView);
        
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
