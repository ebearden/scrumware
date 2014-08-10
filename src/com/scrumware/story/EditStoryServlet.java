package com.scrumware.story;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.scrumware.config.Constants;
import com.scrumware.config.Status;
import com.scrumware.login.SessionHelper;
import com.scrumware.sprint.Sprint;
import com.scrumware.sprint.SprintDB;
import com.scrumware.task.TaskDB;
import com.scrumware.task.TaskHelper;
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
		
		if (!SessionHelper.validateSession(request, response)) {
			return;
		}
		
		String storyId = request.getParameter(Constants.STORY_ID);
		Story story = StoryDB.getStory(Integer.parseInt(storyId));
		ArrayList<Sprint> sprintList = SprintDB.getAllSprints();
		ArrayList<User> userList = UserDB.getUsers();
		
		request.setAttribute(Constants.STATUS, Status.values());
		request.setAttribute("users", userList);
		request.setAttribute("story", story);
		request.setAttribute("sprint", sprintList);
		
		request.getRequestDispatcher("/story/edit_story.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (!SessionHelper.validateSession(request, response)) {
			return;
		}
		
		String storyId = request.getParameter(Constants.STORY_ID);
		String statusId = request.getParameter(Constants.STATUS_ID);
		String sprintId = request.getParameter(Constants.SPRINT_ID);
		
		
		Story story = new Story();
		
		if (statusId != null && Integer.parseInt(statusId) == Status.DONE.getCode()) {
			if (StoryDB.hasOpenTasks(Integer.parseInt(storyId))) {
				int oldStatusId = StoryDB.getStory(Integer.parseInt(storyId)).getStatusID();
				story.setStatusId(oldStatusId);
				request.setAttribute("err_msg", "Can't close a story with open tasks.");				
			} else {
				story.setStatusId(statusId != null ? Integer.parseInt(statusId) : 1);
			}
		} else {
			story.setStatusId(statusId != null ? Integer.parseInt(statusId) : 1);
		}
		
		story.setStoryID(storyId != null ? Integer.parseInt(storyId) : null);
		story.setSprintID(sprintId != null ? Integer.parseInt(sprintId) : null);
		story.setStoryName(request.getParameter(Constants.STORY_NAME));
		story.setDescription(request.getParameter(Constants.DESCRIPTION));
		story.setAcceptanceCriteria(request.getParameter(Constants.ACCEPTENCE_CRITERIA));
		
		
		HttpSession session = request.getSession(false);
		Integer userId = null;
		if (session != null) {
			userId = (Integer)session.getAttribute("id");
		} else if (request.getParameter(Constants.USER_ID) != null) {
			userId = Integer.parseInt(request.getParameter(Constants.USER_ID));
		}
		// Handle Created by, Updated By,
		if (userId != null) {
			if (story.getStoryID() == null) {
				// This is an insert. Set both created and updated
				story.setCreatedBy(userId);
				story.setUpdatedBy(userId);	
			} else {
				// This is an update. Just set updated.
				story.setUpdatedBy(userId);
			}
		}


		Story savedStory = StoryDB.saveStory(story);
		
		if (storyId == null) {
			request.getRequestDispatcher("/story/view?story_id=" + savedStory.getStoryID()).forward(request, response);
		} else {
			request.getRequestDispatcher("/story/view").forward(request, response);			
		}
	}
}