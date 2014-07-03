package com.scrumware.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.scrumware.config.Constants;
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
	private static final String LIMIT_PARAMETER = "limit";
	private static final String DATA_TYPE_PARAMETER = "data_type";
	
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
		System.out.println("doGet()");
		con = JDBCHelper.getConnection();
		String userId = request.getParameter(Constants.USER_ID);
		String storyId = request.getParameter(Constants.STORY_ID);
		String limit = request.getParameter(LIMIT_PARAMETER);
		String dataType = request.getParameter(DATA_TYPE_PARAMETER);
		
	
		try {
			// Retrieve Tasks
			PreparedStatement stmt = buildQueryStatement(
					Arrays.asList(userId, storyId, limit)
					);
			ResultSet taskResultSet = stmt.executeQuery();
			ResultSetMetaData taskMetaData = taskResultSet.getMetaData();
			
			
			
			ArrayList<String> taskNames = new ArrayList<String>();
			for (int i = 1; i < taskMetaData.getColumnCount() + 1; i++) {
				taskNames.add(taskMetaData.getColumnLabel(i));
			}
			
			// Create Task objects from results.
			taskList.clear();
			while (taskResultSet.next()) {
				Task t = new Task();
				t.setTaskId(taskResultSet.getInt(1));
				t.setName(taskResultSet.getString(2));
				t.setDescription(taskResultSet.getString(3));
				t.setAssignedTo(taskResultSet.getInt(4));
				t.setWorkNotes(taskResultSet.getString(5));
				t.setStatusId(taskResultSet.getInt(6));
				taskList.add(t);
			}
			
			// Add Dependency Ids to tasks.
			ResultSet dependencyResultSet = null;
			ArrayList<Integer> dependencyList = new ArrayList<Integer>();
			for (Task task : taskList) {
				stmt = getDependencies(task.getTaskId());
				dependencyResultSet = stmt.executeQuery();
				while (dependencyResultSet.next()) {
					dependencyList.add(dependencyResultSet.getInt(1));
				}
				task.setDependentTaskList(dependencyList);
				dependencyList.clear();
			}
			
			
			if ("json".equals(dataType)) {
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				for (Task task : taskList) {
					jsonArray.put(task.toJSON());
				}
				jsonObject.put("result", jsonArray);
				response.addHeader("Content-Type", "application/json");
				response.getWriter().println(jsonObject);
			} else {
				request.setAttribute("task_list", FormatHelper.taskListToHTMLTable(taskList, taskNames));
				request.getRequestDispatcher("/task.jsp").forward(request, response);				
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCHelper.freeConnection(con);
		}
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse respsonse) throws ServletException, IOException {
		String taskId = request.getParameter(Constants.TASK_ID);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		con = JDBCHelper.getConnection();
		String userId = null;
		if (request.getParameterMap().containsKey(Constants.USER_ID)) {
			userId = request.getParameter(Constants.USER_ID);
		}
		
		String sql;

		if (userId == null) {
			sql = "INSERT INTO Task " + 
					"(task_name, description, status_id, work_notes, story_id, created_by, updated_by, assigned_to) " +
					"VALUES (?, ?, ?, ?, " + 
					"(SELECT story_id FROM Story WHERE story_name=?), ?, ?, " + 
					"(SELECT user_id FROM Sys_User WHERE first_name=? AND last_name=?));";
		} else {
			sql = "UPDATE Task " + 
					"SET (task_name=?, description=?, status_id=?, work_notes=?, " + 
					"story_id=(SELECT story_id FROM Story WHERE name=?), updated_by=?, " + 
					"assigned_to=(SELECT user_id FROM Sys_User WHERE first_name=? AND last_name=?))"
					+ "WHERE user_id=?;";
			
		}
		
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, request.getParameter(Constants.TASK_NAME));
			stmt.setString(2, request.getParameter(Constants.DESCRIPTION));
			if (request.getParameter(Constants.STATUS_ID) != null) {
				stmt.setInt(3, Integer.parseInt(request.getParameter(Constants.STATUS_ID)));				
			} else {
				// Default to 1, shouldn't get here 
				// if front end validation does its job.
				stmt.setInt(3, 1);  
			}
			stmt.setString(4, request.getParameter(Constants.WORK_NOTES));
			stmt.setString(5, request.getParameter(Constants.STORY_NAME).replaceAll("\\s{2}+", ""));
			stmt.setInt(6, 1); //TODO: get the current user...
			stmt.setInt(7, 1); // TODO: get the current user...
			System.out.println(request.getParameter(Constants.ASSIGNED_TO));
			String[] s = request.getParameter(Constants.ASSIGNED_TO).replaceAll("\\s{2}+", "").split("\\s");
			stmt.setString(8, s[0]);
			System.out.println(s[0]);
			System.out.println(s[1]);
			stmt.setString(9, s[1]);
			if (userId != null) {
				stmt.setInt(10, Integer.parseInt(userId));
			}
			
			int result = stmt.executeUpdate();
			
			if (result == 1) {
				request.setAttribute("msg", "Success!");				
			} else {				
				request.setAttribute("msg", "Failure!");
			}

//			request.getRequestDispatcher("/task.jsp").forward(request, response);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCHelper.freeConnection(con);
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
				"SELECT task_id as ID, task_name as Name, description as Description, " +
				"assigned_to as `Assigned To`, work_notes as `Work Notes`, status_id as Status " + 
				"FROM Task "
				
		);
		
		if (params.get(0) != null || params.get(1) != null) {
			stringBuilder.append("WHERE ");
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
			} else {
				statement.setInt(1, limit);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return statement;
	}
	
	private PreparedStatement getDependencies(Integer taskId) {
		PreparedStatement statement = null;
		String sqlString = "SELECT depends_on FROM Task_Dependencies WHERE task_id=?";
		try {
			statement = con.prepareStatement(sqlString);
			statement.setInt(1, taskId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statement;
	}
}



