package com.scrumware.jdbc.dto;

import java.util.Date;

import org.json.JSONObject;

import com.scrumware.config.Constants;

public class Sprint /**implements IJsonObject**/ {
	//Base Variables
	private int sysId;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private int status;
	private int projectId;
	
	//Empty Constructor
	public Sprint() {
		
	}
	
	/**
	//JSON objects
	@Override
	public JSONObject toJSON() {
		JSONObject json =  new JSONObject();
		json.put(Constants.SYS_ID, sysId);
		json.put(Constants.NAME, name);
		json.put(Constants.DESCRIPTION, description);
		json.put(Constants.START_DATE, startDate);
		json.put(Constants.END_DATE, endDate);
		json.put(Constants.STATUS, status);
		json.put(Constants.PROJECT_ID, projectId);
		
		return json;
	}

	@Override
	public void updateFromJSON(JSONObject json) {
		sysId = json.getInt(Constants.SYS_ID);
		name = json.getString(Constants.USERNAME);
		description = json.getString(Constants.LAST_NAME);
		startDate = json.
		endDate = json.getdDate(Constants.ROLE);
		status = json.getBoolean(Constants.ACTIVE);
		status = json.getBoolean(Constants.ACTIVE);
	}
	**/
	//Get Values
	public int getSysID() {
		return sysId;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public int getStatus() {
		return status;
	}
	
	public int getProjectID() {
		return projectId;
	}
	
	//Setters
	public void setSysID(int SysID) {
		this.sysId = SysID;
	}
	
	public void setName(String Name) {
		this.name = Name;
	}
	
	public void setDescription(String Description) {
		this.description = Description;
	}
	
	public void setStartDate(Date StartDate) {
		this.startDate = StartDate;
	}
	
	public void setEndDate(Date EndDate) {
		this.endDate = EndDate;
	}
	
	public void setStatus(int Status) {
		this.status = Status;
	}
	
	public void setProjectID(int ProjectID) {
		this.projectId = ProjectID;
	}
	
}
