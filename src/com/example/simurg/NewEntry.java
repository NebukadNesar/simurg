package com.example.simurg;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.helper.DESedeEncryption;
import com.example.helper.JSONhandler;
import com.example.model.Nabs;
import com.example.model.SavedData;

/**
 * 
 * @author tavatr
 * 
 */
public class NewEntry extends Activity {

	JSONArray jsarray;
	Button addEntry;
	EditText username, password, alias;
	ArrayList<Nabs> passlist;
	SharedPreferences settings;
	DESedeEncryption des;
	JSONhandler jshandle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_entry);
		try {
			addEntry = (Button) findViewById(R.id.add_new_button);
			username = (EditText) findViewById(R.id.username_e);
			password = (EditText) findViewById(R.id.password_e);
			alias = (EditText) findViewById(R.id.alias_e);
			jsarray = new JSONArray();
			settings = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			passlist = new ArrayList<Nabs>();
			des = new DESedeEncryption();
			jshandle = new JSONhandler();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		addEntry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					saveEntry();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * username -> u, passwd -> p, alias -> a, getlist of ex data and convert it
	 * to arraylist add new entry and save it back
	 * 
	 * @throws JSONException
	 */
	public void saveEntry() throws JSONException {
		String a = ""; // alias
		String u = ""; // username
		String p = ""; // passwd
		String uuid = ""; // random uniqe id

		a = alias.getText().toString().trim();
		u = username.getText().toString().trim();
		p = password.getText().toString().trim();
		uuid = UUID.randomUUID().toString();

		System.out.println(a + ", " + u + ", " + p);
		if (!a.equals("") && !u.equals("") && !p.equals("")) {
			if (jsarray.length() == 0) {
				getExList();
			}
			JSONObject jsobject = new JSONObject();
			jsobject.put("alias", des.encrypt(a));
			jsobject.put("username", des.encrypt(u));
			jsobject.put("password", des.encrypt(p));
			jsobject.put("uuid", des.encrypt(uuid));
			jsobject.put("url", des.encrypt(a.substring(0, 1).toLowerCase()));

			jsarray.put(jsobject);
			SharedPreferences.Editor e = settings.edit();
			JSONObject jso = new JSONObject();
			jso.put("entrylist", jsarray);
			e.putString(SavedData.SAVED_DATA, jso.toString());
			e.commit();
			startActivity(new Intent(getApplicationContext(), ListAllData.class));
		}
	}

	/**
	 * nabs collector
	 */
	public void getExList() {
		String listdata = settings.getString(SavedData.SAVED_DATA, "");
		jsarray = jshandle.getEntryList(listdata);
		if (jsarray == null) {
			Toast.makeText(getApplicationContext(), "Nothing :(((.",
					Toast.LENGTH_SHORT).show();
			jsarray = new JSONArray();
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
		case R.id.listall_menu:
			startActivity(new Intent(getApplicationContext(), ListAllData.class));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
