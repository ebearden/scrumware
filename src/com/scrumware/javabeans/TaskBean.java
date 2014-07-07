package com.scrumware.javabeans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.scrumware.jdbc.JDBCHelper;

public class TaskBean {
	private HashMap<Integer, String> taskMap;
	
	/**
	 * Create a bean with all Tasks
	 */
	public TaskBean() {
		taskMap = new HashMap<Integer, String>();
		Connection con = JDBCHelper.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement(
					"SELECT task_id, task_name FROM Task;"
					);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				taskMap.put(rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a bean with tasks in a particular story.
	 * @param storyId - The stories id.
	 */
	public TaskBean(int storyId) {
		taskMap = new HashMap<Integer, String>();
		Connection con = JDBCHelper.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement(
					"SELECT task_id, task_name FROM Task WHERE story_id=?;"
					);
			stmt.setInt(1, storyId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				taskMap.put(rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Get the tasks from the bean.
	 * @return a list of tasks.
	 */
	public HashMap<Integer, String> getItems() {
		return taskMap;
	}
}
