package com.example.simurg;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.helper.JSONhandler;
import com.example.helper.SimpleGestureFilter;
import com.example.helper.SimpleGestureFilter.SimpleGestureListener;
import com.example.model.Nabs;
import com.example.model.SavedData;

/**
 * 
 * @author tavatr
 * 
 */
public class Details extends Activity implements SimpleGestureListener {

	private SimpleGestureFilter detector;
	private EditText d_pword, d_username, d_alias, d_id;
	// private OnSwipeTouchListener onSwipeTouchListener;
	private JSONhandler jshandle;
	private SharedPreferences settings;
	private ViewFlipper viewFlipper;
	private Animation r2l, l2r;
//	private DESedeEncryption des;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simurg_entry_details);
		try {
			initParms();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * init parms...
	 * 
	 * @throws Exception
	 */
	private void initParms() throws Exception {
		Bundle extras = getIntent().getExtras();
		d_alias = (EditText) findViewById(R.id.d_alias);
		d_username = (EditText) findViewById(R.id.d_username);
		d_pword = (EditText) findViewById(R.id.d_pword);
		d_id = (EditText) findViewById(R.id.d_id);

		d_alias.setText(extras.getString("alias"));
		d_username.setText(extras.getString("username"));
		d_pword.setText(extras.getString("password"));
		d_id.setText(extras.getString("uuid"));

		settings = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		jshandle = new JSONhandler();

		detector = new SimpleGestureFilter(this, this);

		viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);

		r2l = AnimationUtils.loadAnimation(this, R.anim.swipe_left_anm);
		l2r = AnimationUtils.loadAnimation(this, R.anim.swipe_right_anm);
//		des = new DESedeEncryption();
		viewFlipper.setInAnimation(r2l);
		viewFlipper.setOutAnimation(l2r);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	private void deleteEntry() {

		JSONArray jsarray = new JSONArray();
		ArrayList<Nabs> nabs = new ArrayList<Nabs>();
		ArrayList<Nabs> nabs_after = new ArrayList<Nabs>();
		String listdata = settings.getString(SavedData.SAVED_DATA, "");
		nabs = jshandle.fillNabsList(listdata);
		String id = d_id.getText().toString();
		SharedPreferences.Editor e = settings.edit();
		try {
			for (int i = 0; i < nabs.size(); i++) {
				if (nabs.get(i).getId().equals(id)) {
					continue;
				}
				nabs_after.add(nabs.get(i));
			}

			jsarray = jshandle.getJsonArray(nabs_after);
			JSONObject jso = new JSONObject();
			jso.put("entrylist", jsarray);
			e.putString(SavedData.SAVED_DATA, jso.toString());
			e.commit();
			startActivity(new Intent(getApplicationContext(), ListAllData.class));

		} catch (Exception xx) {
			Toast.makeText(getApplicationContext(), ":-((( ",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void editEntry() {

		Toast.makeText(getApplicationContext(), "Edited.", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onSwipe(int direction) {
		switch (direction) {

		// get help swipes
		case SimpleGestureFilter.SWIPE_RIGHT:
			viewFlipper.showNext();
			break;

		// get help swipes
		case SimpleGestureFilter.SWIPE_LEFT:
			viewFlipper.showPrevious();
			break;

		// delete entry swipe
		case SimpleGestureFilter.SWIPE_UP:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Do u want to delete?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									deleteEntry();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
								}
							});
			builder.create().show();
			break;

		// edit swipe
		case SimpleGestureFilter.SWIPE_DOWN:
			editEntry();
			break;

		}

	}

	@Override
	public void onDoubleTap() {
		Toast.makeText(this, "Just swipe, no double tap.", Toast.LENGTH_SHORT)
				.show();

	}
}
