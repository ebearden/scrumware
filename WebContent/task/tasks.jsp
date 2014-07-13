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
    <br /> <br />
    <div id="success"></div>
    <p>
    <br /> <br />
    <a href="task/new"><button class="btn btn-primary">New Task</button></a><br />
    </p>
    <div class="span12"><hr /></div>
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
          <td>${t.assignedTo}</td>          
          <td>${t.storyId}</td>
          <td>${t.statusAsString}</td>
          <td>
            <a href="view?task_id=${t.taskId}">
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