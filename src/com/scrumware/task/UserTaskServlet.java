package com.scrumware.task;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.scrumware.config.Constants;
import com.scrumware.task.DetailedTask;
import com.scrumware.task.Task;
import com.scrumware.task.TaskDB;
import com.scrumware.user.User;
import com.scrumware.user.UserDB;

/**
 * Servlet implementation class SprintServlet
 * @author John Zorgdrager
 */
@WebServlet(name = "UserTaskServlet", urlPatterns = {"/UserTaskServlet", "/task/assigned_tasks"})
public class UserTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATA_TYPE_PARAMETER = "data_type";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserTaskServlet() {
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
		
		String dataType = request.getParameter(DATA_TYPE_PARAMETER);
		String userId = request.getParameter(Constants.USER_ID);
		ArrayList<Task> taskList;

		if ("json".equals(dataType) && userId != null) {
			taskList = TaskDB.getAllTasksForUserId(Integer.parseInt(userId));
			JSONObject jsonObject = createJSONObject(taskList);
			response.addHeader("Content-Type", "application/json");
			response.getWriter().println(jsonObject);
		} else {
			taskList = TaskDB.getAllTasks();
			request.setAttribute("task_list", taskList);
			request.getRequestDispatcher("/task/assigned_tasks.jsp").forward(request, response);
		}
	}
	
	private JSONObject createJSONObject(ArrayList<Task> taskList) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Task task : taskList) {
			jsonArray.put(task.toJSON());
		}
		jsonObject.put("result", jsonArray);
		return jsonObject;
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