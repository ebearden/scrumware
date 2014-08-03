package com.scrumware.sprint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scrumware.config.Constants;
import com.scrumware.login.SessionHelper;
import com.scrumware.task.Task;
import com.scrumware.task.TaskDB;
import com.scrumware.task.TaskHelper;

/**
 * Servlet implementation class DeleteSprintServlet
 */
@WebServlet("/DeleteSprintServlet")
public class DeleteSprintServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteSprintServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!SessionHelper.validateSession(request, response)) {
			return;
		}
		
		String sprintId = request.getParameter(Constants.SPRINT_ID);
		if (sprintId != null) {
			Sprint sprint = new Sprint();
			sprint.setSprintId(Integer.parseInt(sprintId));
			boolean result;
			if (TaskHelper.reassignDependenciesForTask(sprint.getSprintId())) {
				result = SprintDB.deleteSprint(sprint);
			} else {
				result = false;
			}
			String message;
			if (result) {
				message = String.format("Sprint %s successfully deleted.", sprint.getSprintId());			
			} else {
				message = "Failed to delete the sprint.";
			}
			request.setAttribute("message", message);
			request.getRequestDispatcher("/sprint/delete_sprint.jsp").forward(request, response);
		} 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!SessionHelper.validateSession(request, response)) {
			return;
		}
		
	}
}
