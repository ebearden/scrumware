package com.scrumware.javabeans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.scrumware.jdbc.JDBCHelper;
import com.scrumware.jdbc.dto.User;

public class UserBean {
	private List<String> userList;
	
	public UserBean() {
		userList = getUsers(null);
	}
	
	public UserBean(int userId) {
		userList = getUsers(userId);
	}

	private List<String> getUsers(Integer id) {
		userList = new ArrayList<String>();
		Connection con = JDBCHelper.getConnection();
		try {
			PreparedStatement stmt = null;
			if (id != null) {
				stmt = con.prepareStatement(
						"SELECT first_name, last_name FROM Sys_User WHERE user_id=?;"
						);
				stmt.setInt(1, id);
			} else {
				stmt = con.prepareStatement(
						"SELECT first_name, last_name FROM Sys_User WHERE user_id <> 1;"
						);
			}
				
					
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				userList.add(rs.getString(1) + " " + rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCHelper.freeConnection(con);
		}
		
		return userList;
	}
	
	public List<String> getItems() {
		return userList;
	}
}
