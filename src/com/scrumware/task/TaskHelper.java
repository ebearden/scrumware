package com.scrumware.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.scrumware.config.Status;
import com.scrumware.jdbc.ConnectionPool;
import com.scrumware.jdbc.DButil;

public class TaskHelper {
	
	public static final String DEPENDENT_ON = "dependent_on"; 
	public static final String DEPENDED_ON = "depended_on";

	
	public static boolean deleteTaskWithDependencies(int taskId) {
		//TODO: 
		// Get a list of the tasks that depend on this task.
		Map<String, Set<Integer>> map = getDependencies(taskId);
		
		// See what dependencies need to be updated after deletion. 
		// Delete task. 
		// Check that dependencies make sense.
		return false;
	}
	
	public static boolean closeTask(int taskId) {
		Map<String, Set<Integer>> map = getDependencies(taskId);
		
		if (map.get(DEPENDENT_ON).size() > 0) {
			// Can't close the task, it has a dependent task not completed
			return false;
		}
		if (map.get(DEPENDENT_ON).size() == 0 && map.get(DEPENDED_ON).size() == 0) {
			// We can close the task, isn't dependent on any tasks or depended on by any tasks.
			return true;
		}
		if (map.get(DEPENDED_ON).size() > 0) {
			// Remove dependencies for the task.
			for (Integer id : map.get(DEPENDED_ON)) {
				if (!removeDependencies(id, taskId)) {
					return false;
				}
			}
			return true;
		}
		
		return false;
	}
	
	private static boolean removeDependencies(int taskId, int dependsOnId) {
		String sql = "UPDATE Task_Dependencies SET active=0 WHERE task_id=? AND depends_on=?;";

		Connection con = ConnectionPool.getInstance().getConnection();
		PreparedStatement stmt = null;
		boolean success = false;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, taskId);
			stmt.setInt(2, dependsOnId);
			int result = stmt.executeUpdate();
			if (result == 1) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(con);
			DButil.closePreparedStatement(stmt);
		}

		return success;
	}
	
	private static boolean changeStatus(int taskId, Status status) {
		String sql = "UPDATE Task SET status_id=? WHERE task_id=?;";
		
		Connection con = ConnectionPool.getInstance().getConnection();
		boolean success = false;
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, status.getCode());
			stmt.setInt(2, taskId);
			int result = stmt.executeUpdate();
			if (result == 1) {
				success = true;
				System.out.println("Status" + success);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(con);
		}

		return success;
	}
	
	/**
	 * 
	 * Return a map of surrounding dependencies for a task. 
	 * @param taskId - the task id
	 * @return dependency map.
	 */
	public static Map<String, Set<Integer>> getDependencies(int taskId) {
		String dependentOnSQL = "SELECT depends_on FROM Task_Dependencies WHERE task_id=? AND active=1";
		String dependsOnSQL = "SELECT task_id FROM Task_Dependencies WHERE depends_on=? AND active=1";
		
		HashMap<String, Set<Integer>> map = new HashMap<String, Set<Integer>>();
		
		Connection con = ConnectionPool.getInstance().getConnection();
		PreparedStatement dependentOnStatement = null;
		PreparedStatement dependedOnStatement = null;
		ResultSet dependentOnResultSet = null;
		ResultSet dependedOnResultSet = null;
		
		try {
			dependentOnStatement = con.prepareStatement(dependentOnSQL);
			dependentOnStatement.setInt(1, taskId);
			
			dependentOnResultSet = dependentOnStatement.executeQuery();
			
			Set<Integer> dependentOnSet = new HashSet<Integer>();
			Set<Integer> dependedOnSet = new HashSet<Integer>();
			
			while (dependentOnResultSet.next()) {
				dependentOnSet.add(dependentOnResultSet.getInt(1));
			}
			map.put(DEPENDENT_ON, dependentOnSet);
			
			dependedOnStatement = con.prepareStatement(dependsOnSQL);
			dependedOnStatement.setInt(1, taskId);
			
			dependedOnResultSet = dependedOnStatement.executeQuery();
			
			while (dependedOnResultSet.next()) {
				dependedOnSet.add(dependedOnResultSet.getInt(1));
			}
			map.put(DEPENDED_ON, dependedOnSet);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(con);
			DButil.closePreparedStatement(dependentOnStatement);
			DButil.closePreparedStatement(dependedOnStatement);
			DButil.closeResultSet(dependentOnResultSet);
			DButil.closeResultSet(dependedOnResultSet);
		}
		return map;
	}

}
