/**
 * 
 */
package com.scrumware.task;

import com.scrumware.story.Story;
import com.scrumware.user.User;

/**
 * Task with a higher level of detail.
 * Contains objects instead of integer IDs.
 * @author Elvin Bearden
 *
 */
public class DetailedTask {
	private Task task;
	private User assignedTo;
	private Story story;
	
	
	public DetailedTask() {/*Default Constructor*/}
	
	public DetailedTask(Task task) {
		this.task = task;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public User getAssignedToUser() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}
	
	/* Getters from Task. */
	public Integer getTaskId() {
		return task.getTaskId();
	}

	public String getName() {
		return task.getName();
	}
	
	public String getDescription() {
		return task.getDescription();
	}
	
	public String getStatus() {
		return task.getStatusAsString();
	}

}
