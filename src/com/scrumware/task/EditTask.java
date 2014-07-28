package com.scrumware.task;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
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
import com.scrumware.story.Story;
import com.scrumware.story.StoryDB;
import com.scrumware.user.User;
import com.scrumware.user.UserDB;

/**
 * Servlet implementation class EditTask
 * @author Elvin Bearden
 */
@WebServlet(name = "EditTask", urlPatterns = {"/EditTask", "/task/edit"})
public class EditTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditTask() {
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
		
		String taskId = request.getParameter(Constants.TASK_ID);
		Task task = TaskDB.getTask(Integer.parseInt(taskId));
		ArrayList<User> userList = UserDB.getUsers();
		ArrayList<Story> storyList = StoryDB.getAllStories();
	
		request.setAttribute(Constants.STATUS, Status.values());
		request.setAttribute("users", userList);
		request.setAttribute("task", task);
		request.setAttribute("stories", storyList);
		request.getRequestDispatcher("/task/edit_task.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (!isValidSession(request)) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		
		String taskId = request.getParameter(Constants.TASK_ID);
		String statusId = request.getParameter(Constants.STATUS_ID);
		String storyId = request.getParameter(Constants.STORY_ID);
		String assignedTo = request.getParameter(Constants.ASSIGNED_TO);
		
		Task task = new Task();
		/*
		 * Make sure the task doesn't have any open dependencies 
		 * if its status is being changed to done. 
		 * 
		 * If it does have open dependencies just keep the status the same. 
		 * 
		*/
		if (statusId != null && Integer.parseInt(statusId) == Status.DONE.getCode()) {
			if (TaskHelper.closeTask(Integer.parseInt(taskId))) {
				task.setStatusId(statusId != null ? Integer.parseInt(statusId) : 1);
			} else {
				int oldStatusId = TaskDB.getTask(Integer.parseInt(taskId)).getStatusId();
				task.setStatusId(oldStatusId);
				request.setAttribute("err_msg", "Can't close a task with open dependencies.");
			}
		} else {
			task.setStatusId(statusId != null ? Integer.parseInt(statusId) : 1);
		}
		
		task.setTaskId(taskId != null ? Integer.parseInt(taskId) : null);			
		task.setStoryId(storyId != null ? Integer.parseInt(storyId) : 1);
		task.setAssignedTo(assignedTo != null ? Integer.parseInt(assignedTo) : 1);
		task.setName(request.getParameter(Constants.TASK_NAME));
		task.setDescription(request.getParameter(Constants.DESCRIPTION));
		task.setWorkNotes(request.getParameter(Constants.WORK_NOTES));
		
		HttpSession session = request.getSession(false);
		Integer userId = null;
		if (session != null) {
			userId = (Integer)session.getAttribute("id");
		} else if (request.getParameter(Constants.USER_ID) != null) {
			userId = Integer.parseInt(request.getParameter(Constants.USER_ID));
		}
		// Handle Created by, Updated By,
		if (userId != null) {
			if (task.getTaskId() == null) {
				// This is an insert. Set both created and updated
				task.setCreatedBy(userId);
				task.setUpdatedBy(userId);	
			} else {
				// This is an update. Just set updated.
				task.setUpdatedBy(userId);
			}
		}
		
		

		Task savedTask = TaskDB.saveTask(task);
		request.setAttribute("task", savedTask);
		
		if (taskId == null) {
			request.getRequestDispatcher("/task/view?task_id=" + savedTask.getTaskId()).forward(request, response);
		} else if (request.getParameter("request_type") != null && request.getParameter("request_type").equals("mobile")) {
			response.addHeader("Content-Type", "application/json");
			response.getWriter().println(new JSONObject().put("result", "success"));
		} else {
			request.getRequestDispatcher("/task/view").forward(request, response);			
		}
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
		} else {
			return true;
		}
	}

}
