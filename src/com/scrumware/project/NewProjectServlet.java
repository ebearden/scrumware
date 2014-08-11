package com.scrumware.project;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scrumware.config.Constants;
import com.scrumware.config.Status;
import com.scrumware.login.SessionHelper;
import com.scrumware.user.User;
import com.scrumware.user.UserDB;

/**
 * Servlet implementation class NewProjectServlet
 * @author Nick Zitzer
 */
@WebServlet(name = "NewProject", urlPatterns = {"/NewProject", "/project/new"})
public class NewProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewProjectServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (!SessionHelper.validateSession(request, response)) {
			return;
		}
		
		ArrayList<User> userList = UserDB.getUsersByRole(2);
		ArrayList<Project> projectList = ProjectDB.getAllProjects();
		
		request.setAttribute(Constants.STATUS, Status.values());
		request.setAttribute("users", userList);
		request.setAttribute("projects", projectList);
		request.getRequestDispatcher("/project/new_project.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (!SessionHelper.validateSession(request, response)) {
			return;
		}
		
		request.getRequestDispatcher("edit").forward(request, response);
	}
}
