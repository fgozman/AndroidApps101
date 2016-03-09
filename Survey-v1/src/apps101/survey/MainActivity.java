/*
Copyright (c) 2014 Lawrence Angrave
Dual licensed under Apache2.0 ((http://www.apache.org/licenses/LICENSE-2.0.txt) 
and MIT Open Source License (included below): 
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package apps101.survey;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements TextWatcher{

	private static final String TAG = "MainActivity";
	private EditText mName;
	private EditText mPhone;
	private EditText mEmail;
	private EditText mComments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mName = (EditText) findViewById(R.id.name);
		mPhone = (EditText) findViewById(R.id.phone);
		mEmail = (EditText) findViewById(R.id.email);
		mComments = (EditText) findViewById(R.id.comments);
		// TextWatcher watcher = new TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before, int
		// count) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count,
		// int after) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// String comments = s.toString();
		// boolean valid = comments.length() > 0 &&
		// comments.toLowerCase().indexOf("duck") == -1;
		// View v = findViewById(R.id.imageButton1);
		// boolean isVisible = v.getVisibility() == View.VISIBLE;
		//
		// if(isVisible == valid){
		// return;
		// }
		// Animation a;
		// if(valid){
		// v.setVisibility(View.VISIBLE);
		// a = AnimationUtils.makeInAnimation(MainActivity.this, true);
		// }else{
		// a = AnimationUtils.makeOutAnimation(MainActivity.this, true);
		// v.setVisibility(View.INVISIBLE);
		//
		// }
		// v.startAnimation(a);
		// }
		// };
		mComments.addTextChangedListener(this);
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		String comments = s.toString();
		String duck = getString(R.string.duck);
		boolean valid = comments.length() > 0
				&& comments.toLowerCase().indexOf(duck) == -1;
		View v = findViewById(R.id.imageButton1);
		boolean isVisible = v.getVisibility() == View.VISIBLE;

		if (isVisible == valid) {
			return;
		}
		Animation a;
		if (valid) {
			v.setVisibility(View.VISIBLE);
			a = AnimationUtils.makeInAnimation(MainActivity.this, true);
		} else {
			a = AnimationUtils.makeOutAnimation(MainActivity.this, true);
			v.setVisibility(View.INVISIBLE);

		}
		v.startAnimation(a);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void processForm(View duck) {
		String comments = mComments.getText().toString();
		String email = mEmail.getText().toString();
		String phone = mPhone.getText().toString();
		String name = mName.getText().toString();
		// Intent i = new Intent(Intent.ACTION_SEND);// send SMS, Email or
		// Bluetooth
		// i.setType("text/plain");
		// i.putExtra(Intent.EXTRA_TEXT, "What a wonderful app!");
		// Intent i = new Intent(Intent.ACTION_VIEW);// send SMS
		// i.setData(Uri.parse("sms:"+phone));
		// i.putExtra("sms_body", comments);
		Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "",
				null));// send email
		i.putExtra(Intent.EXTRA_SUBJECT, "important news");
		String message = name + " says " + comments;
		if (phone.length() > 0) {
			message = message + "\nPhone:" + phone;
		}
		if (email.length() > 0) {
			message = message + "\nEmail:" + email;
		}

		i.putExtra(Intent.EXTRA_TEXT, message);
		if (i.resolveActivity(getPackageManager()) == null) {
			Toast.makeText(this.getApplicationContext(),
					"Configure your email client!", Toast.LENGTH_LONG).show();
		} else {
			startActivity(Intent.createChooser(i,
					"Please choose your email app"));
		}

		// try{
		// startActivity(Intent.createChooser(i,
		// "Please choose your email app"));
		// }catch(Exception e){
		// Toast.makeText(this.getApplicationContext(), "Cannot send email!",
		// Toast.LENGTH_LONG).show();
		// Log.e("MainActivity", "Could not send email",e);
		// }
	}

	public void processFormOld(View duck) {
		String comments = mComments.getText().toString();
		String email = mEmail.getText().toString();
		String phone = mPhone.getText().toString();
		String name = mName.getText().toString();

		int position = email.indexOf("@");
		if (position == -1) {
			Toast.makeText(this.getApplicationContext(),
					"Invalid email address!", Toast.LENGTH_LONG).show();
			mEmail.requestFocus();
			return;
		}
		int len = comments.length();
		if (len == 0) {
			Toast.makeText(this.getApplicationContext(), "Give me commments!",
					Toast.LENGTH_LONG).show();

			return;
		}
		if (name.equals("Fred")) {
			Toast.makeText(this.getApplicationContext(), "Hi Fred!",
					Toast.LENGTH_LONG).show();
			return;
		}
		int value = -1;
		boolean valueOk = false;
		try {
			value = Integer.parseInt(phone);
			valueOk = true;
			Log.d(TAG, "Phone number is " + value);
		} catch (Exception e) {
			Log.d(TAG, "Invalid phone value - no of digits: "
					+ phone);
		}

		// if(!email.contains("@")){
		//
		// }
		String username = email.substring(0, position);
		String thankyou = "Thankyou " + username + "!";
		Toast.makeText(this.getApplicationContext(), thankyou,
				Toast.LENGTH_LONG).show();

		Animation anim = AnimationUtils.makeOutAnimation(this, true);
		duck.startAnimation(anim);
		duck.setVisibility(View.INVISIBLE);// take space
		// duck.setVisibility(View.GONE);// does not take space
		Toast.makeText(this.getApplicationContext(), R.string.app_name,
				Toast.LENGTH_LONG).show();
	}
}
