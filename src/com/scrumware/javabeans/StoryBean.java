package com.scrumware.javabeans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.scrumware.jdbc.ConnectionPool;


public class StoryBean {
	private HashMap<Integer, String> storyMap;
	private ConnectionPool pool;
	public StoryBean() {
		pool = ConnectionPool.getInstance();
		storyMap = new HashMap<Integer, String>();
		Connection con = pool.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement(
					"SELECT story_id, story_name FROM Story;"
					);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				storyMap.put(rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<Integer, String> getItems() {
		return storyMap;
	}
}

