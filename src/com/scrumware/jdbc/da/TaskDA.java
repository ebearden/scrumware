package com.scrumware.jdbc.da;

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
import com.scrumware.jdbc.JDBCHelper;
import com.scrumware.jdbc.dto.Task;

public class TaskDA {
	public Task getTask(int taskId) {
		return getTaskListForIdType(Constants.TASK_ID, taskId).get(0);
	}
	
	public List<Task> getAllTasks() {
		return getTaskListForIdType(null, null);
	}
	
	public List<Task> getAllTasksForStory(int storyId) {
		return getTaskListForIdType(Constants.STORY_ID, storyId);
	}
	
	public List<Task> getAllTasksForUserId(int userId) {
		return getTaskListForIdType(Constants.ASSIGNED_TO, userId);
	}
	
	public boolean saveTask(Task task) {
		Connection connection = JDBCHelper.getConnection();
		String taskSQL;
		boolean success = false, isUpdate = false;
		
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
		
		try {
			PreparedStatement taskStatement = connection.prepareStatement(taskSQL, Statement.RETURN_GENERATED_KEYS);
			
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
				ResultSet generatedKey = taskStatement.getGeneratedKeys();
				if (generatedKey.next()) {
					task.setTaskId(generatedKey.getInt(1));
					System.out.println(generatedKey.getInt(1));
					System.out.println(generatedKey.getLong(1));
				}
				success = true;
				for (int i = 0; i < task.getDependentTaskMap().size(); i++) {
					if (saveDependency(task.getDependentTaskMap(), task.getTaskId())) {
						success = true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCHelper.freeConnection(connection);
		}
		return success;
	}
	
	public boolean saveTasks(List<Task> taskList) {
		boolean success = false;
		for (Task t : taskList) {
			 success = saveTask(t);
		}
		return success;
	}
	
	private boolean saveDependency(Map<Integer, List<Integer>> map, Integer taskId) {
		Connection connection = JDBCHelper.getConnection();
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
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
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
			JDBCHelper.freeConnection(connection);
		}
		
		return success;
	}
	
	private List<Task> getTaskListForIdType(String type, Integer id) {
		Connection connection = JDBCHelper.getConnection();
		List<Task> taskList = new ArrayList<Task>();

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
		
		try {
			PreparedStatement taskStatement = connection.prepareStatement(sql);
			System.out.println(taskStatement);
			
			PreparedStatement dependencyStatement = connection.prepareStatement(
					"SELECT dependency_id, depends_on, active FROM Task_Dependencies WHERE task_id=?;"
					);
			
			if (id != null) {
				taskStatement.setInt(1, id);
			}
			ResultSet taskResultSet = taskStatement.executeQuery();
			ResultSet dependencyResultSet;
			
			while (taskResultSet.next()) {
				Map<Integer, List<Integer>> dependencyMap = new HashMap<Integer, List<Integer>>();
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
			JDBCHelper.freeConnection(connection);
		}

		return taskList;
	}
	
	public boolean deleteTask(Task task) {
		Connection connection = JDBCHelper.getConnection();
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
			JDBCHelper.freeConnection(connection);
		}
		return success;
	}

}
