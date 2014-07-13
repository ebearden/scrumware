<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>New Task</title>
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
</head>

<body role="document">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../partials/navigation_bar_partial.jsp"%>
  
  <div id="main" class="container theme-showcase" role="main">
  <br /><br /><br /><br />
  
    <form role="form" name="new_task" method="POST" action="new">
        <!-- Name -->
        <div class="form-group">
          <label for="task_name">Name</label> <input type="text"
            class="form-control" name="task_name">
        </div>
        
        <!-- Description -->
        <div class="form-group">
          <label for="description">Description</label>
          <textarea class="form-control" rows="3" name="description"></textarea>
        </div>

        <!-- Assign To -->
        <div class="form-group">
          <label for="assigned_to">Assign To</label> 
          <select class="form-control" name="assigned_to">
            <c:forEach var="u" items="${users}">
                <option value="${u.id}">${u.name}</option>            
            </c:forEach>
          </select>
        </div>
        
        <!-- Story -->
        <div class="form-group">
          <label for="story_name">Story</label> 
          <select class="form-control" name="story_id">
            <%-- <c:forEach var="s" items="${stories}">
                <option value="${s.id}">${s.name}</option>            
            </c:forEach> --%>
          </select>
        </div>
        
        <!-- Submit -->
        <button type="submit" class="btn btn-primary">Add Task</button>
    </form>
</div>
</body>
</html>