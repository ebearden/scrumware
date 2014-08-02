package com.scrumware.sprint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.scrumware.config.Constants;
import com.scrumware.jdbc.ConnectionPool;
import com.scrumware.sprint.Sprint;




public class SprintDB {
	/**
	 * Get a sprint by Id.
	 * @param sprintId - The tasks Id.
	 * @return a new Sprint object.
	 */
	public static Sprint getSprint(int sprintId) {
		return getSprintListForIdType(Constants.SPRINT_ID, sprintId).get(0);
	}
	
	/**
	 * Get all sprints. Potentially way too many.
	 * @return List of all sprints.
	 */
	public static ArrayList<Sprint> getAllSprints() {
		return getSprintListForIdType(null, null);
	}

	/**
	 * Get all sprints related to a project.
	 * @param projectId - The project Id.
	 * @return a list of new sprint objects for the project.
	 */
	public static ArrayList<Sprint> getAllSprintsForProject(int projectId) {
		return getSprintListForIdType(Constants.STORY_ID, projectId);
	}
	
	
	
	public static Sprint saveSprint(Sprint sprint) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		String sprintSQL;
		boolean isUpdate = false;
		
		if ((Integer)(sprint.getSprintId()) != null) {
			isUpdate = true;
			sprintSQL = "UPDATE Sprint SET sprint_name=?, description=?, "
					+ "status_id=?, sprint_id=?, "
					+ "updated=NOW(), updated_by=?,project_id=? "
					+ "WHERE sprint_id=?;";
					
		}
		else {
			sprintSQL = "INSERT INTO Project(sprint_name, description, status_id, "
					+ "sprint_id, created_by, updated_by, project_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?);";	
		}
		
		try {
			PreparedStatement sprintStatement = connection.prepareStatement(sprintSQL, Statement.RETURN_GENERATED_KEYS);
			
			sprintStatement.setString(1, sprint.getName());
			sprintStatement.setString(2, sprint.getDescription());
			sprintStatement.setInt(3, sprint.getStatusId());
			sprintStatement.setInt(4, sprint.getSprintId());
			
			if (isUpdate) {
				sprintStatement.setInt(6, sprint.getUpdatedBy());
			} else {
				sprintStatement.setInt(5, sprint.getCreatedBy());
				sprintStatement.setInt(6, sprint.getUpdatedBy());
			}
			sprintStatement.setInt(7, sprint.getProjectId());

			
			int result = sprintStatement.executeUpdate();
			if (result == 1) {
				ResultSet generatedKey = sprintStatement.getGeneratedKeys();
				if (generatedKey.next()) {
					sprint.setSprintId(generatedKey.getInt(1));
					System.out.println(generatedKey.getInt(1));
					System.out.println(generatedKey.getLong(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
		}
		return getSprint(sprint.getSprintId());
	}
	
	/**
	 * Save a list of sprints.
	 * @param sprintList - the list of sprint.
	 */
	public static void saveSprint(ArrayList<Sprint> sprintList) {
		for (Sprint s : sprintList) {
			 saveSprint(s);
		}
	}
	
	private static ArrayList<Sprint> getSprintListForIdType(String type, Integer id) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		ArrayList<Sprint> sprintList = new ArrayList<Sprint>();

		Sprint sprint = null;
		String sql;
		if (id == null) {
			sql = "SELECT sprint_id, created, created_by, updated, updated_by, sprint_name, description, "
					+ "start_date, end_date, status_id, project_id "
					+ "FROM Sprint;";
		}
		else {
			sql = "SELECT sprint_id, created, created_by, updated, updated_by, sprint_name, description, "
					+ "start_date, end_date, status_id, project_id"
					+ "FROM Sprint WHERE " + type + "=?;";
		}
		
		try {
			PreparedStatement sprintStatement = connection.prepareStatement(sql);
			System.out.println(sprintStatement);
			

			
			if (id != null) {
				sprintStatement.setInt(1, id);
			}
			ResultSet sprintResultSet = sprintStatement.executeQuery();
			
			while (sprintResultSet.next()) {
				sprint = new Sprint();
				sprint.setSprintId(sprintResultSet.getInt(1));
				sprint.setCreated(sprintResultSet.getDate(2));
				sprint.setCreatedBy(sprintResultSet.getInt(3));
				sprint.setUpdated(sprintResultSet.getDate(4));
				sprint.setUpdatedBy(sprintResultSet.getInt(5));
				sprint.setName(sprintResultSet.getString(6));
				sprint.setDescription(sprintResultSet.getString(7));
				sprint.setStartDate(sprintResultSet.getDate(8));
				sprint.setEndDate(sprintResultSet.getDate(9));
				sprint.setStatus(sprintResultSet.getInt(10));
				sprint.setProjectId(sprintResultSet.getInt(11));
				
				sprintList.add(sprint);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
		}

		return sprintList;
	}
	
	/**
	 * Delete a Sprint.
	 * @param sprint - The sprint to delete.
	 * @return True if successful, else false.
	 */
	public static boolean deleteSprint(Sprint sprint) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement removeTasksStatement = null;
		PreparedStatement removeFromStoryStatement = null;
		PreparedStatement deleteSprintStatement = null;
		
		String deleteSprintSQL = "DELETE FROM Sprint WHERE sprint_id=?;";
		String removeFromStorySQL = "UPDATE Story SET sprint_id=NULL WHERE sprint_id=?;";
		String removeTasksSQL = "UPDATE Task SET sprint_id=NULL WHERE sprint_id=?;";
		
		boolean success = false;
		
		try {
			connection.setAutoCommit(false);
			
			removeTasksStatement = connection.prepareStatement(removeTasksSQL);
			removeTasksStatement.setInt(1, sprint.getSprintId());
			removeTasksStatement.executeUpdate();
			
			removeFromStoryStatement = connection.prepareStatement(removeFromStorySQL);
			removeFromStoryStatement.setInt(1, sprint.getSprintId());
			removeFromStoryStatement.executeUpdate();
			
			deleteSprintStatement = connection.prepareStatement(deleteSprintSQL);
			deleteSprintStatement.setInt(1, sprint.getSprintId());
			
			if (deleteSprintStatement.executeUpdate() == 1) {
				success = true;
				connection.commit();
				connection.setAutoCommit(true);
			} else {
				connection.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
		}
		return success;
	}
}
