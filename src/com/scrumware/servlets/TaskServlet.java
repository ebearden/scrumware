package com.scrumware.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scrumware.helpers.FormatHelper;
import com.scrumware.jdbc.JDBCHelper;
import com.scrumware.jdbc.dto.Task;

/**
 * Servlet implementation class TaskServlet
 * 
 * Parameter List:
 * user_id - Tasks assigned to a user.
 * story_id - Tasks that are a part of a story.
 * limit - Max amount to return, defaults to 50.
 */
@WebServlet({ "/TaskServlet", "/tasks" })
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final Integer TASK_LIMIT = 50;
	private static final String USER_ID_PARAMETER = "user_id";
	private static final String STORY_ID_PARAMETER = "story_id";
	private static final String LIMIT_PARAMETER = "limit";
	
	private Connection con;
	private ArrayList<Task> taskList;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskServlet() {
        super();
        taskList = new ArrayList<Task>();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		con = JDBCHelper.getConnection();
		String userId = request.getParameter(USER_ID_PARAMETER);
		String storyId = request.getParameter(STORY_ID_PARAMETER);
		String limit = request.getParameter(LIMIT_PARAMETER);
	
		try {
			
			PreparedStatement stmt = buildQueryStatement(
					Arrays.asList(userId, storyId, limit)
					);
			ResultSet rs = stmt.executeQuery();

			
			// Create Task objects from results.
			taskList.clear();
			while (rs.next()) {
				Task t = new Task();
				t.setTaskId(rs.getInt(1));
				t.setName(rs.getString(2));
				t.setDescription(rs.getString(3));
				t.setStatus(rs.getString(6));
				taskList.add(t);
			}
			response.getWriter().println(FormatHelper.taskListToHTMLTable(taskList));
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
		con = JDBCHelper.getConnection();
		for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
		       response.getWriter().println(params.nextElement());
		}
		
		String sql = "INSERT INTO Task " + 
				"(name, description, status, work_notes, story_id, created_by, updated_by, assigned_to) " +
				"VALUES (?, ?, ?, ?, " + 
				"(SELECT sys_id FROM Story WHERE name=?), ?, ?, " + 
				"(SELECT sys_id FROM Sys_User WHERE first_name=? AND last_name=?));";
		
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, request.getParameter("name"));
			stmt.setString(2, request.getParameter("description"));
			stmt.setString(3, "Backlog");
			stmt.setString(4, null);
			stmt.setString(5, request.getParameter("story_id"));
			stmt.setInt(6, 1); //TODO: get the current user...
			stmt.setInt(7, 1);
			String[] s = request.getParameter("assigned_to").split("\\s");
			stmt.setString(8, s[0]);
			stmt.setString(9, s[1]);
			
			
			System.out.println(stmt.toString());
			
			int taskId = stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Build a Query from the parameters sent to the servlet. 
	 * @param params - sent params
	 * @return PreparedStatement build from params.
	 */
	private PreparedStatement buildQueryStatement(List<String> params) {
		Integer userId = null; 
		Integer storyId = null;
		Integer limit = null;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(
				"SELECT sys_id, name, description, assigned_to, work_notes, status " + 
				"FROM Task " + 
				"WHERE "
		);
	 
		if (params.get(0) != null) {
			stringBuilder.append("assigned_to=? ");
			userId = Integer.parseInt(params.get(0));
		}
		if (params.get(1) != null) {
			if (params.get(0) != null) {
				stringBuilder.append(" AND ");
			}
			stringBuilder.append("story_id=? ");
			storyId = Integer.parseInt(params.get(1));
		}
		if (params.get(2) != null) {
			stringBuilder.append("LIMIT ?;");
			limit = Integer.parseInt(params.get(2));
		} else {
			stringBuilder.append("LIMIT ?;");
			limit = TASK_LIMIT;
		}
		
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(stringBuilder.toString());
			if (userId != null && storyId != null) {
				statement.setInt(1, userId);
				statement.setInt(2, storyId);
				statement.setInt(3, limit);
			} else if (userId != null && storyId == null) {
				statement.setInt(1, userId);
				statement.setInt(2, limit);
			} else if (userId == null && storyId != null) {
				statement.setInt(1, storyId);
				statement.setInt(2, limit);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return statement;
	}
}



