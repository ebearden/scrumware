<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Edit Project</title>

<%@ include file="../partials/include_bootstrap_partial.jsp"%>
</head>
<body role="document">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../partials/navigation_bar_partial.jsp"%>

  <div id="main" class="container theme-showcase" role="main">
    <br /><br /><br /><br />
    <h2>#${project.projectId} - ${project.name}&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="edit?project_id=${project.projectId}"><button class="btn btn-default">Edit</button></h2></a>
    <p><h4><small>Created on: ${project.createdOnDateAsString} by ${created_by.name} <br/>
    Last Updated: ${project.updatedOnDateAsString} by ${updated_by.name}</small></h4></p>
    <h3>${project.statusAsString}</h3>
    <div class="span12 "><hr /></div>
    <div class="lead"><p>${project.description}</p></div>
    <div class="span12 "><hr /></div>
    <p class="lead">Project Manager: ${project.project_manager.name}</p>
    <p class="lead">Planned Start Date: ${project.planned_start_date}</p>
    <p class="lead">Planned End Date: ${project.planned_end_date}</p>
    <p class="lead"></p>
  </div>   
  <%@ include file="../partials/include_bootstrap_javascript.jsp"%>
</body>
</html>