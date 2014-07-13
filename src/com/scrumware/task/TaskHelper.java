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

public class TaskHelper {
	
	public static final String DEPENDENT_ON = "dependent_on"; 
	public static final String DEPENDED_ON = "depended_on";

	
	public static boolean deleteTaskWithDependencies(int taskId) {
		//TODO: 
		// Get a list of the tasks that depend on this task.
		// See what dependencies need to be updated after deletion. 
		// Delete task. 
		// Check that dependencies make sense.
		return false;
	}
	
	
	
	public static boolean closeTask(int taskId) {
		Map<String, Set<Integer>> map = getDependencies(taskId);
		
		System.out.println("Size " + map.size());
		
		if (map.get(DEPENDENT_ON).size() > 0) {
			//Can't close, dependent task not completed
			System.out.println("Dependent on a task " + taskId);
			return false;
		}
		if (map.get(DEPENDENT_ON).size() == 0 && map.get(DEPENDED_ON).size() == 0) {
			//Close task, isn't dependent on any tasks or depended on by any tasks.
			System.out.println("Not dependent or depened on a task " + taskId);
			return changeStatus(taskId, Status.DONE);
		}
		if (map.get(DEPENDED_ON).size() > 0) {
			System.out.println("dependend on");
			// Inform tasks that depend on this task that it is closed. Then close task.
			for (Integer id : map.get(DEPENDED_ON)) {
			System.out.println("made it.");
				if (!removeDependencies(id, taskId)) {
					return false;
				}
			}
			return changeStatus(taskId, Status.DONE);
			
		}
		
		return false;
	}
	
	private static boolean removeDependencies(int taskId, int dependsOnId) {
		String sql = "UPDATE Task_Dependencies SET active=0 WHERE task_id=? AND depends_on=?;";

		Connection con = ConnectionPool.getInstance().getConnection();
		boolean success = false;
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			System.out.println(stmt);
			stmt.setInt(1, taskId);
			stmt.setInt(2, dependsOnId);
			int result = stmt.executeUpdate();
			if (result == 1) {
				success = true;
				
				System.out.println("Dependency" + success);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(con);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(con);
		}

		return success;
	}
	
	public static Map<String, Set<Integer>> getDependencies(int taskId) {
		String sql = "SELECT o.depends_on as dependent_on, b.task_id as depended_on " +
						"FROM (SELECT depends_on FROM Task_Dependencies " +
						"WHERE task_id=?) as o, " +
						"(SELECT task_id FROM Task_Dependencies " +
						"WHERE depends_on=?) as b;";
		
		HashMap<String, Set<Integer>> map = new HashMap<String, Set<Integer>>();
		
		Connection con = ConnectionPool.getInstance().getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, taskId);
			stmt.setInt(2, taskId);
			
			ResultSet rs = stmt.executeQuery();
			
			Set<Integer> dependentOnSet = new HashSet<Integer>();
			Set<Integer> dependedOnSet = new HashSet<Integer>();
			
			while (rs.next()) {
				dependentOnSet.add(rs.getInt(1));
				dependedOnSet.add(rs.getInt(2));
			}
			map.put(DEPENDENT_ON, dependentOnSet);
			map.put(DEPENDED_ON, dependedOnSet);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(con);
		}
		System.out.println("Size " + map.size());
		return map;
	}

}
