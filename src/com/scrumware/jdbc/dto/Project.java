package com.scrumware.jdbc.dto;

import java.sql.Date;

import org.json.JSONObject;

import com.scrumware.config.Constants;

public class Project implements IJsonObject {
	//Base Variables
	private int projectId;
	private String name;
	private String description;
	private int projectManagerId;
	private Date startDate;
	private Date endDate;
	private int statusId;
	private Date createdOn;
	private Date updatedOn;
	private int createdBy;
	private int updatedBy;
	
	//Empty Constructor
	public Project() {
		
	}
	
	//JSON objects
	public JSONObject toJSON() {
		JSONObject json =  new JSONObject();
		json.put(Constants.PROJECT_ID, this.projectId);
		json.put(Constants.UPDATED, this.updatedOn);
		json.put(Constants.UPDATED_BY, this.updatedBy);
		json.put(Constants.PROJECT_NAME, this.name);
		json.put(Constants.DESCRIPTION, this.description);
		json.put(Constants.PROJECT_MANAGER, this.projectManagerId);
		json.put(Constants.PLANNED_START_DATE, this.startDate);
		json.put(Constants.PLANNED_END_DATE, this.endDate);
		json.put(Constants.STATUS, this.statusId);
		return json;
	}

	public void updateFromJSON(JSONObject json) {
		projectId = json.getInt(Constants.PROJECT_ID);
		name = json.getString(Constants.PROJECT_NAME);
		description = json.getString(Constants.DESCRIPTION);
		projectManagerId = json.getInt(Constants.PROJECT_MANAGER);
		//startDate = json.;
		//endDate = json.;
		statusId = json.getInt(Constants.STATUS);
		//createdOn = json.;
		//updatedOn = json.;
		createdBy = json.getInt(Constants.CREATED_BY);
		updatedBy = json.getInt(Constants.UPDATED_BY);
	}
	
	//Get Values
	public int getProjectID() {
		return this.projectId;
	}
	
	public Date getCreated() {
		return this.createdOn;
	}
	
	public int getCreatedBy() {
		return this.createdBy;
	}
	
	public Date getUpdated() {
		return this.updatedOn;
	}
	
	public int getUpdatedBy() {
		return this.updatedBy;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int getPM() {
		return this.projectManagerId;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	
	public int getStatus() {
		return this.statusId;
	}
	
	//Set Values
	public void setProjectID(int project) {
		this.projectId = project;
	}
	
	public void setCreated(Date create) {
		this.createdOn = create;
	}
	
	public void setCreatedBy(int createdby) {
		this.createdBy = createdby;
	}
	
	public void setUpdated(Date update) {
		this.updatedOn = update;
	}
	
	public void setUpdatedBy(int updatedby) {
		this.updatedBy = updatedby;
	}
	
	public void setName(String projName) {
		this.name = projName;
	}
	
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	public void setPM(int projMgr) {
		this.projectManagerId = projMgr;
	}
	
	public void setStartDate(Date start) {
		this.startDate = start;
	}
	
	public void setEndDate(Date end) {
		this.endDate = end;
	}
	
	public void setStatus(int status) {
		this.statusId = status;
	}
}

