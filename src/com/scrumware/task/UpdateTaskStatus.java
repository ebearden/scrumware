package com.scrumware.task;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scrumware.config.Constants;
import com.scrumware.config.Status;
import com.scrumware.login.SessionHelper;

/**
 * Servlet implementation class UpdateTaskStatus
 */
@WebServlet(name = "UpdateTaskStatus", urlPatterns = {"/UpdateTaskStatus", "/task/UpdateTaskStatus"})
public class UpdateTaskStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTaskStatus() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!SessionHelper.validateSession(request, response)) {
			return;
		}
		
		String newStatus = "";
		String taskId = request.getParameter("taskID");
		String status = request.getParameter("statusID");
		
		//convert <div> id to status
		if (status.equals("draggablePanelList1")){
			newStatus = "1";
		}else if (status.equals("draggablePanelList2")){
			newStatus = "2";
		}else if (status.equals("draggablePanelList3")){
			newStatus = "3";
		}else if (status.equals("draggablePanelList4")){
			newStatus =  "4";
		}
		
		
		Task task = TaskDB.getTask(Integer.parseInt(taskId));
		if (newStatus != null && Integer.parseInt(newStatus) == Status.DONE.getCode()) {
			if (TaskHelper.closeTask(Integer.parseInt(taskId))) {
				task.setStatusId(newStatus != null ? Integer.parseInt(newStatus) : 1);
			} else {
				int oldStatusId = TaskDB.getTask(Integer.parseInt(taskId)).getStatusId();
				task.setStatusId(oldStatusId);
				response.setContentType("text/plain");
				response.getWriter().write("fail");
			}
		} else {
			task.setStatusId(newStatus != null ? Integer.parseInt(newStatus) : 1);
		}
		
		
		
		//task.setStatusId(newStatus);
		
		
		
		HttpSession session = request.getSession(false);
		Integer userId = null;
		if (session != null) {
			userId = (Integer)session.getAttribute("id");
		} else if (request.getParameter(Constants.USER_ID) != null) {
			userId = Integer.parseInt(request.getParameter(Constants.USER_ID));
		}
		
		// Handle Created by, Updated By,
		if (userId != null) {
			if (task.getTaskId() == null) {
				// This is an insert. Set both created and updated
				task.setCreatedBy(userId);
				task.setUpdatedBy(userId);	
			} else {
				// This is an update. Just set updated.
				task.setUpdatedBy(userId);
			}
		}
		
		Task savedTask = TaskDB.saveTask(task);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!SessionHelper.validateSession(request, response)) {
			return;
		}
		
		String newStatus = "";
		String taskId = request.getParameter("taskID");
		String status = request.getParameter("statusID");
		
		//convert <div> id to status
		if (status.equals("draggablePanelList1")){
			newStatus = "1";
		}else if (status.equals("draggablePanelList2")){
			newStatus = "2";
		}else if (status.equals("draggablePanelList3")){
			newStatus = "3";
		}else if (status.equals("draggablePanelList4")){
			newStatus =  "4";
		}
		
		
		Task task = TaskDB.getTask(Integer.parseInt(taskId));
		if (newStatus != null && Integer.parseInt(newStatus) == Status.DONE.getCode()) {
			if (TaskHelper.closeTask(Integer.parseInt(taskId))) {
				task.setStatusId(newStatus != null ? Integer.parseInt(newStatus) : 1);
			} else {
				int oldStatusId = TaskDB.getTask(Integer.parseInt(taskId)).getStatusId();
				task.setStatusId(oldStatusId);
				response.setContentType("text/plain");
				response.getWriter().write("fail");
			}
		} else {
			task.setStatusId(newStatus != null ? Integer.parseInt(newStatus) : 1);
		}
		
		
		
		//task.setStatusId(newStatus);
		
		
		
		HttpSession session = request.getSession(false);
		Integer userId = null;
		if (session != null) {
			userId = (Integer)session.getAttribute("id");
		} else if (request.getParameter(Constants.USER_ID) != null) {
			userId = Integer.parseInt(request.getParameter(Constants.USER_ID));
		}
		
		// Handle Created by, Updated By,
		if (userId != null) {
			if (task.getTaskId() == null) {
				// This is an insert. Set both created and updated
				task.setCreatedBy(userId);
				task.setUpdatedBy(userId);	
			} else {
				// This is an update. Just set updated.
				task.setUpdatedBy(userId);
			}
		}
		
		Task savedTask = TaskDB.saveTask(task);
	}

}
