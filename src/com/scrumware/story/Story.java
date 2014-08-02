package com.scrumware.story;

import java.io.Serializable;
import java.sql.Date;

import org.json.JSONObject;

import com.scrumware.config.Constants;
import com.scrumware.config.Status;

public class Story implements com.scrumware.interfaces.IJsonObject, Serializable{
	
	//base variables
	private Integer storyID;
	private Date created;
	private int createdBy;
	private Date updated;
	private int updatedBy;
	private int statusID;
	private int projectID;
	private Integer sprintID;
	private int taskCount;
	private String storyName;
	private String description;
	private String acceptenceCriteria;

	//contructor
	public Story(){
		
	}

	//JSON objects
	@Override
	public JSONObject toJSON() {
		JSONObject json =  new JSONObject();
		json.put(Constants.STORY_ID, this.storyID);
		json.put(Constants.CREATED, this.created);
		json.put(Constants.CREATED_BY, this.createdBy);
		json.put(Constants.UPDATED, this.updated);
		json.put(Constants.UPDATED_BY, this.updatedBy);
		json.put(Constants.STATUS_ID, this.statusID);
		json.put(Constants.PROJECT_ID, this.projectID);
		json.put(Constants.SPRINT_ID, this.statusID);
		json.put(Constants.TASK_COUNT, this.taskCount);
		json.put(Constants.STORY_NAME, this.storyName);
		json.put(Constants.DESCRIPTION, this.description);
		json.put(Constants.ACCEPTENCE_CRITERIA, this.acceptenceCriteria);
		return json;
	}
	
	public void updateFromJSON(JSONObject json) {
		statusID = json.getInt(Constants.STATUS_ID);
		taskCount = json.getInt(Constants.TASK_COUNT);
		storyName = json.getString(Constants.STORY_NAME);
		description = json.getString(Constants.DESCRIPTION);
		acceptenceCriteria = json.getString(Constants.ACCEPTENCE_CRITERIA);
	}
	//getters
	public Integer getStoryID(){
		return storyID;
	}
	public Date getCreated(){
		return created;
	}
	public int getCreatedBy(){
		return createdBy;
	}
	public Date getUpdated(){
		return updated;
	}
	public int getUpdatedBy(){
		return updatedBy;
	}
	public int getStatusID(){
		return statusID;
	}
	public int getProjectID(){
		return projectID;
	}
	public Integer getSprintID(){
		return sprintID;
	}
	public int getTaskCount(){
		return taskCount;
	}
	public String getStoryName(){
		return storyName;
	}
	public String getDescription(){
		return description;
	}
	public String getAcceptanceCriteria(){
		return acceptenceCriteria;
	}
	public String getStatusAsString() {
		return Status.values()[statusID - 1].getDescription();
	}
	
	//setters

	public void setUpdated(Date date){
		this.updated = date;
	}
	public void setUpdatedBy(int user){
		this.updatedBy = user;
	}
	public void setStatusId(int status){
		this.statusID = status;
	}

	public void setSprintID(Integer sprint){
		this.sprintID = sprint;
	}
	public void setTaskCount(int tasks){
		this.taskCount = tasks;
	}
	public void setStoryName(String name){
		this.storyName = name;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public void setAcceptanceCriteria(String criteria){
		this.acceptenceCriteria = criteria;
	}

	public void setStoryID(int id) {
		this.storyID = id;
		
	}

	public void setCreated(Date date) {
		this.created = date;
		
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
		
	}

	public void setProjectID(int id) {
		this.projectID = id;
		
	}
	

}
