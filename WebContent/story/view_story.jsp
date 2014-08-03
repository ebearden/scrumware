<%@ page language="java" contentType="text/html; charset=US-ASCII"
  pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Story | ${story.storyName}</title>
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
</head>
<body role="document">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../partials/navigation_bar_partial.jsp"%>

  <div id="main" class="container theme-showcase" role="main">
    <h2>#${story.storyID} - ${story.storyName}&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="edit?story_id=${story.storyID}"><button class="btn btn-default">Edit</button></h2></a>
    <p><h4><small>Created on: ${story.createdDateAsString} by ${created_by.username} <br/>
    Last Updated: ${story.updatedDateAsString} by ${updated_by.username}</small></h4></p>
    <h3>${story.statusAsString}</h3>
    <div class="span12"><hr /></div>
    <div class="lead"><p>${story.description}</p></div>
    <div class="span12 "><hr /></div>    
    <p class="lead">Tasks in this story:</p>
    <div id="task-table" class="span12">
    <table id="task-table" class="table table-condensed table-hover">
      <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Assigned To</th>
        <th>Story</th>
        <th>Status</th>
        <th><!-- Button column --></th>
      <tr>
      </thead>
      <c:forEach var="t" items="${task_list}">
        <tr>
          <td>${t.taskId}</td>          
          <td>${t.name}</td>          
          <td>${t.description}</td>          
          <td>${t.assignedToUser.username}</td>          
          <td>${t.story.storyName}</td>
          <td>${t.status}</td>
          <td>
            <a href="../task/view?task_id=${t.taskId}">
                <button class="btn btn-primary">View</button>
            </a>
          </td>
        </tr>
      </c:forEach>
    </table>
    </div>
  </div>

  <%@ include file="../partials/include_bootstrap_javascript.jsp"%>
</body>
</html>