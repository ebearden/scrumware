package com.scrumware.project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scrumware.config.Constants;
import com.scrumware.login.SessionHelper;

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
		
		if (!SessionHelper.validateSession(request, response)) {
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
		if (!SessionHelper.validateSession(request, response)) {
			return;
		}
		doGet(request,response);
	}
}
