package com.scrumware.task;

/**
 * Task Database Transfer Object
 * @author Elvin Bearden
 */
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.scrumware.config.Constants;
import com.scrumware.config.Status;
import com.scrumware.interfaces.IJsonObject;

public class Task implements IJsonObject, Serializable {

	private static final long serialVersionUID = 5699379609889874177L;
	
	private Integer taskId;
	private int assignedTo;
	private int storyId;
	private Map<Integer, ArrayList<Integer>> dependentTaskMap;
	
	private String name;
	private int statusId;
	private String workNotes;
	private String description;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	private int createdBy;
	private int updatedBy;
	private int dependentCount;
	
	public Task() {
		dependentTaskMap = new HashMap<Integer, ArrayList<Integer>>();
	}
	
	public Task(JSONObject json) {
		dependentTaskMap = new HashMap<Integer, ArrayList<Integer>>();
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
		assignedTo = json.getInt(Constants.ASSIGNED_TO);
		storyId = json.getInt(Constants.STORY_ID);
		dependentTaskMap = dependencyMap(json.getJSONArray(Constants.DEPENDS_ON));
		name = json.getString(Constants.TASK_NAME);
		statusId = json.getInt(Constants.STATUS_ID);
		workNotes = json.getString(Constants.WORK_NOTES);
		description = json.getString(Constants.DESCRIPTION);
	}
	
	private JSONArray dependencyArray() {
		JSONArray jsonArray = new JSONArray();
		for (Integer id : dependentTaskMap.keySet()) {
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(Constants.DEPENDENCY_ID, id);
			jsonObject.put(Constants.TASK_ID, dependentTaskMap.get(id).get(0));
			jsonObject.put(Constants.ACTIVE, dependentTaskMap.get(id).get(1));
			jsonArray.put(jsonObject);
		}
		
		return jsonArray;
	}
	
	private Map<Integer, ArrayList<Integer>> dependencyMap(JSONArray jsonArray) {
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		
		for (int i = 0; i < jsonArray.length(); i++) {
			map.put(
					jsonArray.getJSONObject(i).getInt(Constants.DEPENDENCY_ID), 
					new ArrayList<Integer>(Arrays.asList(
							jsonArray.getJSONObject(i).getInt(Constants.TASK_ID),
							jsonArray.getJSONObject(i).getInt(Constants.ACTIVE)
							)
					));
		}
		return map;
	}
	
	public Integer getTaskId() {
		return taskId;
	}
	
	public void setTaskId(Integer taskId) {
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

	public String getStatusAsString() {
		return Status.values()[statusId - 1].getDescription();
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

	public Map<Integer, ArrayList<Integer>> getDependentTaskMap() {
		return dependentTaskMap;
	}

	public void setDependentTaskMap(Map<Integer, ArrayList<Integer>> dependentTaskMap) {
		this.dependentTaskMap.clear();
		this.dependentTaskMap.putAll(dependentTaskMap);
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}
	
	public String getCreatedOnDateAsString() {
		return DateFormat.getDateTimeInstance().format(createdOn);
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}
	
	public String getUpdatedOnDateAsString() {
		return DateFormat.getDateTimeInstance().format(updatedOn);
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public int getDependentCount() {
		return dependentCount;
	}
	
	public void setDependentCount(int dependentCount) {
		this.dependentCount = dependentCount;
	}

	public String toString() {
		return "TaskID: " + taskId + " Name: " + name + " Description: " + description;
	}
}
