package com.scrumware.task;

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
		String taskId = request.getParameter(Constants.TASK_ID);
		Task task = TaskDB.getTask(Integer.parseInt(taskId));
		ArrayList<User> userList = UserDB.getUsers();
		
		request.setAttribute("users", userList);
		request.setAttribute("task", task);
		
		request.getRequestDispatcher("/task/edit_task.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String taskId = request.getParameter(Constants.TASK_ID);
		String statusId = request.getParameter(Constants.STATUS_ID);
		String storyId = request.getParameter(Constants.STORY_ID);
		String assignedTo = request.getParameter(Constants.ASSIGNED_TO);
		
		Task task = new Task();
		task.setTaskId(taskId != null ? Integer.parseInt(taskId) : null);
		task.setStatusId(statusId != null ? Integer.parseInt(statusId) : 1);
		task.setStoryId(storyId != null ? Integer.parseInt(storyId) : 1);
		task.setAssignedTo(assignedTo != null ? Integer.parseInt(assignedTo) : 1);
		
		task.setName(request.getParameter(Constants.TASK_NAME));
		task.setDescription(request.getParameter(Constants.DESCRIPTION));
		task.setWorkNotes(request.getParameter(Constants.WORK_NOTES));
		
		// This needs work...
		task.setUpdatedBy(1);
		task.setCreatedBy(1);
//		ArrayList<Integer> list = new ArrayList<>();
//		list.addAll(Arrays.asList(2, 1));
//		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
//		map.put(null, list);
//		// ------------------
//		task.setDependentTaskMap(map);
		Task savedTask = TaskDB.saveTask(task);
		
		request.setAttribute("task", savedTask);
		request.getRequestDispatcher("/task/view_task.jsp").forward(request, response);
	}

}
