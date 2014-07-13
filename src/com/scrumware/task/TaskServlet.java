package com.scrumware.task;

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

/**
 * Servlet implementation class TaskServlet
 * @author Elvin Bearden
 */
@WebServlet(name = "TaskServlet", urlPatterns = {"/TaskServlet", "/task/all"})
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATA_TYPE_PARAMETER = "data_type";
	
	private ArrayList<Task> taskList;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskServlet() {
        super();
        taskList = new ArrayList<Task>();
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
		else {

			if ("json".equals(dataType)) {
				if (userId != null) {
					taskList.clear();
					taskList.addAll(TaskDB.getAllTasksForUserId(Integer.parseInt(userId)));
					JSONObject jsonObject = createJSONObject();
					response.addHeader("Content-Type", "application/json");
					response.getWriter().println(jsonObject);
				}
			} else {
				if (taskId != null) {
					request.setAttribute("task", TaskDB.getTask(Integer.parseInt(taskId)));
					request.getRequestDispatcher("/task/view_task.jsp").forward(request, response);
				} else {
					taskList.clear();
					taskList.addAll(TaskDB.getAllTasks());
					request.setAttribute("task_list", taskList);
					request.getRequestDispatcher("/task/tasks.jsp").forward(request, response);
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
			TaskDB.deleteTask(task);
			response.sendRedirect("tasks");
		} 
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



