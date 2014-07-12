<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Edit</title>
<script>
function startUp() {
	var options = document.getElementsByName('assigned_to');
	for (var i = 0; i < options[0].length; i++) {
		if (parseInt(options[0][i].value) == <%=request.getAttribute("assigned_to")%>) {
			options[0][i].selected = true;
			break;
		}
	}
	
	options = document.getElementsByName('story_id');
	for (var i = 0; i < options[0].length; i++) {
		console.log(parseInt(options[0][i].value));
		console.log(parseInt(<%=request.getAttribute("story_id")%>));
		
        if (parseInt(options[0][i].value) == <%=request.getAttribute("story_id")%>) {
            options[0][i].selected = true;
           break;
        }
    }
	
}
</script>

<%@ include file="partials/include_bootstrap_partial.jsp"%>
</head>
<body role="document" onload="startUp()">
  <%@ include file="partials/navigation_bar_partial.jsp"%>

  <div id="main" class="container theme-showcase" role="main">
  <br /><br /> <br /><br />
    <form role="form" name="new_task" method="POST" action="TaskServlet">
  
          <input type="text" class="hidden" name="task_id" value="${task.getTaskId()}">
          <div class="form-group">
            <label for="task_name">Name</label> <input type="text"
              class="form-control" name="task_name" value="${task.getName()}">
          </div>
          <div class="form-group">
            <label for="description">Description</label>
            <textarea class="form-control" rows="3" name="description">${task.getDescription()}</textarea>
          </div>

          <div class="form-group">
            <label for="assigned_to">Assign To</label> 
            <select
              class="form-control" name="assigned_to">
              <%@ page import="com.scrumware.javabeans.UserBean"%>
              <%
                UserBean userBean = new UserBean();
              %>
              <%for (int i = 0; i < userBean.getItems().size(); i++) {%>
              <option value="<%=i+2%>">
                <% out.print(userBean.getItems().get(i)); %>
              </option>
              <%}%>
            </select>

          </div>
          <div class="form-group">
            <label for="story_name">Story</label> <select
              class="form-control" name="story_id">
              <%@ page import="com.scrumware.javabeans.StoryBean"%>
              <%StoryBean storyBean = new StoryBean();%>
              <%for (Integer i : storyBean.getItems().keySet()) {%>
              <option value="<%=i%>">
                <% out.print(storyBean.getItems().get(i)); }%>
              </option>
            </select>
          </div>
              <button type="submit" class="btn btn-primary">Save</button>
        </form>
        <br />
        <button class="btn btn-danger" data-toggle="modal" data-target="#confirmDeleteModal">Delete</button>
        
        <!--Confirm Deletion Modal-->
        <div class="modal fade" id="confirmDeleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Close</span>
                </button>
              </div>
              <div class="modal-body">
                <p>Are you sure you want to delete this task?</p>
                <h4 class="modal-title">Confirm Delete</h4>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                <a href="tasks?action=delete&task_id=${task.getTaskId()}">
                    <button type="button" class="btn btn-primary">Delete</button>
                </a>
              </div>
            </div>
          </div>
        </div>
        <%@ include file="partials/include_bootstrap_javascript.jsp"%>
</div>
</body>
</html>