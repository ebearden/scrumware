package com.scrumware.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.scrumware.jdbc.JDBCHelper;
import com.scrumware.jdbc.dto.User;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/testservlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		con = JDBCHelper.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement(
					"SELECT sys_id, first_name, last_name, " +
					"email_address, role, active " +
					"FROM Sys_User;"
					);
			
			ResultSet rs = stmt.executeQuery();
			ArrayList<User> userList = new ArrayList<User>();
			
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setEmailAddress(rs.getString(4));
				user.setRole(rs.getInt(5));
				user.setActive(rs.getBoolean(6));
				userList.add(user);
			}
			
			JSONArray jsonArray = new JSONArray();
			for (User user : userList) {
				jsonArray.put(user.toJSON());
			}
			response.getWriter().println(jsonArray);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}