package com.scrumware.project;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scrumware.config.Constants;
import com.scrumware.user.User;
import com.scrumware.user.UserDB;

/**
 * Servlet implementation class ViewProject
 * @author Nick Zitzer
 */
@WebServlet(name = "ViewProject", urlPatterns = {"/ViewProject", "/project/view"})
public class ViewProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String projectId = request.getParameter(Constants.PROJECT_ID);
		Project project = ProjectDB.getProject(Integer.parseInt(projectId));
		User project_manager = UserDB.getUser(project.getProjectManagerId());
		User createdBy = UserDB.getUser(project.getCreatedBy());
		User updatedBy = UserDB.getUser(project.getUpdatedBy());
		
		request.setAttribute("project", project);
		request.setAttribute("projectManager", project_manager);
		request.setAttribute("created_by", createdBy);
		request.setAttribute("updated_by", updatedBy);

		request.getRequestDispatcher("/project/view_project.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
