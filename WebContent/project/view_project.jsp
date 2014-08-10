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
    <h2>#${project.projectId} - ${project.name}&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="edit?project_id=${project.projectId}"><button class="btn btn-default">Edit</button></h2></a>
    <p><h4><small>Created on: ${project.createdOnDateAsString} by ${created_by.firstname} ${created_by.lastname}<br/>
    Last Updated: ${project.updatedOnDateAsString} by ${updated_by.firstname} ${updated_by.lastname} </small></h4></p>
    <h3>${project.statusAsString}</h3>
    <c:if test="${err_msg != null}">
      <p class="alert alert-danger">${err_msg}</p>
    </c:if>
    <div class="span12 "><hr /></div>
    <div class="lead"><p>${project.description}</p></div>
    <div class="span12 "><hr /></div>
    <p class="lead">Project Manager: ${projectManager.firstname} ${projectManager.lastname}</p>
    <p class="lead">Planned Start Date: ${project.startDate}</p>
    <p class="lead">Planned End Date: ${project.endDate}</p>
    <p class="lead"></p>
    <div id="story-table" class="span12">
    <table id="story-table" class="table table-condensed table-hover">
      <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Status</th>
        <th><!-- Button column --></th>
      <tr>
      </thead>
      <c:forEach var="s" items="${story_list}">
        <tr>
          <td>${s.storyID}</td>          
          <td>${s.storyName}</td>          
          <td>${s.description}</td>          
          <td>${s.getStatusAsString()}</td>
          <td>
            <a href="../story/view?story_id=${s.storyID}">
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