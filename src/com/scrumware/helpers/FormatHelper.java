package com.scrumware.helpers;

import java.util.List;

import com.scrumware.config.Status;
import com.scrumware.javabeans.UserBean;
import com.scrumware.jdbc.dto.Task;

public class FormatHelper {
	
	public static String taskListToHTMLTable(List<Task> taskList, List<String> names) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<table class=\"table table-striped table-bordered\">");
		// Setup the table header
		stringBuilder.append("<thead>");
		for (String s : names) {
			stringBuilder.append("<td>" + s + "</td>");
		}
		stringBuilder.append("</thead>");
		
		// Setup table rows.
		for (Task task : taskList) {
			UserBean userBean = new UserBean(task.getAssignedTo());
			String status = Status.values()[task.getStatusId() - 1].getDescription();
			
			stringBuilder.append("<tr>");
			stringBuilder.append("<td>" + task.getTaskId() + "</td>");
			stringBuilder.append("<td>" + task.getName() + "</td>");
			stringBuilder.append("<td>" + task.getDescription() + "</td>");
			stringBuilder.append("<td>" + userBean.getItems().get(0) + "</td>");
			stringBuilder.append("<td>" + task.getWorkNotes() + "</td>");
			stringBuilder.append("<td>" + status + "</td>");
			stringBuilder.append("</tr>");
		}
		stringBuilder.append("</table>");
		
		return stringBuilder.toString();
	}
}
