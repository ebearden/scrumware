package com.scrumware.story;

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
import com.scrumware.task.DetailedTask;
import com.scrumware.task.Task;
import com.scrumware.task.TaskDB;
import com.scrumware.user.User;
import com.scrumware.user.UserDB;

/**
 * Servlet implementation class ViewStoryServlet
 * @author Josh Thao
 */
@WebServlet(name = "ViewStoryServlet", urlPatterns = {"/ViewStory", "/story/view"})
public class ViewStoryServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;

	private static final String DATA_TYPE_PARAMETER = "data_type";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewStoryServlet() {
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
		String storyId = request.getParameter(Constants.STORY_ID);
		ArrayList<Task> taskList;
		
		Story story = StoryDB.getStory(Integer.parseInt(storyId));
		User createdByUser = UserDB.getUser(story.getCreatedBy());
		User updatedByUser = UserDB.getUser(story.getUpdatedBy());
		
		if ("json".equals(dataType) && storyId != null) {
			taskList = TaskDB.getAllTasksForStory(Integer.parseInt(storyId));
			JSONObject jsonObject = createJSONObject(taskList);
			response.addHeader("Content-Type", "application/json");
			response.getWriter().println(jsonObject);
		} else {
			taskList = TaskDB.getAllTasksForStory(Integer.parseInt(storyId));
			ArrayList<DetailedTask> taskDetailList = new ArrayList<DetailedTask>();
			
			for (Task task : taskList) {
				User user = UserDB.getUser(task.getAssignedTo());
				Story s = StoryDB.getStory(task.getStoryId());
				DetailedTask detailedTask = new DetailedTask(task);
				detailedTask.setAssignedTo(user);
				detailedTask.setStory(s);
				taskDetailList.add(detailedTask);
			}
			request.setAttribute("story", story);
			request.setAttribute("task_list", taskDetailList);
			request.setAttribute(Constants.CREATED_BY, createdByUser);
			request.setAttribute(Constants.UPDATED_BY, updatedByUser);
			request.getRequestDispatcher("/story/view_story.jsp").forward(request, response);
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
