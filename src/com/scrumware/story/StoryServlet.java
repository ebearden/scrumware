package com.scrumware.story;
import java.io.IOException;
import java.sql.Date;
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
import com.scrumware.project.Project;
import com.scrumware.project.ProjectDB;
import com.scrumware.task.DetailedTask;
import com.scrumware.task.Task;
import com.scrumware.task.TaskDB;
import com.scrumware.user.User;
import com.scrumware.user.UserDB;

@WebServlet(name = "StoryServlet", urlPatterns = {"/StoryServlet", "/story/stories"})
public class StoryServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private static final String DATA_TYPE_PARAMETER = "data_type";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StoryServlet() {
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
		String sprintId = request.getParameter(Constants.SPRINT_ID);
		ArrayList<Story> storyList;

		if ("json".equals(dataType) && sprintId != null) {
			storyList = StoryDB.getAllStoriesForSprint(Integer.parseInt(sprintId));
			JSONObject jsonObject = createJSONObject(storyList);
			response.addHeader("Content-Type", "application/json");
			response.getWriter().println(jsonObject);
		} 
		else{
			storyList = StoryDB.getAllStories();
			request.setAttribute("story_list", storyList);
			request.getRequestDispatcher("/story/stories.jsp").forward(request, response);
		}
	}
	private JSONObject createJSONObject(ArrayList<Story> storyList) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Story story : storyList) {
			jsonArray.put(story.toJSON());
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
