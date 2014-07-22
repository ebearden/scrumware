package com.scrumware.story;

import java.io.IOException;
import java.sql.Date;
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
 * Servlet implementation class EditStoryServlet
 * @author Josh Thao
 */
@WebServlet(name = "EditStroy", urlPatterns = {"/EditStory", "/story/edit"})
public class EditStoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditStoryServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String storyId = request.getParameter(Constants.STORY_ID);
		Story story = StoryDB.getStory(Integer.parseInt(storyId));
		ArrayList<User> userList = UserDB.getUsers();
		
		request.setAttribute("users", userList);
		request.setAttribute("stories", story);
		
		request.getRequestDispatcher("/story/edit_story.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String storyId = request.getParameter(Constants.STORY_ID);
		String statusId = request.getParameter(Constants.STATUS_ID);
		String projectId = request.getParameter(Constants.PROJECT_ID);
		
		Story story = new Story();
		story.setStoryID(storyId != null ? Integer.parseInt(storyId) : null);
		story.setStatusId(statusId != null ? Integer.parseInt(statusId) : 1);
		story.setProjectID(projectId != null ? Integer.parseInt(projectId) : null);
		
		story.setStoryName(request.getParameter(Constants.STORY_NAME));
		story.setDescription(request.getParameter(Constants.DESCRIPTION));
		// NEED SESSIONS
		story.setUpdatedBy(1);
		story.setCreatedBy(1);

		Story savedStory = StoryDB.saveStory(story);
		
		request.setAttribute("story", savedStory);
		request.getRequestDispatcher("/story/view_story.jsp").forward(request, response);
	}

}