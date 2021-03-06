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
import org.omg.CORBA.Request;

import com.scrumware.config.Constants;
import com.scrumware.login.SessionHelper;
import com.scrumware.story.Story;
import com.scrumware.story.StoryDB;
import com.scrumware.user.User;
import com.scrumware.user.UserDB;

/**
 * Servlet implementation class TaskServlet
 * @author Elvin Bearden
 */
@WebServlet(name = "TaskServlet", urlPatterns = {"/TaskServlet", "/task/tasks"})
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATA_TYPE_PARAMETER = "data_type";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!SessionHelper.validateSession(request, response)) {
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
			ArrayList<DetailedTask> taskDetailList = new ArrayList<DetailedTask>();
			
			for (Task task : taskList) {
				User user = UserDB.getUser(task.getAssignedTo());
				Story story = StoryDB.getStory(task.getStoryId());
				DetailedTask detailedTask = new DetailedTask(task);
				detailedTask.setAssignedTo(user);
				detailedTask.setStory(story);
				taskDetailList.add(detailedTask);
			}
			
			request.setAttribute("task_list", taskDetailList);
			request.getRequestDispatcher("/task/tasks.jsp").forward(request, response);
		}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!SessionHelper.validateSession(request, response)) {
			return;
		}
		doGet(request,response);
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
