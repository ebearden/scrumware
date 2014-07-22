package com.scrumware.story;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

}
