package com.scrumware.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.scrumware.config.Constants;
import com.scrumware.helpers.FormatHelper;
import com.scrumware.jdbc.da.TaskDA;
import com.scrumware.jdbc.dto.Task;

/**
 * Servlet implementation class TaskServlet
 * 
 * Parameter List:
 * user_id - Tasks assigned to a user.
 * story_id - Tasks that are a part of a story.
 * limit - Max amount to return, defaults to 50.
 */
@WebServlet({ "/TaskServlet", "/tasks" })
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATA_TYPE_PARAMETER = "data_type";
	
	private ArrayList<Task> taskList;
	private TaskDA taskDA;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskServlet() {
        super();
        taskList = new ArrayList<Task>();
        taskDA = new TaskDA();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dataType = request.getParameter(DATA_TYPE_PARAMETER);
		String taskId = request.getParameter(Constants.TASK_ID);
		String userId = request.getParameter(Constants.USER_ID);
		String action = request.getParameter("action");
		
		if (action != null && action.equalsIgnoreCase("delete")) {
			doDelete(request, response);
		} 
		else if (action != null && action.equalsIgnoreCase("edit")) {
			Task task = taskDA.getTask(Integer.parseInt(taskId));
			request.setAttribute(Constants.ASSIGNED_TO, task.getAssignedTo());
			request.setAttribute(Constants.STORY_ID, task.getStoryId());
			request.setAttribute("task", task);
			request.getRequestDispatcher("/edit_task.jsp").forward(request, response);
		}
		else {
			ArrayList<String> taskNames = new ArrayList<String>();


			if ("json".equals(dataType)) {
				if (userId != null) {
					taskList.clear();
					taskList.addAll(taskDA.getAllTasksForUserId(Integer.parseInt(userId)));
					JSONObject jsonObject = createJSONObject();
					response.addHeader("Content-Type", "application/json");
					response.getWriter().println(jsonObject);
				}
			} else {
				if (taskId != null) {
					request.setAttribute("task", taskDA.getTask(Integer.parseInt(taskId)));
					request.getRequestDispatcher("/view_task.jsp").forward(request, response);
				} else {
					taskList.clear();
					taskList.addAll(taskDA.getAllTasks());
					request.setAttribute("task_list", FormatHelper.taskListToHTMLTable(taskList, taskNames));
					request.getRequestDispatcher("/task.jsp").forward(request, response);
				}
			}
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In doDelete()");
		String taskId = request.getParameter(Constants.TASK_ID);
		if (taskId != null) {
			Task task = new Task();
			task.setTaskId(Integer.parseInt(taskId));
			taskDA.deleteTask(task);
			response.sendRedirect("tasks");
		} 
	}
	
	
	@Override
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
		Task savedTask = taskDA.saveTask(task);
		
		request.setAttribute("task", savedTask);
		request.getRequestDispatcher("/view_task.jsp").forward(request, response);
	}
	
	
	private JSONObject createJSONObject() {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Task task : taskList) {
			jsonArray.put(task.toJSON());
		}
		jsonObject.put("result", jsonArray);
		return jsonObject;
	}
}



