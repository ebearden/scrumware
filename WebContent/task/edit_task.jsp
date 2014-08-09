<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Edit Task</title>
<script>
function addDependency() {
    var options = document.getElementsByName('story_id');
    console.log(options);
    for (var i in options) {
        if (options[i].selected) {
        	var location = 'dependencies?story_id=' + options[i].value;
        	location = location + '&task_id=' + document.getElementsByName('task_id')[0].value;
            window.location = location;
        }
    }
    return false;
}
</script>

<%@ include file="../partials/include_bootstrap_partial.jsp"%>
</head>
<body role="document">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../partials/navigation_bar_partial.jsp"%>
  
  <div id="main" class="container theme-showcase" role="main">
  
    <form role="form" id="taskForm" name="edit_task" method="POST" action="edit">
        <!-- Hidden Task ID -->
        <input type="text" class="hidden" name="task_id" value="${task.taskId}">
        
        <!-- Name -->
        <div class="form-group">
          <label for="task_name">Name</label> <input type="text"
            class="form-control" name="task_name" value="${task.name}">
        </div>
        
        <!-- Description -->
        <div class="form-group">
          <label for="description">Description</label>
          <textarea class="form-control" rows="3" name="description">${task.description}</textarea>
        </div>

        <!-- Assign To -->
        <div class="form-group">
          <label for="assigned_to">Assign To</label> 
          <select class="form-control" name="assigned_to">
            <c:forEach var="u" items="${users}">
              <c:choose>
                <c:when test="${u.id == task.assignedTo}">
                  <option value="${u.id}" selected>${u.username}</option>
                </c:when>
                <c:otherwise>
                  <option value="${u.id}">${u.username}</option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
        </div>
        
        <!-- Story -->
        <div class="form-group">
          <label for="story_name">Story</label> 
          <select class="form-control" name="story_id">
            <c:forEach var="s" items="${stories}">
                <option value="${s.storyID}">${s.storyName}</option>            
            </c:forEach> 
          </select>
        </div>
        
        <div class="form-group">
          <label for="story_name">Status</label> 
          <select class="form-control" name="status_id">
            <c:forEach var="status" items="${status}">
                <c:choose>
                <c:when test="${status.code == task.statusId}">
                  <option value="${status.code}" selected>${status.description}</option>
                </c:when>
                <c:otherwise>
                   <option value="${status.code}">${status.description}</option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
        </div>
        
        <!-- Save -->
        <button type="submit" class="btn btn-primary">Save</button>
        <!-- Add Dependency -->
        <!-- Delete -->
        <button onclick="return false;" class="btn btn-danger" data-toggle="modal" data-target="#confirmDeleteModal">Delete</button>
    </form><br /> 
    <a href="dependencies?story_id=${task.storyId}&task_id=${task.taskId}">
        <button class="btn btn-primary">Add a dependency</button>
    </a>
    
    <!-- Confirm Delete Modal -->
    <div class="modal fade" id="confirmDeleteModal" tabindex="-1"
      role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog" style="left:0px;">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">
              <span aria-hidden="true">&times;</span> <span
                class="sr-only">Close</span>
            </button>
            <h4 class="modal-title" id="myModalLabel">Confirm Deletion</h4>
          </div>
          <div class="modal-body">
            <p>Are you sure you want to delete this task?</p> 
            <p>Its dependencies will be reassigned automatically.</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default"
              data-dismiss="modal">No</button>
            <a href="delete?task_id=${task.taskId}">
              <button type="button" class="btn btn-primary">Delete</button>
            </a>
          </div>
        </div>
      </div>
    </div>

  </div>
  <%@ include file="../partials/include_bootstrap_javascript.jsp"%>
  <script type="text/javascript" src="../js/formValidation.js"></script>
  <script type="text/javascript">
  $(document).ready(function() {
	taskFormValidation();
  });
</script>
</body>
</html>