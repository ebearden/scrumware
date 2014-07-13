package com.scrumware.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scrumware.config.Constants;
import com.scrumware.user.User;
import com.scrumware.user.UserDB;

/**
 * Servlet implementation class TaskDependencies
 */
@WebServlet(name = "TaskDependencies", urlPatterns = {"/TaskDependencies", "/task/dependencies"})
public class TaskDependencies extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskDependencies() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String storyId = request.getParameter(Constants.STORY_ID);
		String taskId = request.getParameter(Constants.TASK_ID);
		ArrayList<Task> taskList = TaskDB.getAllTasksForStory(Integer.parseInt(storyId));
		ArrayList<DetailedTask> detailedTaskList = new ArrayList<DetailedTask>(); 
		
		// Get the tasks in the story except for the current task.
		for (Task task : taskList) {
			if (task.getTaskId() != Integer.parseInt(taskId)) {
				User user = UserDB.getUser(task.getAssignedTo());
				DetailedTask detailedTask = new DetailedTask(task);
				detailedTask.setAssignedTo(user);
				detailedTaskList.add(detailedTask);
			}
		}
		
		request.setAttribute("task_list", detailedTaskList);
		request.setAttribute(Constants.TASK_ID, taskId);
		request.getRequestDispatcher("/task/add_dependency.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String taskId = request.getParameter(Constants.TASK_ID);
		String dependencyId = request.getParameter("dependent_task_id");
		
		
		Task taskToSave = TaskDB.getTask(Integer.parseInt(taskId));
		ArrayList<Integer> list = new ArrayList<>();
		list.addAll(Arrays.asList(Integer.parseInt(dependencyId), 1));
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		map.put(null, list);
		taskToSave.setDependentTaskMap(map);
		
		Task savedTask = TaskDB.saveTask(taskToSave);
		
		request.setAttribute("task", savedTask);
		request.getRequestDispatcher("/task/view").forward(request, response);
	}

}
