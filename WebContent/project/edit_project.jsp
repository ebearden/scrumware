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
  
    <form role="form" name="edit_project" method="POST" action="edit">
        <!-- Hidden Project ID -->
        <input type="text" class="hidden" name="project_id" value="${project.projectId}">
        
    	<table width="100%">
    		<tr>
    			<td style="padding-right:20px!important;">
					<!-- Name -->
	        		<div class="form-group">
	          			<label for="project_name">Name</label>
	          			<input type="text" class="form-control" name="project_name" value="${project.name}">
	       			 </div>
    			</td>
    			<td>
    				<!-- Planned Start Date -->
	        		<div class="form-group">
	          			<label for="planned_start_date">Planned Start Date</label>
	          			<input id="start_date" type="text" class="form-control" name="planned_start_date" value="${project.startDate}">
	        		</div>
    			</td>
    		</tr>
    		<tr>
    			<td style="padding-right:20px!important;">
    				<div class="form-group">
			          <label for="status_id">Status</label> 
			          <select class="form-control" name="status_id">
			            <c:forEach var="status" items="${status}">
			                <c:choose>
			                <c:when test="${status.code == project.statusId}">
			                  <option value="${status.code}" selected>${status.description}</option>
			                </c:when>
			                <c:otherwise>
			                   <option value="${status.code}">${status.description}</option>
			                </c:otherwise>
			              </c:choose>
			            </c:forEach>
			          </select>
			        </div>
    			</td>
    			<td>
    				<!-- Planned End Date -->
	        		<div class="form-group">
	         			<label for="planned_end_date">Planned End Date</label>
	          			<input id="end_date" type="text" class="form-control" name="planned_end_date" value="${project.endDate}">
	        		</div>
    			</td>
    		</tr>
    		<tr>
    			<td style="padding-right:20px!important;">
			        <div class="form-group">
			          <label for="project_manager">Project Manager</label> 
			          <select class="form-control" name="project_manager">
			            <c:forEach var="u" items="${users}">
			              <c:choose>
			                <c:when test="${u.id == project.projectManagerId}">
			                  <option value="${u.id}" selected>${u.username}</option>
			                </c:when>
			                <c:otherwise>
			                  <option value="${u.id}">${u.username}</option>
			                </c:otherwise>
			              </c:choose>
			            </c:forEach>
			          </select>
			        </div>
		        </td>
    		</tr>
        </table>
        
        <!-- Description -->
        <div class="form-group">
          <label for="description">Description</label>
          <textarea class="form-control" rows="3" name="description">${project.description}</textarea>
        </div>
        
        <!-- Save -->
        <button type="submit" class="btn btn-primary">Save</button>
        <!-- Delete -->
        <button onclick="return false;" class="btn btn-danger" data-toggle="modal" data-target="#confirmDeleteModal">Delete</button>
    </form><br />
    
  <!-- Confirm Delete Modal -->
    <div class="modal fade" id="confirmDeleteModal" tabindex="-1"
      role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="background-image: url('http://i.imgur.com/ECYOWOC.gif'); background-size:cover;">
      <div class="modal-dialog" style="left: 0px;">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">
              <span aria-hidden="true">&times;</span> <span
                class="sr-only">Close</span>
            </button>
            <h4 class="modal-title" id="myModalLabel">Confirm Deletion</h4>
          </div>
          <div class="modal-body">
            <p>     Are you sure you want to delete this project?<br></p>
            <p>All related sprints, stories, and tasks will be deleted.</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default"
              data-dismiss="modal">No</button>
            <a href="delete?project_id=${project.projectId}">
              <button type="button" class="btn btn-primary">Delete</button>
            </a>
          </div>
        </div>
      </div>
    </div>

  </div>
  <%@ include file="../partials/include_bootstrap_javascript.jsp"%>
  <!-- Below Added for Datepicker Fields -->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script>
  $(function() {
    $( "#start_date" ).datepicker({ dateFormat: 'yy-mm-dd'});
    $( "#end_date").datepicker({ dateFormat: 'yy-mm-dd'});
  });
  </script>
</body>
</html>