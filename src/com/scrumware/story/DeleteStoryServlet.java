package com.scrumware.story;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scrumware.config.Constants;

/**
 * Servlet implementation class DeleteStoryServlet
 * @author Josh Thao
 */
@WebServlet(name = "DeleteStory", urlPatterns = {"/DeleteStory", "/story/delete"})
public class DeleteStoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteStoryServlet() {
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
		
		String storyId = request.getParameter(Constants.STORY_ID);
		if (storyId != null) {
			Story story = new Story();
			story.setStoryID(Integer.parseInt(storyId));
			boolean result = StoryDB.deleteStory(story);
			String message;
			if (result) {
				message = String.format("Story %s successfully deleted.", story.getStoryID());			
			} else {
				message = "Failed to delete the story.";
			}
			request.setAttribute("message", message);
			request.getRequestDispatcher("/story/delete_story.jsp").forward(request, response);
		} 
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
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
