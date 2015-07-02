package com.example.simurg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.model.SavedData;

/**
 * 
 * @author tavatr
 * 
 */
public class MainActivity extends Activity {

	Button login;
	EditText username, password;
	SharedPreferences settings;
	RelativeLayout lin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		settings = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		
		if (settings.getString(SavedData.FIRST_TIMEUSE, "").equals("")) {
			startActivity(new Intent(getApplicationContext(), SignUp.class));
		}

		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				signin();
			}
		});

		hideKeyBoard();
	}

	/**
	 * don't forget your user creadidentials :))
	 */
	public void signin() {
		String u = "", p = "";
		u = username.getText().toString().trim();
		p = password.getText().toString().trim();
		if (!u.equals("") && !p.equals("")) {
			if (settings.getString(SavedData.USER_NAME, "").equals(u)
					&& settings.getString(SavedData.USER_PWD, "").equals(p)) {
				startActivity(new Intent(getApplicationContext(),
						ListAllData.class));
				username.setText("");
				password.setText("");
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.exit:
			Toast.makeText(getApplicationContext(), "exiting!",
					Toast.LENGTH_SHORT).show();
			System.exit(0);

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void hideKeyBoard() {
		lin = (RelativeLayout) findViewById(R.id.relative_layout);
		lin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
						.getWindowToken(), 0);

			}
		});
	}

}
