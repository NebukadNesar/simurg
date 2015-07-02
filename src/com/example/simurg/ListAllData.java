package com.example.simurg;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.helper.*;
import com.example.model.Nabs;
import com.example.model.SavedData;

/**
 * 
 * @author tavatr
 * 
 */
public class ListAllData extends Activity {
	SharedPreferences settings;
	ArrayList<HashMap<String, String>> entryList;
	DESedeEncryption des;
	ListView listview;
	JSONhandler jshandle;
	ArrayList<Nabs> nabs;
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		settings = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		entryList = new ArrayList<HashMap<String, String>>();
		listview = (ListView) findViewById(R.id.activitylist);
		jshandle = new JSONhandler();
		simpleSampleFunc();
	}

	/**
	 * 
	 * @param listdata
	 */
	private void simpleSampleFunc() {
		String listdata = settings.getString(SavedData.SAVED_DATA, "");
		JSONArray jsarray = jshandle.getEntryList(listdata);

		if (jsarray != null && jsarray.length() > 0) {
			try {
				des = new DESedeEncryption();
				HashMap<String, String> map;

				for (int i = 0; i < jsarray.length(); i++) {
					map = new HashMap<String, String>();
					map.put("alias",
							des.decrypt(jsarray.getJSONObject(i).getString(
									"alias")));
					map.put("username",
							des.decrypt(jsarray.getJSONObject(i).getString(
									"username")));
					map.put("password",
							des.decrypt(jsarray.getJSONObject(i).getString(
									"password")));
					map.put("uuid",
							des.decrypt(jsarray.getJSONObject(i).getString(
									"uuid")));
					map.put("url",
							des.decrypt(jsarray.getJSONObject(i).getString(
									"url")));
					entryList.add(map);
				}

				CustomListViewAdapter adapter = new CustomListViewAdapter(this,
						R.layout.simple_row, entryList);

				listview.setAdapter(adapter);

				listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						@SuppressWarnings("unchecked")
						HashMap<String, String> map = (HashMap<String, String>) listview
								.getItemAtPosition(position);

						Intent i = new Intent(getApplicationContext(),
								Details.class);
						i.putExtra("alias", map.get("alias"));
						i.putExtra("username", map.get("username"));
						i.putExtra("password", map.get("password"));
						i.putExtra("uuid", map.get("uuid"));
						startActivity(i);

					}
				});

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getApplicationContext(), "empty :-((((",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void createPdfDoc() {
		GeneratePDF gpdf = new GeneratePDF();
		String listdata = settings.getString(SavedData.SAVED_DATA, "");
		ArrayList<Nabs> arr = jshandle.fillNabsList(listdata);
		if (arr.size() > 0) {
			String file = gpdf.generate(arr);
			if (!file.equals("")) {
				alert.showAlertDialog(ListAllData.this, "Simurg PDF export",
						"Please check your files. "+file, true);
				Toast.makeText(getApplicationContext(), "Check your files. "+file,
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Sorry Couldn't export to a file.", Toast.LENGTH_SHORT)
						.show();
			}
		} else
			Toast.makeText(getApplicationContext(),
					"There is nothing to export", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.addnew_menu:
			startActivity(new Intent(getApplicationContext(), NewEntry.class));
			return true;
		case R.id.create_pdf_doc:
			createPdfDoc();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
