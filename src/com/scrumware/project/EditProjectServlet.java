package com.scrumware.project;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scrumware.config.Constants;
import com.scrumware.task.Task;
import com.scrumware.task.TaskDB;
import com.scrumware.user.User;
import com.scrumware.user.UserDB;
import com.scrumware.config.Status;

/**
 * Servlet implementation class EditProjectServlet
 * @author Nick Zitzer
 */
@WebServlet(name = "EditProject", urlPatterns = {"/EditProject", "/project/edit"})
public class EditProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProjectServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (!isValidSession(request)) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		String projectId = request.getParameter(Constants.PROJECT_ID);
		Project project = ProjectDB.getProject(Integer.parseInt(projectId));
		ArrayList<User> userList = UserDB.getUsers();
		

		request.setAttribute(Constants.STATUS, Status.values());
		request.setAttribute("users", userList);
		request.setAttribute("project", project);
		
		request.getRequestDispatcher("/project/edit_project.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (!isValidSession(request)) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		String projectId = request.getParameter(Constants.PROJECT_ID);
		String statusId = request.getParameter(Constants.STATUS_ID);
		String projectMgr = request.getParameter(Constants.PROJECT_MANAGER);
		
		Project project = new Project();
		if(projectId != null) {
			project.setProjectId(Integer.parseInt(projectId));
			System.out.println(projectId);
		}
		project.setStatusId(statusId != null ? Integer.parseInt(statusId) : 1);
		project.setProjectManagerId(projectMgr != null ? Integer.parseInt(projectMgr) : null);
		
		project.setName(request.getParameter(Constants.PROJECT_NAME));
		project.setDescription(request.getParameter(Constants.DESCRIPTION));
		project.setStartDate(Date.valueOf(request.getParameter(Constants.PLANNED_START_DATE)));
		project.setEndDate(Date.valueOf(request.getParameter(Constants.PLANNED_END_DATE)));
		
		HttpSession session = request.getSession(false);
		Integer userId = null;
		if (session != null) {
			userId = (Integer)session.getAttribute("id");
		} else if (request.getParameter(Constants.USER_ID) != null) {
			userId = Integer.parseInt(request.getParameter(Constants.USER_ID));
		}
		// Handle Created by, Updated By,
		if (userId != null) {
			if (project.getProjectId() == null) {
				// This is an insert. Set both created and updated
				project.setCreatedBy(userId);
				project.setUpdatedBy(userId);	
			} else {
				// This is an update. Just set updated.
				project.setUpdatedBy(userId);
			}
		}

		Project savedProject = ProjectDB.saveProject(project);
		request.setAttribute("project", savedProject);
		if (projectId == null) {
			request.getRequestDispatcher("/project/view?project_id=" + savedProject.getProjectId()).forward(request, response);
		} else {
			request.getRequestDispatcher("/project/view").forward(request, response);			
		}
		
	}
	
	private boolean isValidSession(HttpServletRequest request) {
		if (request.getParameter("key") != null && request.getParameter("key").equals(Constants.LOGIN_KEY)) {
			return true;
		}
		
		HttpSession session = request.getSession(false);
		if (session.getAttribute("id") == null || session.getAttribute("id").equals("")) {
			return false;
		} else if (session.getAttribute("user_name") == null || session.getAttribute("user_name").equals("")) {
			return false;
		} else if (session.getAttribute("role") == null || session.getAttribute("role").equals("")) {
			return false;
		} else {
			return true;
		}
	}

}