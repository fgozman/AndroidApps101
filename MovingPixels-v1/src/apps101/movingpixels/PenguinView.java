package apps101.movingpixels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PenguinView extends View implements OnSharedPreferenceChangeListener{
	private Paint p = new Paint();
	private TextPaint textPaint = new TextPaint();
	private Bitmap bPing;
	private Bitmap b;
	private int bPHw;
	private int bPHh;
	private float x;
	private float y;
	private float vx = 1;
	private float vy = 1;
	private boolean touching;
	private Canvas c;
	private String name;
	private boolean gravity;
	private SharedPreferences pref;
	
	
	public PenguinView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	public PenguinView(Context context, AttributeSet attrSet){
		this(context,attrSet,0);
		
	}
	public PenguinView(Context context){
		this(context,null);
	}
	@SuppressLint("ClickableViewAccessibility") 
	private void init() {
		pref =  PreferenceManager.getDefaultSharedPreferences(getContext());
		pref.registerOnSharedPreferenceChangeListener(this);
		onSharedPreferenceChanged(null, null);
		
		Bitmap original = BitmapFactory.decodeResource(getResources(),
				R.drawable.rain_penguin_180);
		int desired = getResources().getDimensionPixelSize(R.dimen.penguin);
		
		bPing = Bitmap.createScaledBitmap(original, desired, desired, true);
		bPHw = bPing.getWidth() / 2;
		bPHh = bPing.getHeight() / 2;
		
		
		b = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
		
		c = new Canvas(b);
		c.drawColor(0xff808080);

		textPaint = new TextPaint();
		p = new Paint();
		p.setColor(0xff0000ff);
		// p.setAntiAlias(true);
		// p.setStyle(Style.STROKE);
		// p.setStrokeWidth(0);
		c.drawLine(0, 3.5f, 3.5f, 0, p);
		
		setOnTouchListener(new OnTouchListener() {

			

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				if (MotionEvent.ACTION_DOWN == action
						|| MotionEvent.ACTION_CANCEL == action) {
					touching = true;
				} else if (MotionEvent.ACTION_UP == action) {
					touching = false;
				}

				if (MotionEvent.ACTION_DOWN == action
						|| MotionEvent.ACTION_MOVE == action) {
					x = event.getX();
					y = event.getY();
					vx = 0;
					vy = 0;

				}
				float sx = b.getWidth() / (float) v.getWidth();
				float sy = b.getHeight() / (float) v.getHeight();
				float pX = event.getX() * sx;
				float pY = event.getY() * sy;
				c.drawCircle(pX, pY, 2, p);
				// event.getAction();
				return true;
			}
		});
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		name = pref.getString("name", "no name");
		gravity = pref.getBoolean("gravity",true);
		
	}
	protected void onDraw(Canvas canvas) {
		// Log.e(TAG," canvas height:"+canvas.getHeight());
		// Log.e(TAG," view height:"+this.getHeight());
		drawBackground(canvas);
		drawPenguin(p, canvas);
		doPenguinPhysics();
		postInvalidateDelayed(100);
	}

	private void doPenguinPhysics() {
		
		if (y + 2 * bPHh + vy + 1 > this.getHeight()) {
			vy = -0.8f * vy;
		} else if(gravity){
			vy = vy + 1;
		}

		x = x + vx;
		y = y + vy;
	}

	private void drawPenguin(final Paint p, Canvas canvas) {
		p.setColor(0x80ffffff);
		p.setStyle(Style.FILL_AND_STROKE);

		float angle = SystemClock.uptimeMillis() / 10f;
		canvas.translate(x, y);

		if(touching){
			canvas.scale(1.2f, 1.2f,bPHw, bPHh);
		}
		canvas.drawCircle(bPHw, bPHh, bPHh, p);
		canvas.rotate(angle, bPHw, bPHh);
		
		textPaint.setTextSize(22);
		textPaint.setColor(0x800000ff);
		canvas.drawText(name, 100, 100, textPaint );
		canvas.drawBitmap(bPing, 0, 0, null);
	}

	private void drawBackground(Canvas canvas) {
		canvas.drawColor(0xffff9900);
		float sx = this.getWidth() / ((float) b.getWidth());
		float sy = this.getHeight() / ((float) b.getHeight());
		canvas.save();
		canvas.scale(sx, sy);

		canvas.drawBitmap(b, 0, 0, null);
		canvas.restore();
	};
}