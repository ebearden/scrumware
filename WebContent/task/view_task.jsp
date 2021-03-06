<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
<title>Task | ${task.taskId}</title>

</head>
<body role="document">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../partials/navigation_bar_partial.jsp"%>

  <div id="main" class="container theme-showcase" role="main">
    <h2>#${task.taskId} - ${task.name}&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="edit?task_id=${task.taskId}"><button class="btn btn-default">Edit</button></h2></a>
    <p><h4><small>Created on: ${task.createdOnDateAsString} by ${created_by.username} <br/>
    Last Updated: ${task.updatedOnDateAsString} by ${updated_by.username}</small></h4></p>
    <h3>${task.statusAsString}</h3>
    <c:if test="${err_msg != null}">
      <p class="alert alert-danger">${err_msg}</p>
    </c:if>
    <div class="span12 "><hr /></div>
    <div class="lead"><p>${task.description}</p></div>
    <div class="span12 "><hr /></div>
    <p class="lead">Assigned to: ${assigned_to.firstname} ${assigned_to.lastname}</p>
    <p class="lead">Story: ${story.storyName}</p>
    <p class="lead">Work Notes: <br/>${task.workNotes}</p>
    <p class="lead">Depends On:</p>
    
    <div id="dependent-table" class="span12">
    <table id="task-table" class="table table-condensed table-hover">
      <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Assigned To</th>
        <th>Status</th>
        <th><!-- Button column --></th>
      <tr>
      </thead>
      <c:forEach var="t" items="${dependencies}">
        <tr>
          <td>${t.taskId}</td>          
          <td>${t.name}</td>          
          <td>${t.description}</td>          
          <td>${t.assignedToUser.username}</td>          
          <td>${t.status}</td>
          <td>
            <a href="view?task_id=${t.taskId}">
                <button class="btn btn-primary">View</button>
            </a>
            <a href="dependencies?action=delete&task_id=${task.taskId}&depends_on=${t.taskId}">
                <button class="btn btn-danger">Delete</button>
            </a>
          </td>
        </tr>
      </c:forEach>
    </table>
    </div>
  </div>
    
</div>
  <%@ include file="../partials/include_bootstrap_javascript.jsp"%>
</body>
</html>