<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Edit Story</title>

<%@ include file="../partials/include_bootstrap_partial.jsp"%>
</head>
<body role="document">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../partials/navigation_bar_partial.jsp"%>
  
  <div id="main" class="container theme-showcase" role="main">
  
    <form role="form" id="storyForm" name="edit_story" method="POST" action="edit">
        <!-- Hidden story ID -->
        <input type="text" class="hidden" name="story_id" value="${story.storyID}">
        
    	<table width="100%">
    		<tr>
    			<td style="padding-right:20px!important;">
					<!-- Name -->
	        		<div class="form-group">
	          			<label for="story_name">Name</label>
	          			<input type="text" class="form-control" name="story_name" value="${story.storyName}">
	       			 </div>
    			</td>
    		</tr>
            <tr>
                <td style="padding-right:20px!important;">
                    <div class="form-group">
                      <label for="status_id">Sprint</label> 
                      <select class="form-control" name="sprint_id">
                        <c:forEach var="sprint" items="${sprint}">            
                          <c:choose>
                            <c:when test="${sprint.sprintId == story.sprintID}">
                              <option value="${sprint.sprintId}" selected>${sprint.name}</option>
                            </c:when>
                            <c:otherwise>
                               <option value="${sprint.sprintId}">${sprint.name}</option>
                            </c:otherwise>
                          </c:choose>
                        </c:forEach>
                      </select>
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
			                <c:when test="${status.code == story.statusID}">
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
    			
    		</tr>
    		
        </table>
        
        <!-- Description -->
        <div class="form-group">
          <label for="description">Description</label>
          <textarea class="form-control" rows="3" name="description">${story.description}</textarea>
        </div>
        <div class="form-group">
          <label for="acceptance criteria">Acceptance Criteria</label>
          <textarea class="form-control" rows="3" name="acceptance_criteria">${story.acceptanceCriteria}</textarea>
        </div>
        
        
        <!-- Save -->
        <button type="submit" class="btn btn-primary">Save</button>
        <!-- Delete -->
        <button onclick="return false;" class="btn btn-danger" data-toggle="modal" data-target="#confirmDeleteModal">Delete</button>
    </form><br />
    
  <!-- Confirm Delete Modal -->
    <div class="modal fade" id="confirmDeleteModal" tabindex="-1"
      role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
            <p>     Are you sure you want to delete this story?<br></p>
            <p>All related tasks will be deleted.</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default"
              data-dismiss="modal">No</button>
            <a href="delete?story_id=${story.storyID}">
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
	storyFormValidation();
  });
</script>
  <!-- Below Added for Datepicker Fields -->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
</body>
</html>