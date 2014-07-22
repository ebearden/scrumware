package com.scrumware.task;

/**
 * Task Database Access Object
 * @author Elvin Bearden
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scrumware.config.Constants;
import com.scrumware.jdbc.ConnectionPool;
import com.scrumware.jdbc.DButil;

/**
 * TaskDB -
 * Class to access Retrieve, Insert, Update, and Delete from the Database.
 *  
 * @author Elvin Bearden
 *
 */
public class TaskDB {
	/**
	 * Get a task by Id.
	 * @param taskId - The tasks Id.
	 * @return a new Task object.
	 */
	public static Task getTask(int taskId) {
		return getTaskListForIdType(Constants.TASK_ID, taskId).get(0);
	}
	
	/**
	 * Get all tasks. Potentially way too many.
	 * @return List of all Tasks.
	 */
	public static ArrayList<Task> getAllTasks() {
		return getTaskListForIdType(null, null);
	}
	
	/**
	 * Get all tasks related to a story.
	 * @param storyId - The stories Id.
	 * @return a list of new task objects for the story.
	 */
	public static ArrayList<Task> getAllTasksForStory(int storyId) {
		return getTaskListForIdType(Constants.STORY_ID, storyId);
	}
	
	/**
	 * Get all tasks for a user.
	 * @param userId - the Users Id.
	 * @return List of tasks for the user.
	 */
	public static ArrayList<Task> getAllTasksForUserId(int userId) {
		return getTaskListForIdType(Constants.ASSIGNED_TO, userId);
	}
	
	/**
	 * Save a task, creates a new task if no Id exists.
	 * @param task - the Task to save.
	 * @return the saved Task. 
	 */
	public static Task saveTask(Task task) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		String taskSQL;
		boolean isUpdate = false;
		
		if (task.getTaskId() != null) {
			isUpdate = true;
			taskSQL = "UPDATE Task SET task_name=?, description=?, assigned_to=?, "
					+ "status_id=?, work_notes=?, story_id=?, dependent_count=?,  "
					+ "updated=NOW(), updated_by=? "
					+ "WHERE task_id=?;";
					
		}
		else {
			taskSQL = "INSERT INTO Task(task_name, description, assigned_to, status_id, "
					+ "work_notes, story_id, dependent_count, created_by, updated_by) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		}
		
		PreparedStatement taskStatement = null;
		ResultSet generatedKeyResultSet = null;
		
		try {
			taskStatement = connection.prepareStatement(taskSQL, Statement.RETURN_GENERATED_KEYS);
			
			taskStatement.setString(1, task.getName());
			taskStatement.setString(2, task.getDescription());
			taskStatement.setInt(3, task.getAssignedTo());
			taskStatement.setInt(4, task.getStatusId());
			taskStatement.setString(5, task.getWorkNotes());
			taskStatement.setInt(6, task.getStoryId());
			taskStatement.setInt(7, task.getDependentCount());
			
			if (isUpdate) {
				taskStatement.setInt(8, task.getUpdatedBy());
				taskStatement.setInt(9, task.getTaskId());
			} else {
				taskStatement.setInt(8, task.getCreatedBy());
				taskStatement.setInt(9, task.getUpdatedBy());
			}

			
			int result = taskStatement.executeUpdate();
			if (result == 1) {
				generatedKeyResultSet = taskStatement.getGeneratedKeys();
				if (generatedKeyResultSet.next()) {
					task.setTaskId(generatedKeyResultSet.getInt(1));
				}

				for (int i = 0; i < task.getDependentTaskMap().size(); i++) {
					saveDependency(task.getDependentTaskMap(), task.getTaskId());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
			DButil.closeResultSet(generatedKeyResultSet);
			DButil.closePreparedStatement(taskStatement);
		}
		return getTask(task.getTaskId());
	}
	
	/**
	 * Save a list of tasks.
	 * @param taskList - the list of tasks.
	 */
	public static void saveTasks(List<Task> taskList) {
		for (Task t : taskList) {
			 saveTask(t);
		}
	}
	
	/**
	 * Save a dependency for a task.
	 * @param map - The map of dependencies.
	 * @param taskId - The id for the task.
	 * @return true if saved, else false.
	 */
	private static boolean saveDependency(Map<Integer, ArrayList<Integer>> map, Integer taskId) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		ArrayList<Integer> list = new ArrayList<Integer>();
		//TODO: This seems a little risky.
		list.addAll((ArrayList<Integer>)map.values().toArray()[0]);
				
		String sql;
		boolean success = false, isUpdate = false;
		if (map.keySet().size() < 1) {
			isUpdate = true;
			sql = "UPDATE Task_Dependencies "
					+ "SET task_id=?, depends_on=?, active=? "
					+ "WHERE dependency_id=?;";
		}
		else {
			sql = "INSERT INTO Task_Dependencies "
					+ "(task_id, depends_on, active) "
					+ "VALUES(?, ?, ?);";
		}
		
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, taskId);
			statement.setInt(2, list.get(0));
			statement.setInt(3, list.get(1));
			if (isUpdate) {
				statement.setInt(4, (Integer)map.keySet().toArray()[0]);
			}
			if (statement.executeUpdate() == 1) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
			DButil.closePreparedStatement(statement);
		}
		
		return success;
	}
	
	/**
	 * Get a list of tasks for an Id and Id type (User Id, Story Id, etc.) 
	 * @param type - The id type.
	 * @param id - The user id.
	 * @return A list of tasks.
	 */
	private static ArrayList<Task> getTaskListForIdType(String type, Integer id) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		ArrayList<Task> taskList = new ArrayList<Task>();

		Task task = null;
		String sql;
		if (id == null) {
			sql = "SELECT task_id, created, created_by, updated, updated_by, task_name, description, "
					+ "assigned_to, status_id, work_notes, story_id, dependent_count "
					+ "FROM Task;";
		}
		else {
			sql = "SELECT task_id, created, created_by, updated, updated_by, task_name, description, "
					+ "assigned_to, status_id, work_notes, story_id, dependent_count "
					+ "FROM Task WHERE " + type + "=?;";
		}
		
		PreparedStatement taskStatement = null;
		PreparedStatement dependencyStatement = null;
		ResultSet taskResultSet = null;
		ResultSet dependencyResultSet = null;
		
		try {
			taskStatement = connection.prepareStatement(sql);
			dependencyStatement = connection.prepareStatement(
					"SELECT dependency_id, depends_on, active FROM Task_Dependencies WHERE task_id=? AND active=1;"
					);
			
			if (id != null) {
				taskStatement.setInt(1, id);
			}
			taskResultSet = taskStatement.executeQuery();
			
			while (taskResultSet.next()) {
				Map<Integer, ArrayList<Integer>> dependencyMap = new HashMap<Integer, ArrayList<Integer>>();
				task = new Task();
				task.setTaskId(taskResultSet.getInt(1));
				task.setCreatedOn(taskResultSet.getDate(2));
				task.setCreatedBy(taskResultSet.getInt(3));
				task.setUpdatedOn(taskResultSet.getDate(4));
				task.setUpdatedBy(taskResultSet.getInt(5));
				task.setName(taskResultSet.getString(6));
				task.setDescription(taskResultSet.getString(7));
				task.setAssignedTo(taskResultSet.getInt(8));
				task.setStatusId(taskResultSet.getInt(9));
				task.setWorkNotes(taskResultSet.getString(10));
				task.setStoryId(taskResultSet.getInt(11));
				task.setDependentCount(taskResultSet.getInt(12));
				dependencyStatement.setInt(1, task.getTaskId());
				dependencyResultSet = dependencyStatement.executeQuery();
				while (dependencyResultSet.next()) {
					dependencyMap.put(
							dependencyResultSet.getInt(1), 
							new ArrayList<Integer>(Arrays.asList(
									dependencyResultSet.getInt(2), 
									dependencyResultSet.getInt(3))
									)
							);
				}
				task.setDependentTaskMap(dependencyMap);
				taskList.add(task);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
			DButil.closeResultSet(taskResultSet);
			DButil.closeResultSet(dependencyResultSet);
			DButil.closePreparedStatement(taskStatement);
			DButil.closePreparedStatement(dependencyStatement);
			
		}

		return taskList;
	}
	
	/**
	 * Delete a Task.
	 * @param task - The task to delete.
	 * @return True if successful, else false.
	 */
	public static boolean deleteTask(Task task) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement statement = null;
		String sql = "DELETE FROM Task WHERE task_id=?";
		boolean success = false;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, task.getTaskId());
			if (statement.executeUpdate() == 1) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
			DButil.closePreparedStatement(statement);
		}
		return success;
	}
	
	public static boolean deleteDependency(int taskId, int dependsOn) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement statement = null;
		String sql = "DELETE FROM Task_Dependencies WHERE task_id=? AND depends_on=?;";
		boolean success = false;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, taskId);
			statement.setInt(2, dependsOn);
			if (statement.executeUpdate() == 1) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
			DButil.closePreparedStatement(statement);
		}
		return success;
	}

}
