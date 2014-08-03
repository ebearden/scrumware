package com.scrumware.sprint;

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
 * Servlet implementation class SprintServlet
 * @author John Zorgdrager
 */
@WebServlet(name = "SprintServlet", urlPatterns = {"/SprintServlet", "/sprint/sprints"})
public class SprintServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String DATA_TYPE_PARAMETER = "data_type";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SprintServlet() {
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
		String projectId = request.getParameter(Constants.PROJECT_ID);
		ArrayList<Sprint> sprintList;

		if ("json".equals(dataType) && projectId != null) {
			sprintList = SprintDB.getAllSprintsForProject(Integer.parseInt(projectId));
			JSONObject jsonObject = createJSONObject(sprintList);
			response.addHeader("Content-Type", "application/json");
			response.getWriter().println(jsonObject);
		} else {
			sprintList = SprintDB.getAllSprints();
			request.setAttribute("sprint_list", sprintList);
			request.getRequestDispatcher("/sprint/sprints.jsp").forward(request, response);
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
	
	private JSONObject createJSONObject(ArrayList<Sprint> sprintList) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Sprint sprint : sprintList) {
			jsonArray.put(sprint.toJSON());
		}
		jsonObject.put("result", jsonArray);
		return jsonObject;
	}
}