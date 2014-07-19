<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>New Project</title>
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
</head>

<body role="document">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../partials/navigation_bar_partial.jsp"%>
  
  <div id="main" class="container theme-showcase" role="main">
  <br /><br /><br /><br />
  
    <form role="form" name="new_project" method="POST" action="new">
        <!-- Name -->
        <div class="form-group">
          <label for="project_name">Name</label>
          <input type="text" class="form-control" name="task_name">
        </div>
        
        <!-- Status -->
        <div class="form-group">
          <label for="status_id">Status</label> 
          <select class="form-control" name="status_id">
            <c:forEach var="s" items="${status}">
                <option value="${s.id}">${s.name}</option>            
            </c:forEach>
          </select>
        </div>
        
        <!-- Project Manager -->
        <div class="form-group">
          <label for="project_manager">Project Manager</label> 
          <select class="form-control" name="project_manager">
            <c:forEach var="u" items="${users}">
                <option value="${u.id}">${u.name}</option>            
            </c:forEach>
          </select>
        </div>
        
        <!-- Planned Start Date -->
        <div class="form-group">
          <label for="planned_start_date">Planned Start Date</label>
          <input type="text" class="form-control" name="planned_start_date">
        </div>
        
        <!-- Planned End Date -->
        <div class="form-group">
          <label for="planned_end_date">Planned End Date</label>
          <input type="text" class="form-control" name="planned_end_date">
        </div>
        
        <!-- Description -->
        <div class="form-group">
          <label for="description">Description</label>
          <textarea class="form-control" rows="3" name="description"></textarea>
        </div>
        
        <!-- Submit -->
        <button type="submit" class="btn btn-primary">Add Task</button>
    </form>
</div>
</body>
</html>