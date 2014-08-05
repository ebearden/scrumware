package com.scrumware.sprint;

import java.sql.Date;
import java.text.DateFormat;

import org.json.JSONObject;

import com.scrumware.config.Constants;
import com.scrumware.interfaces.IJsonObject;

public class Sprint implements IJsonObject{
	//Base Variables
	
	private Integer sprintId;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private int statusId;
	private Date createdOn;
	private Date updatedOn;
	private int createdBy;
	private int updatedBy;
	private int projectId;
	
	//Empty Constructor
	public Sprint() {
		
	}
	
	
	//JSON objects
	@Override
	public JSONObject toJSON() {
		JSONObject json =  new JSONObject();
		json.put(Constants.SPRINT_ID, this.sprintId);
		json.put(Constants.CREATED, this.createdOn);
		json.put(Constants.CREATED_BY, this.createdBy);
		json.put(Constants.UPDATED, this.updatedOn);
		json.put(Constants.UPDATED_BY, this.updatedBy);
		json.put(Constants.SPRINT_NAME, this.name);
		json.put(Constants.DESCRIPTION, this.description);
		json.put(Constants.START_DATE, this.startDate);
		json.put(Constants.END_DATE, this.endDate);
		json.put(Constants.STATUS, this.statusId);
		json.put(Constants.PROJECT_ID, this.projectId);
		return json;
		
		
	}

	@Override
	public void updateFromJSON(JSONObject json) {
		sprintId = json.getInt(Constants.SPRINT_ID);
		name = json.getString(Constants.SPRINT_NAME);
		description = json.getString(Constants.DESCRIPTION);
		startDate = Date.valueOf(json.getString(Constants.START_DATE));
		endDate = Date.valueOf(json.getString(Constants.END_DATE));
		statusId = json.getInt(Constants.STATUS);
		createdBy = json.getInt(Constants.CREATED_BY);
		updatedBy = json.getInt(Constants.UPDATED_BY);
		projectId = json.getInt(Constants.PROJECT_ID);
	}
	
	//Get Values
		
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
	
	public int getStatusId() {
		return statusId;
	}
	
	public Integer getSprintId() {
		return sprintId;
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
	
	public String getCreatedOnDateAsString() {
		return DateFormat.getDateTimeInstance().format(createdOn);
	}
	
	public String getUpdatedOnDateAsString() {
		return DateFormat.getDateTimeInstance().format(updatedOn);
	}
	
	public int getProjectId(){
		return this.projectId;
	}
	
	//Setters
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
		this.statusId = Status;
	}
	
	public void setSprintId(int SprintID) {
		this.sprintId = SprintID;
	}
	
	public void setCreatedBy(int createdBY){
		this.createdBy = createdBY;
	}
	
	public void setCreated(Date created){
		this.createdOn = created;
	}
	
	public void setUpdatedBy (int updatedBY){
		this.updatedBy = updatedBY;
	}
	
	public void setUpdated(Date updated){
		this.updatedOn = updated;
	}
	
	public void setProjectId(int projectID){
		this.projectId = projectID;
	}
	
	
	
}
