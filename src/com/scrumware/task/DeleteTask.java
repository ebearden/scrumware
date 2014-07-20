package com.scrumware.task;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scrumware.config.Constants;

/**
 * Servlet implementation class DeleteTask
 * @author Elvin Bearden
 */
@WebServlet(name = "DeleteTask", urlPatterns = {"/DeleteTask", "/task/delete"})
public class DeleteTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteTask() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String taskId = request.getParameter(Constants.TASK_ID);
		if (taskId != null) {
			Task task = new Task();
			task.setTaskId(Integer.parseInt(taskId));
			boolean result;
			if (TaskHelper.reassignDependenciesForTask(task.getTaskId())) {
				result = TaskDB.deleteTask(task);
			} else {
				result = false;
			}
			String message;
			if (result) {
				message = String.format("Task %s successfully deleted.", task.getTaskId());			
			} else {
				message = "Failed to delete the task.";
			}
			request.setAttribute("message", message);
			request.getRequestDispatcher("/task/delete_task.jsp").forward(request, response);
		} 
	}

}
