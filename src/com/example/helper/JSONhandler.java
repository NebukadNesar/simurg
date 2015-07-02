package com.example.helper;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.model.Nabs;

/**
 * 
 * @author tavatr
 * 
 */
public class JSONhandler {

	ArrayList<Nabs> nabs = null;
	DESedeEncryption des;

	/**
	 * def cons
	 */
	public JSONhandler() {
	}

	/**
	 * 
	 * @return
	 */
	public JSONArray getEntryList(String listdata) {
		System.out.println("listdata: " + listdata);
		if (!listdata.equals("")) {
			try {
				JSONObject jsnobject = new JSONObject(listdata);
				System.out.println("jsnobject: " + jsnobject);
				return jsnobject.getJSONArray("entrylist");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Nabs> fillNabsList(String listdata) {
		nabs = new ArrayList<Nabs>();
		JSONArray ja = getEntryList(listdata);
		Nabs nab = null;

		if (ja != null)
			try {
				des = new DESedeEncryption();
				for (int i = 0; i < ja.length(); i++) {
					nab = new Nabs();
					nab.setUsername(des.decrypt(ja.getJSONObject(i).getString(
							"username")));
					nab.setPassswd(des.decrypt(ja.getJSONObject(i).getString(
							"password")));
					nab.setAlies(des.decrypt(ja.getJSONObject(i).getString(
							"alias")));
					nab.setId(des
							.decrypt(ja.getJSONObject(i).getString("uuid")));
					nab.setUrl(des
							.decrypt(ja.getJSONObject(i).getString("url")));
					nabs.add(nab);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		return nabs;
	}

	/**
	 * 
	 * @param nabs
	 * @return
	 */
	public JSONArray getJsonArray(ArrayList<Nabs> nabs) {
		try {
			JSONArray jArray = new JSONArray();
			for (Nabs nab : nabs) {
				JSONObject jso = new JSONObject();
				jso.put("username", des.encrypt(nab.getUsername()));
				jso.put("password", des.encrypt(nab.getPassswd()));
				jso.put("alias", des.encrypt(nab.getAlies()));
				jso.put("uuid", des.encrypt(nab.getId()));
				jso.put("url", des.encrypt(nab.getUrl()));

				jArray.put(jso);
			}
			return jArray;
		} catch (JSONException jse) {
			jse.printStackTrace();
		}

		return null;
	}

}
