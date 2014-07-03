package com.scrumware.javabeans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.scrumware.jdbc.JDBCHelper;

public class UserBean {
	private List<String> userList;
	public UserBean() {
		userList = new ArrayList<String>();
		Connection con = JDBCHelper.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement(
					"SELECT first_name, last_name FROM Sys_User WHERE user_id <> 1;"
					);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				userList.add(rs.getString(1) + " " + rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getItems() {
		return userList;
	}
}
