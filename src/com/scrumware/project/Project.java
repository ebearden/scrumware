package com.scrumware.project;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;

import org.json.JSONObject;

import com.scrumware.config.Constants;
import com.scrumware.config.Status;
import com.scrumware.interfaces.IJsonObject;

public class Project implements IJsonObject {
	//Base Variables
	private Integer projectId;
	private String name;
	private String description;
	private int projectManagerId;
	private Timestamp startDate;
	private Timestamp endDate;
	private int statusId;
	private Timestamp createdOn;
	private Timestamp updatedOn;
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
		startDate = Timestamp.valueOf(json.getString(Constants.PLANNED_START_DATE));
		endDate = Timestamp.valueOf(json.getString(Constants.PLANNED_END_DATE));
		statusId = json.getInt(Constants.STATUS);
		createdBy = json.getInt(Constants.CREATED_BY);
		updatedBy = json.getInt(Constants.UPDATED_BY);
	}
	
	//Get Values
	public Integer getProjectId() {
		return this.projectId;
	}
	
	public Timestamp getCreated() {
		return this.createdOn;
	}
	
	public int getCreatedBy() {
		return this.createdBy;
	}
	
	public Timestamp getUpdated() {
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
	
	public int getProjectManagerId() {
		return this.projectManagerId;
	}
	
	public Timestamp getStartDate() {
		return this.startDate;
	}
	
	public Timestamp getEndDate() {
		return this.endDate;
	}
	
	public int getStatusId() {
		System.out.println(this.statusId);
		return this.statusId;
	}
	
	public String getStatusAsString() {
		return Status.values()[statusId - 1].getDescription();
	}
	
	public String getCreatedOnDateAsString() {
		return DateFormat.getDateTimeInstance().format(createdOn);
	}
	
	public String getUpdatedOnDateAsString() {
		return DateFormat.getDateTimeInstance().format(updatedOn);
	}
	
	//Set Values
	public void setProjectId(Integer project) {
		this.projectId = project;
	}
	
	public void setCreated(Timestamp create) {
		this.createdOn = create;
	}
	
	public void setCreatedBy(int createdby) {
		this.createdBy = createdby;
	}
	
	public void setUpdated(Timestamp update) {
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
	
	public void setProjectManagerId(int projMgr) {
		this.projectManagerId = projMgr;
	}
	
	public void setStartDate(Timestamp start) {
		this.startDate = start;
	}
	
	public void setEndDate(Timestamp end) {
		this.endDate = end;
	}
	
	public void setStatusId(int status) {
		this.statusId = status;
	}
}

