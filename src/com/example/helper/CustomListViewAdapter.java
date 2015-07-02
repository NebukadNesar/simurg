package com.example.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simurg.R;

public class CustomListViewAdapter extends
		ArrayAdapter<HashMap<String, String>> {

	public static final HashMap<String, Integer> images = new HashMap<String, Integer>();
	static {
		images.put("a", R.drawable.a);
		images.put("b", R.drawable.b);
		images.put("c", R.drawable.c);
		images.put("d", R.drawable.d);
		images.put("e", R.drawable.e);
		images.put("f", R.drawable.f);
		images.put("g", R.drawable.g);
		images.put("h", R.drawable.h);
		images.put("i", R.drawable.i);
		images.put("j", R.drawable.j);
		images.put("k", R.drawable.k);
		images.put("l", R.drawable.l);
		images.put("m", R.drawable.m);
		images.put("n", R.drawable.n);
		images.put("o", R.drawable.o);
		images.put("p", R.drawable.p);
		images.put("r", R.drawable.r);
		images.put("s", R.drawable.s);
		images.put("t", R.drawable.t);
		images.put("u", R.drawable.u);
		images.put("v", R.drawable.v);
		images.put("x", R.drawable.x);
		images.put("y", R.drawable.y);
		images.put("z", R.drawable.z);
		images.put("w", R.drawable.w);
		images.put("q", R.drawable.q);
		images.put("skull", R.drawable.skull);
	}

	public static final ArrayList<String> chars = new ArrayList<String>();
	static {
		chars.add("a");
		chars.add("b");
		chars.add("c");
		chars.add("d");
		chars.add("e");
		chars.add("f");
		chars.add("g");
		chars.add("h");
		chars.add("i");
		chars.add("j");
		chars.add("k");
		chars.add("l");
		chars.add("m");
		chars.add("n");
		chars.add("o");
		chars.add("p");
		chars.add("q");
		chars.add("r");
		chars.add("s");
		chars.add("t");
		chars.add("u");
		chars.add("x");
		chars.add("w");
		chars.add("v");
		chars.add("y");
		chars.add("z");
	};

	Context context;

	public CustomListViewAdapter(Context context, int resourceId,
			List<HashMap<String, String>> items) {
		super(context, resourceId, items);
		this.context = context;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView icon;
		TextView username;
		TextView password;
		TextView uuid;
		TextView alias;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		HashMap<String, String> rowItem = getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.simple_row, null);
			holder = new ViewHolder();
			holder.alias = (TextView) convertView.findViewById(R.id.alias);
			holder.username = (TextView) convertView
					.findViewById(R.id.username);
			holder.password = (TextView) convertView
					.findViewById(R.id.password);
			holder.uuid = (TextView) convertView.findViewById(R.id.uuid);
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);

			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.alias.setText(rowItem.get("alias"));
		holder.username.setText(rowItem.get("username"));
		holder.password.setText(rowItem.get("password"));
		holder.uuid.setText(rowItem.get("uuid"));
		holder.icon.setImageResource(images.get(chars.contains(rowItem.get("url")) ? rowItem.get("url"):"skull"));

		return convertView;
	}
}