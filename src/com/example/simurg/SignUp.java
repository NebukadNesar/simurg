package com.example.simurg;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.SavedData;

/**
 * 
 * @author tavatr
 * 
 */
public class SignUp extends Activity {
	Button signup;
	EditText username, password, passwordx;
	SharedPreferences settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		signup = (Button) findViewById(R.id.signup);
		username = (EditText) findViewById(R.id.username_s);
		password = (EditText) findViewById(R.id.password_s);
		passwordx = (EditText) findViewById(R.id.password_sx);
		settings = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		signup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				signup();
			}
		});
	}

	/**
	 * 
	 */
	public void signup() {
		String un = "", p = "", px = "";
		un = username.getText().toString().trim();
		p = password.getText().toString().trim();
		px = passwordx.getText().toString().trim();
		if (!un.equals("") && !p.equals("") && p.equals(px)) {
			SharedPreferences.Editor e = settings.edit();
			e.putString(SavedData.USER_NAME, un);
			e.putString(SavedData.USER_PWD, p);
			e.putString(SavedData.FIRST_TIMEUSE, "none not");
			e.commit();
			Toast.makeText(
					getApplicationContext(),
					settings.getString(SavedData.USER_NAME, "") + ","
							+ settings.getString(SavedData.USER_PWD, ""),
					Toast.LENGTH_LONG).show();
			startActivity(new Intent(getApplicationContext(),
					NewEntry.class));
			finish();
		}
	}
}
