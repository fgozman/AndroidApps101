package apps101.movingpixels;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			showPrefBeforeHoneycomb();
		} else {
			showPrefFragmentStyle(savedInstanceState);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void showPrefFragmentStyle(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();
			Fragment fragment = new MyPrefFragment();
			transaction.replace(android.R.id.content, fragment);
			transaction.commit();
		}// else the syste,m remember 

	}

	@SuppressWarnings("deprecation")
	private void showPrefBeforeHoneycomb() {
		addPreferencesFromResource(R.xml.penguin_prefs);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class MyPrefFragment extends PreferenceFragment {
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			Log.d("F", "I am attached to an activity - I have a context!");
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			this.addPreferencesFromResource(R.xml.penguin_prefs);
			return super.onCreateView(inflater, container, savedInstanceState);
		}
	}
}
