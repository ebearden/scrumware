package com.scrumware.task;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scrumware.config.Constants;
import com.scrumware.login.SessionHelper;
import com.scrumware.story.Story;
import com.scrumware.story.StoryDB;
import com.scrumware.user.User;
import com.scrumware.user.UserDB;

/**
 * Servlet implementation class ViewTask
 * @author Elvin Bearden
 */
@WebServlet(name = "ViewTask", urlPatterns = {"/ViewTask", "/task/view"})
public class ViewTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewTask() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (!SessionHelper.validateSession(request, response)) {
			return;
		}
		
		String taskId = request.getParameter(Constants.TASK_ID);
		Task task = TaskDB.getTask(Integer.parseInt(taskId));
		User assignedToUser = UserDB.getUser(task.getAssignedTo());
		User createdByUser = UserDB.getUser(task.getCreatedBy());
		User updatedByUser = UserDB.getUser(task.getUpdatedBy());
		Story story = StoryDB.getStory(task.getStoryId());
		
		ArrayList<DetailedTask> taskDependencyList = new ArrayList<DetailedTask>();
		
		for (Integer id : task.getDependentTaskMap().keySet()) {
			ArrayList<Integer> list = task.getDependentTaskMap().get(id);
			User user = UserDB.getUser(TaskDB.getTask(list.get(0)).getAssignedTo());
			
			DetailedTask dependentTask = new DetailedTask();
			dependentTask.setTask(TaskDB.getTask(list.get(0)));
			dependentTask.setAssignedTo(user);
			
			taskDependencyList.add(dependentTask);
		}
		
		request.setAttribute("task", task);
		request.setAttribute("dependencies", taskDependencyList);
		request.setAttribute(Constants.ASSIGNED_TO, assignedToUser);
		request.setAttribute(Constants.CREATED_BY, createdByUser);
		request.setAttribute(Constants.UPDATED_BY, updatedByUser);
		request.setAttribute("story", story);

		request.getRequestDispatcher("/task/view_task.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!SessionHelper.validateSession(request, response)) {
			return;
		}
		doGet(request, response);
	}
}
