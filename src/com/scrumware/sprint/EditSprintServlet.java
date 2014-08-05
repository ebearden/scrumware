package com.scrumware.sprint;

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
import com.scrumware.config.Status;
import com.scrumware.project.Project;
import com.scrumware.project.ProjectDB;
import com.scrumware.user.User;
import com.scrumware.user.UserDB;

/**
 * Servlet implementation class EditSprintServlet
 * John Zorgdrager
 */
@WebServlet(name = "EditSprint", urlPatterns = {"/EditSprint", "/sprint/edit"})
public class EditSprintServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditSprintServlet() {
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
		
		String sprintId = request.getParameter(Constants.SPRINT_ID);
		Sprint sprint = SprintDB.getSprint(Integer.parseInt(sprintId));
		ArrayList<Project> projects = ProjectDB.getAllProjects();
		ArrayList<User> userList = UserDB.getUsers();
		

		request.setAttribute(Constants.STATUS, Status.values());
		request.setAttribute("projects", projects);
		request.setAttribute("sprint", sprint);
		
		request.getRequestDispatcher("/sprint/edit_sprint.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if (!isValidSession(request)) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		String sprintId = request.getParameter(Constants.SPRINT_ID);
		String statusId = request.getParameter(Constants.STATUS_ID);
		String projectId = request.getParameter(Constants.PROJECT_ID);
		
		Sprint sprint = new Sprint();
		if(sprintId != null) {
			sprint.setSprintId(Integer.parseInt(sprintId));
		}
		
		sprint.setStatus(statusId != null ? Integer.parseInt(statusId) : 1);
		
		sprint.setName(request.getParameter(Constants.SPRINT_NAME));
		sprint.setDescription(request.getParameter(Constants.DESCRIPTION));
		sprint.setProjectId(Integer.parseInt(projectId));
		sprint.setStartDate(Date.valueOf(request.getParameter(Constants.START_DATE)));
		sprint.setEndDate(Date.valueOf(request.getParameter(Constants.END_DATE)));
		
		HttpSession session = request.getSession(false);
		Integer userId = null;
		if (session != null) {
			userId = (Integer)session.getAttribute("id");
		} else if (request.getParameter(Constants.USER_ID) != null) {
			userId = Integer.parseInt(request.getParameter(Constants.USER_ID));
		}
		// Handle Created by, Updated By,
		if (userId != null) {
			if (sprint.getSprintId() == null) {
				// This is an insert. Set both created and updated
				sprint.setCreatedBy(userId);
				sprint.setUpdatedBy(userId);	
			} else {
				// This is an update. Just set updated.
				sprint.setUpdatedBy(userId);
			}
		}
		
		Sprint savedSprint = SprintDB.saveSprint(sprint);
		request.setAttribute("sprint", savedSprint);
		if (sprintId == null) {
			request.getRequestDispatcher("/sprint/view?sprint_id=" + savedSprint.getSprintId()).forward(request, response);
		} else {
			request.getRequestDispatcher("/sprint/view").forward(request, response);			
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
