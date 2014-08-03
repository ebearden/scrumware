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
import com.scrumware.login.SessionHelper;
import com.scrumware.task.Task;
import com.scrumware.task.TaskDB;

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
		if (!SessionHelper.validateSession(request, response)) {
			return;
		}
		
		Integer userId = SessionHelper.getSessionUserId(request);
		String dataType = request.getParameter(DATA_TYPE_PARAMETER);
		ArrayList<Task> taskList;

		if ("json".equals(dataType) && userId != null) {
			taskList = TaskDB.getAllTasksForUserId(userId);
			JSONObject jsonObject = createJSONObject(taskList);
			response.addHeader("Content-Type", "application/json");
			response.getWriter().println(jsonObject);
		} else {
			taskList = TaskDB.getAllTasksForUserId(userId);
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
}