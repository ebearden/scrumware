package com.scrumware.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scrumware.config.Constants;

/**
 * Servlet implementation class DeleteProjectServlet
 * @author Nick Zitzer
 */
@WebServlet(name = "DeleteProject", urlPatterns = {"/DeleteProject", "/project/delete"})
public class DeleteProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteProjectServlet() {
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
		if (projectId != null) {
			Project project = new Project();
			project.setProjectId(Integer.parseInt(projectId));
			boolean result = ProjectDB.deleteProject(project.getProjectId());
			String message;
			if (result) {
				message = String.format("Project %s successfully deleted.", project.getProjectId());			
			} else {
				message = "Failed to delete the project.";
			}
			request.setAttribute("message", message);
			request.getRequestDispatcher("/project/delete_project.jsp").forward(request, response);
		} 
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
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
