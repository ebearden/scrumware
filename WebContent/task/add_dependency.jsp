<%@ page language="java" contentType="text/html; charset=US-ASCII"
  pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Tasks</title>
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
</head>
<body role="document">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../partials/navigation_bar_partial.jsp"%>

  <div id="main" class="container theme-showcase" role="main">
    <div id="task-table" class="span12">
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
      <c:forEach var="t" items="${task_list}">
        <tr>
          <td>${t.taskId}</td>          
          <td>${t.name}</td>          
          <td>${t.description}</td>          
          <td>${t.assignedToUser.username}</td>          
          <td>${t.status}</td>
          <td>
          <form name="new_task" method="POST" action="dependencies">
            <input type="text" name="dependent_task_id" value="${t.taskId}" class="hidden" />
            <input type="text" name="task_id" value="${task_id}" class="hidden" />
            <button class="btn btn-primary">Add dependency</button>
            </form>
          </td>
        </tr>
      </c:forEach>
    </table>
    </div>
  </div>

  <%@ include file="../partials/include_bootstrap_javascript.jsp"%>
</body>
</html>