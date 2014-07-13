<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Edit</title>

<%@ include file="../partials/include_bootstrap_partial.jsp"%>
</head>
<body role="document">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../partials/navigation_bar_partial.jsp"%>

  <div id="main" class="container theme-showcase" role="main">
    <br /><br /><br /><br />
    <h2>#${task.taskId} - ${task.name}&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="edit?task_id=${task.taskId}"><button class="btn btn-default">Edit</button></h2></a>
    <p><h4><small>Created on: ${task.createdOnDateAsString} by ${created_by.name} <br/>
    Last Updated: ${task.updatedOnDateAsString} by ${updated_by.name}</small></h4></p>
    <h3>${task.statusAsString}</h3>
    <div class="span12 "><hr /></div>
    <div class="lead"><p>${task.description}</p></div>
    <div class="span12 "><hr /></div>
    <p class="lead">Assigned to: ${assigned_to.name}</p>
    <p class="lead">Story: ${task.storyId}</p>
    <p class="lead">Work Notes: <br/>${task.workNotes}</p>
</div>
  <%@ include file="../partials/include_bootstrap_javascript.jsp"%>
</body>
</html>