package com.scrumware.story;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scrumware.login.SessionHelper;
import com.scrumware.sprint.Sprint;
import com.scrumware.sprint.SprintDB;
import com.scrumware.user.User;
import com.scrumware.user.UserDB;

/**
 * Servlet implementation class NewStoryServlet
 * @author Josh Thao
 */
@WebServlet(name = "NewStory", urlPatterns = {"/NewStory", "/story/new"})
public class NewStoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewStoryServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (!SessionHelper.validateSession(request, response)) {
			return;
		}
		
		ArrayList<User> userList = UserDB.getUsers();
		ArrayList<Story> storyList = StoryDB.getAllStories();
		ArrayList<Sprint> sprintList = SprintDB.getAllSprints();
		//GET SPRINTS
		request.setAttribute("users", userList);
		request.setAttribute("story", storyList);
		request.setAttribute("sprint", sprintList);
		request.getRequestDispatcher("/story/new_story.jsp").forward(request, response);
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

