package com.scrumware.helpers;

import java.util.List;

import com.scrumware.jdbc.dto.Task;

public class FormatHelper {
	
	public static String taskListToHTMLTable(List<Task> taskList) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<table>");
		for (Task task : taskList) {
			stringBuilder.append("<tr>");
			stringBuilder.append("<td>" + task.getTaskId() + "</td>");
			stringBuilder.append("<td>" + task.getName() + "</td>");
			stringBuilder.append("<td>" + task.getDescription() + "</td>");
			stringBuilder.append("<td>" + task.getStatus() + "</td>");
			stringBuilder.append("<td>" + task.getAssignedTo() + "</td>");
			stringBuilder.append("<td>" + task.getWorkNotes() + "</td>");
			stringBuilder.append("<td>" + task.getDependentCount() + "</td>");
			stringBuilder.append("</tr>");
		}
		stringBuilder.append("</table>");
		return stringBuilder.toString();
	}
}
