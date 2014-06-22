package com.scrumware.javabeans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.scrumware.jdbc.JDBCHelper;

public class StoryBean {
	private List<String> storyList;
	
	public StoryBean() {
		storyList = new ArrayList<String>();
		Connection con = JDBCHelper.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement(
					"SELECT name FROM Story;"
					);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				storyList.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getItems() {
		return storyList;
	}
}

