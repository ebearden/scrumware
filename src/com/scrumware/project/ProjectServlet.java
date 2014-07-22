package com.scrumware.project;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.scrumware.config.Constants;
import com.scrumware.task.DetailedTask;
import com.scrumware.task.Task;
import com.scrumware.task.TaskDB;
import com.scrumware.user.User;
import com.scrumware.user.UserDB;

/**
 * Servlet implementation class ProjectServlet
 * @author Nick Zitzer
 */
@WebServlet(name = "ProjectServlet", urlPatterns = {"/ProjectServlet", "/project/projects"})
public class ProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATA_TYPE_PARAMETER = "data_type";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dataType = request.getParameter(DATA_TYPE_PARAMETER);
		String userId = request.getParameter(Constants.USER_ID);
		ArrayList<Project> projectList;

		if ("json".equals(dataType) && userId != null) {
			projectList = ProjectDB.getAllProjectsForUserId(Integer.parseInt(userId));
			JSONObject jsonObject = createJSONObject(projectList);
			response.addHeader("Content-Type", "application/json");
			response.getWriter().println(jsonObject);
		} else {
			projectList = ProjectDB.getAllProjects();
			request.setAttribute("project_list", projectList);
			request.getRequestDispatcher("/project/project.jsp").forward(request, response);
		}
	}
	
	private JSONObject createJSONObject(ArrayList<Project> projectList) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Project project : projectList) {
			jsonArray.put(project.toJSON());
		}
		jsonObject.put("result", jsonArray);
		return jsonObject;
	}
}