package com.scrumware.helpers;

import com.scrumware.jdbc.dto.Task;

public class DependencyHelper {
	/**
	 * deleteTaskWithDependencies(Task task)
	 * @param task
	 * @return true if task was successfully deleted, else false.
	 */
	public static boolean deleteTaskWithDependencies(Task task) {
		//TODO: 
		// Get a list of the tasks that depend on this task.
		// See what dependencies need to be updated after deletion. 
		// Delete task. 
		// Check that dependencies make sense.
		return false;
	}
	
	/**
	 * 
	 * @param task
	 * @return
	 */
	public static boolean closeTask(Task task) {
		return false;
	}

}
