package com.scrumware.project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * Servlet implementation class ProjectServlet
 * 
 * Parameter List:
 * user_id - Projects for PM User.
 * limit - Max amount to return, defaults to 50.
 */
@WebServlet({ "/ProjectServlet", "/project" })
public class ProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATA_TYPE_PARAMETER = "data_type";
	
	private ArrayList<Project> projectList;
	private ProjectDA projectDA;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectServlet() {
        super();
        projectList = new ArrayList<Project>();
        projectDA = new ProjectDA();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dataType = request.getParameter(DATA_TYPE_PARAMETER);
		String projectId = request.getParameter(Constants.PROJECT_ID);
		String userId = request.getParameter(Constants.USER_ID);
		String action = request.getParameter("action");
		
		if (action != null && action.equalsIgnoreCase("delete")) {
			doDelete(request, response);
		} 
		else if (action != null && action.equalsIgnoreCase("edit")) {
			Project project = projectDA.getProject(Integer.parseInt(projectId));
			request.setAttribute(Constants.PROJECT_MANAGER, project.getPM());
			request.setAttribute(Constants.PROJECT_ID, project.getProjectID());
			request.setAttribute("project", project);
			request.getRequestDispatcher("/edit_project.jsp").forward(request, response);
		}
		else {
			ArrayList<String> projectNames = new ArrayList<String>();


			if ("json".equals(dataType)) {
				if (userId != null) {
					projectList.clear();
					projectList.addAll(projectDA.getAllProjectsForUserId(Integer.parseInt(userId)));
					JSONObject jsonObject = createJSONObject();
					response.addHeader("Content-Type", "application/json");
					response.getWriter().println(jsonObject);
				}
			} else {
				if (projectId != null) {
					request.setAttribute("project", projectDA.getProject(Integer.parseInt(projectId)));
					request.getRequestDispatcher("/view_project.jsp").forward(request, response);
				} else {
					projectList.clear();
					projectList.addAll(projectDA.getAllProjects());
//					request.setAttribute("project_list", FormatHelper.projectListToHTMLTable(projectList, projectNames));
					request.getRequestDispatcher("/task.jsp").forward(request, response);
				}
			}
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In doDelete()");
		String projectId = request.getParameter(Constants.PROJECT_ID);
		if (projectId != null) {
			Project project = new Project();
			project.setProjectID(Integer.parseInt(projectId));
			projectDA.deleteProject(project);
			request.getRequestDispatcher("/project.jsp").forward(request, response);
		} 
	}
	
	
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		request.setAttribute("project", projectDA.getProject(Integer.parseInt(projectId)));
//		request.getRequestDispatcher("/view_project.jsp").forward(request, response);
//	}
//	
	
	private JSONObject createJSONObject() {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Project project : projectList) {
			jsonArray.put(project.toJSON());
		}
		jsonObject.put("result", jsonArray);
		return jsonObject;
	}
}



