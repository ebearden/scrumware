package com.scrumware.jdbc.dto;

import java.io.Serializable;

import org.json.JSONObject;

import com.scrumware.config.Constants;

public class User implements IJsonObject, Serializable {
	private int userId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private int role;
	private Boolean active;
	
	public User() {
		
	}
	
	public User(JSONObject json) {
		updateFromJSON(json);
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject json =  new JSONObject();
		json.put(Constants.USER_ID, userId);
		json.put(Constants.USERNAME, username);
		json.put(Constants.FIRST_NAME, firstName);
		json.put(Constants.LAST_NAME, lastName);
		json.put(Constants.EMAIL_ADDRESS, emailAddress);
		json.put(Constants.ROLE, role);
		json.put(Constants.ACTIVE, active);
		
		return json;
	}

	@Override
	public void updateFromJSON(JSONObject json) {
		userId = json.getInt(Constants.USER_ID);
		firstName = json.getString(Constants.USERNAME);
		lastName = json.getString(Constants.LAST_NAME);
		emailAddress = json.getString(Constants.EMAIL_ADDRESS);
		role = json.getInt(Constants.ROLE);
		active = json.getBoolean(Constants.ACTIVE);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	
}
