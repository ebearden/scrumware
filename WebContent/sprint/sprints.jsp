<%@ page language="java" contentType="text/html; charset=US-ASCII"
  pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Sprints</title>
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
</head>
<body role="document">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../partials/navigation_bar_partial.jsp"%>

  <div id="main" class="container theme-showcase" role="main">
    <div id="success"></div>
    <p>
    <a href="new"><button class="btn btn-primary">New Sprint</button></a><br />
    </p>
    <div class="span12"><hr /></div>
    <div id="sprint-table" class="span12">
    <table id="sprint-table" class="table table-condensed table-hover">
      <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Status</th>
        <th><!-- Button column --></th>
      <tr>
      </thead>
      <c:forEach var="s" items="${sprint_list}">
        <tr>
          <td>${s.sprintId}</td>         
          <td>${s.name}</td>              
          <td>${s.startDate}</td>
          <td>${s.endDate}</td>
          <td>
            <a href="view?sprint_id=${p.projectId}">
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