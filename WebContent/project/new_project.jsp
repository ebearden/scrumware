<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>New Project</title>
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
<!-- Below Added for Datepicker Fields -->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script>
  $(function() {
    $( "#start_date" ).datepicker();
    $( "#end_date").datepicker();
  });
  </script>
</head>

<body role="document">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../partials/navigation_bar_partial.jsp"%>
  <div id="main" class="container theme-showcase" role="main">
  <br /><br /><br /><br />
  <script>
  </script>
    <form role="form" name="new_project" method="POST" action="new">
    	<table width="100%">
    		<tr>
    			<td style="padding-right:20px!important;">
					<!-- Name -->
	        		<div class="form-group">
	          			<label for="project_name">Name</label>
	          			<input type="text" class="form-control" name="task_name">
	       			 </div>
    			</td>
    			<td>
    				<!-- Planned Start Date -->
	        		<div class="form-group">
	          			<label for="planned_start_date">Planned Start Date</label>
	          			<input id="start_date" type="text" class="form-control" name="planned_start_date">
	        		</div>
    			</td>
    		</tr>
    		<tr>
    			<td style="padding-right:20px!important;">
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
    			</td>
    			<td>
    				<!-- Planned End Date -->
	        		<div class="form-group">
	         			<label for="planned_end_date">Planned End Date</label>
	          			<input id="end_date" type="text" class="form-control" name="planned_end_date">
	        		</div>
    			</td>
    		</tr>
    		<tr>
    			<td style="padding-right:20px!important;">     
			        <!-- Project Manager -->
			        <div class="form-group">
			          <label for="project_manager">Project Manager</label> 
			          <select class="form-control" name="project_manager">
			            <c:forEach var="u" items="${users}">
			                <option value="${u.id}">${u.name}</option>            
			            </c:forEach>
			          </select>
			        </div>
    			</td>
    		</tr>
        </table>
        
        <!-- Description -->
        <div class="form-group">
          <label for="description">Description</label>
          <textarea class="form-control" rows="3" name="description"></textarea>
        </div>
        
        <!-- Submit -->
        <button type="submit" class="btn btn-primary">Add Project</button>
    </form>
</div>
</body>
</html>