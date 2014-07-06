<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Edit</title>

<%@ include file="partials/include_bootstrap_partial.jsp"%>
</head>
<body role="document">
  <%@ include file="partials/navigation_bar_partial.jsp"%>

  <div id="main" class="container theme-showcase" role="main">
    <br /><br /> <br /><br />
    <h2>#${task.getTaskId()} - ${task.getName()}&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="tasks?action=edit&task_id=${task.getTaskId()}"><button class="btn btn-default">Edit</button></h2></a>
    <p><h4><small>Created on: ${task.getCreatedOnDateAsString()} by: ${task.getCreatedBy()} <br/>
    Last Updated: ${task.getUpdatedOnDateAsString()} by ${task.getUpdatedBy()}</small></h4></p>
    <h3>${task.getStatusAsString()}</h3>
    <div class="span12 "><hr /></div>
    <div class="lead"><p>${task.getDescription()}</p></div>
    <div class="span12 "><hr /></div>
    <p class="lead">Assigned to: ${task.getAssignedTo()}</p>
    <p class="lead">Story: ${task.getStoryId()}</p>
    <p class="lead">Work Notes: <br/>${task.getWorkNotes()}</p>
</div>
  <%@ include file="partials/include_bootstrap_javascript.jsp"%>
</body>
</html>