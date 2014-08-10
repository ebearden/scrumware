package com.scrumware.sprint;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scrumware.config.Constants;
import com.scrumware.story.Story;
import com.scrumware.story.StoryDB;
import com.scrumware.user.User;
import com.scrumware.user.UserDB;

/**
 * Servlet implementation class ViewSprintServlet
 * John Zorgdrager
 */
@WebServlet(name = "ViewSprintServlet", urlPatterns = {"/ViewSprint", "/sprint/view"})
public class ViewSprintServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ViewSprintServlet() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (!isValidSession(request)) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		String sprintID = request.getParameter(Constants.SPRINT_ID);
		Sprint sprint = SprintDB.getSprint(Integer.parseInt(sprintID));
		User createdBy = UserDB.getUser(sprint.getCreatedBy());
		User updatedBy = UserDB.getUser(sprint.getUpdatedBy());
		ArrayList<Story> storyList = StoryDB.getAllStoriesForSprint(Integer.parseInt(sprintID));
		
		request.setAttribute("story_list", storyList);
		request.setAttribute("sprint", sprint);
		request.setAttribute("created_by", createdBy);
		request.setAttribute("updated_by", updatedBy);

		request.getRequestDispatcher("/sprint/view_sprint.jsp").forward(request, response);
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		if (!isValidSession(request)) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		doGet(request, response);
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
