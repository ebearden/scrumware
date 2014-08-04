<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>New Sprint</title>
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
<!-- Below Added for Datepicker Fields -->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
 
</head>

<body role="document">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../partials/navigation_bar_partial.jsp"%>
  <div id="main" class="container theme-showcase" role="main">
  <script>
  </script>
    <form role="form" name="new_sprint" method="POST" action="new">
    	<table width="100%">
    		<tr>
    			<td style="padding-right:20px!important;">
					<!-- Name -->
	        		<div class="form-group">
	          			<label for="sprint_name">Name</label>
	          			<input type="text" class="form-control" name="sprint_name">
	       			 </div>
    			</td>
    			<td>
    				<!-- Planned Start Date -->
	        		<div class="form-group">
	          			<label for="start_date">Start Date</label>
	          			<input id="start_date" type="text" class="form-control" name="start_date"  readonly="true" style="cursor: default; background-color: white;">
	        		</div>
    			</td>
    		</tr>
    		<tr>
    			<td style="padding-right:20px!important;">
			        <div class="form-group">
			          <label for="status_name">Status</label> 
			          <select class="form-control" name="status_id">
			            <c:forEach var="status" items="${status}">
			                <c:choose>
			                <c:when test="${status.code == sprint.statusId}">
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
	         			<label for="end_date">End Date</label>
	          			<input id="end_date" type="text" class="form-control" name="end_date">
	        		</div>
    			</td>
    		</tr>
    		<tr>
    			<div class="form-group">
          			<label for="project_id">Assign To Project</label> 
          			<select class="form-control" name="project_id">
            			<c:forEach var="p" items="${projects}">
                			<option value="${p.projectId}">${p.name}</option>            
            			</c:forEach>
          			</select>
        		</div>
    			
    			
    		</tr>
        </table>
        
        <!-- Description -->
        <div class="form-group">
          <label for="description">Description</label>
          <textarea class="form-control" rows="3" name="description"></textarea>
        </div>
        
        <!-- Submit -->
        <button type="submit" class="btn btn-primary">Add Sprint</button>
    </form>
</div>
 <%@ include file="../partials/include_bootstrap_javascript.jsp"%>
  <script type="text/javascript" src="../js/formValidation.js"></script>
  <script>
  $(document).ready(function() {
    projectFormValidation();
  });
  </script>
<!-- Below Added for Datepicker Fields -->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script>
  $(function() {
    $( "#start_date" ).datepicker({ dateFormat: 'yy-mm-dd'});
    $( "#end_date").datepicker({ dateFormat: 'yy-mm-dd', minDate: new Date()});
  });
  </script>
</body>
</html>