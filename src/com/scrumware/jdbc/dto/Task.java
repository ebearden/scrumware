package com.scrumware.jdbc.dto;

import java.util.ArrayList;

import org.json.JSONObject;

public class Task implements IJsonObject {
	private int taskId;
	private User assignedTo;
//	private Story story; <---- TODO:
	private ArrayList<Task> dependentTaskList = new ArrayList<Task>();
	
	private String name;
	private String status;
	private String workNotes;
	private String description;
	
	@Override
	public JSONObject toJSON() {
		return null;
	}

	@Override
	public void updateFromJSON(JSONObject json) {
		// TODO Auto-generated method stub
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

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWorkNotes() {
		return workNotes;
	}

	public void setWorkNotes(String workNotes) {
		this.workNotes = workNotes;
	}

//	public Story getStory() {
//		return story;
//	}
//
//	public void setStoryId(Story story) {
//		this.story = story;
//	}

	public int getDependentCount() {
		return dependentTaskList.size();
	}
	
	public String toString() {
		return "TaskID: " + taskId + " Name: " + name + " Description: " + description;
	}
}
