package com.scrumware.story;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

import com.scrumware.config.Constants;

public class Story implements com.scrumware.interfaces.IJsonObject, Serializable{
	
	//base variables
	private int story_id;
	private Date created;
	private int created_by;
	private Date updated;
	private int updated_by;
	private int status_id;
	private int project_id;
	private int sprint_id;
	private int task_count;
	private String story_name;
	private String description;
	private String acceptence_criteria;

	//contructor
	public Story(){
		
	}

	//JSON objects
	@Override
	public JSONObject toJSON() {
		JSONObject json =  new JSONObject();
		json.put(Constants.STORY_ID, this.story_id);
		json.put(Constants.CREATED, this.created);
		json.put(Constants.CREATED_BY, this.created_by);
		json.put(Constants.UPDATED, this.updated);
		json.put(Constants.UPDATED_BY, this.updated_by);
		json.put(Constants.STATUS_ID, this.status_id);
		json.put(Constants.PROJECT_ID, this.project_id);
		json.put(Constants.SPRINT_ID, this.status_id);
		json.put(Constants.TASK_COUNT, this.task_count);
		json.put(Constants.STORY_NAME, this.story_name);
		json.put(Constants.DESCRIPTION, this.description);
		json.put(Constants.ACCEPTENCE_CRITERIA, this.acceptence_criteria);
		return json;
	}
	
	public void updateFromJSON(JSONObject json) {
		status_id = json.getInt(Constants.STATUS_ID);
		task_count = json.getInt(Constants.TASK_COUNT);
		story_name = json.getString(Constants.STORY_NAME);
		description = json.getString(Constants.DESCRIPTION);
		acceptence_criteria = json.getString(Constants.ACCEPTENCE_CRITERIA);
	}
	//getters
	public int getStoryID(){
		return story_id;
	}
	public Date getCreated(){
		return created;
	}
	public int getCreatedBy(){
		return created_by;
	}
	public Date getUpdated(){
		return updated;
	}
	public int getUpdatedBy(){
		return updated_by;
	}
	public int getStatusId(){
		return status_id;
	}
	public int getProjectId(){
		return project_id;
	}
	public int getSprintId(){
		return sprint_id;
	}
	public int getTaskCount(){
		return task_count;
	}
	public String getStoryName(){
		return story_name;
	}
	public String getDescription(){
		return description;
	}
	public String getAcceptenceCriteria(){
		return acceptence_criteria;
	}
	
	//setters

	public void setUpdated(Date date){
		this.updated = date;
	}
	public void setUpdatedBy(int user){
		this.updated_by = user;
	}
	public void setStatusId(int status){
		this.status_id = status;
	}

	public void setSprintId(int sprint){
		this.sprint_id = sprint;
	}
	public void setTaskCount(int tasks){
		this.task_count = tasks;
	}
	public void setStoryName(String name){
		this.story_name = name;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public void setAcceptenceCriteria(String criteria){
		this.acceptence_criteria = criteria;
	}
	

}
