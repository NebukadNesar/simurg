package com.example.model;

public class Nabs {

	private String passswd;
	private String username;
	private String alies;
	private String saveddate;
	private String id;
	private String url;

	public Nabs() {
		// TODO Auto-generated constructor stub
	}

	public Nabs(String username, String pass, String alias) {
		setAlies(alias);
		setPassswd(pass);
		setUsername(username);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSaveddate() {
		return saveddate;
	}

	public void setSaveddate(String saveddate) {
		this.saveddate = saveddate;
	}

	public String getPassswd() {
		return passswd;
	}

	public void setPassswd(String passswd) {
		this.passswd = passswd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAlies() {
		return alies;
	}

	public void setAlies(String alies) {
		this.alies = alies;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}
