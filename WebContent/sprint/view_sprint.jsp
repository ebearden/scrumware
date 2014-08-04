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
    <h2>#${sprint.sprintId} - ${sprint.name}&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="edit?sprint_id=${sprint.sprintId}"><button class="btn btn-default">Edit</button></h2></a>
    <p><h4><small>Created on: ${sprint.created} by ${created_by.firstname} ${created_by.lastname}<br/>
    Last Updated: ${sprint.updated} by ${updated_by.firstname} ${updated_by.lastname} </small></h4></p>
    
    <div class="span12 "><hr /></div>
    <div class="lead"><p>${sprint.description}</p></div>
    <div class="span12 "><hr /></div>
    <p class="lead">Start Date: ${sprint.startDate}</p>
    <p class="lead">End Date: ${sprint.endDate}</p>
    <p class="lead"></p>
  </div>   
  <%@ include file="../partials/include_bootstrap_javascript.jsp"%>
</body>
</html>