package com.scrumware.jdbc.dto;

import java.io.Serializable;
import java.util.ArrayList;

import javax.naming.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import com.scrumware.config.Constants;

public class Task implements IJsonObject, Serializable {

	private static final long serialVersionUID = 5699379609889874177L;
	
	private int taskId;
	private int assignedTo;
	private int storyId;
	private ArrayList<Integer> dependentTaskList;
	
	private String name;
	private int statusId;
	private String workNotes;
	private String description;
	
	public Task() {
		dependentTaskList = new ArrayList<Integer>();
	}
	
	public Task(JSONObject json) {
		dependentTaskList = new ArrayList<Integer>();
		updateFromJSON(json);
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject json =  new JSONObject();
		json.put(Constants.TASK_ID, taskId);
		json.put(Constants.ASSIGNED_TO, assignedTo);
		json.put(Constants.DEPENDS_ON, dependencyArray());
		json.put(Constants.TASK_NAME, name);
		json.put(Constants.STATUS_ID, statusId);
		json.put(Constants.WORK_NOTES, workNotes);
		json.put(Constants.DESCRIPTION, description);
		return json;
	}

	@Override
	public void updateFromJSON(JSONObject json) {
		taskId = json.getInt(Constants.USER_ID);
		assignedTo = json.getInt(Constants.ASSIGNED_TO);
		storyId = json.getInt(Constants.STORY_ID);
		dependentTaskList = dependencyArray(json.getJSONArray(Constants.DEPENDS_ON));
		name = json.getString(Constants.TASK_NAME);
		statusId = json.getInt(Constants.STATUS_ID);
		workNotes = json.getString(Constants.WORK_NOTES);
		description = json.getString(Constants.DESCRIPTION);
	}
	
	private JSONArray dependencyArray() {
		JSONArray jsonArray = new JSONArray();
		for (Integer id : dependentTaskList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(Constants.TASK_ID, id);
			jsonArray.put(jsonObject);
		}
		
		return jsonArray;
	}
	
	private ArrayList<Integer> dependencyArray(JSONArray jsonArray) {
		ArrayList<Integer> taskList = new ArrayList<Integer>();
		for (int i = 0; i < jsonArray.length(); i++) {
			taskList.add(jsonArray.getJSONObject(i).getInt(Constants.TASK_ID));
		}
		return taskList;
	}
	
	public int getTaskId() {
		return taskId;
	}
	
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(int assignedTo) {
		this.assignedTo = assignedTo;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	public String getWorkNotes() {
		return workNotes;
	}

	public void setWorkNotes(String workNotes) {
		this.workNotes = workNotes;
	}

	public int getStoryId() {
		return storyId;
	}

	public void setStoryId(int storyId) {
		this.storyId = storyId;
	}
	

	public ArrayList<Integer> getDependentTaskList() {
		return dependentTaskList;
	}

	public void setDependentTaskList(ArrayList<Integer> dependentTaskList) {
		this.dependentTaskList.clear();
		this.dependentTaskList.addAll(dependentTaskList);
	}

	public int getDependentCount() {
		return dependentTaskList.size();
	}
	
	public String toString() {
		return "TaskID: " + taskId + " Name: " + name + " Description: " + description;
	}
}
