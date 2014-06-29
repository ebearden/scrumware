<%@ page language="java" contentType="text/html; charset=US-ASCII"
  pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Tasks</title>
<%@ include file="partials/include_bootstrap_partial.jsp"%>
</head>
<body role="document">
  <%@ include file="partials/navigation_bar_partial.jsp"%>

  <div class="container theme-showcase" role="main">
      <br /> <br />
        <div class="alert alert-success hidden">${msg}</div>
      <p>
      <h2>New Task</h2>
      <div class="span12"><hr/></div>
      </p>
      <div class="col-md-5">
        <form role="form" method="put" action="TaskServlet">
          <div class="form-group">
            <label for="name">Name</label> <input type="text"
              class="form-control" name="task_name" placeholder="Task Name">
          </div>
          <div class="form-group">
            <label for="name">Description</label>
            <textarea class="form-control" rows="3" name="description"></textarea>
          </div>

          <div class="form-group">
            <label for="name">Assign To</label>
            <select class="form-control" name="assigned_to">
              <%@ page import="com.scrumware.javabeans.UserBean"%>
              <%
              	UserBean userBean = new UserBean();
              	userBean.getItems();
              %>
              <%for (String s : userBean.getItems()) {%>
              <option><% out.print(s); %></option>
              <%}%>
            </select>

          </div>
          <div class="form-group">
            <label for="name">Story</label> <select class="form-control"
              name="story_name">
              <%@ page import="com.scrumware.javabeans.StoryBean"%>
              <%StoryBean storyBean = new StoryBean();%>
              <%for (Integer i : storyBean.getItems().keySet()) {%>
              <option><% out.print(storyBean.getItems().get(i)); }%></option>
            </select>
          </div>
          <button type="submit" class="btn btn-default">Add Task</button>
        </form>
        <div class="span12"><hr/></div>
        <button class="btn btn-default" data-toggle="modal" data-target="#dependencyModal">Add a Dependency</button>
        <div class="modal fade" id="dependencyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Add Dependency</h4>
              </div>
              <div class="modal-body">
              <%@ page import="com.scrumware.javabeans.TaskBean"%>
              <%TaskBean taskBean = new TaskBean();%>
              <%for (Integer i : taskBean.getItems().keySet()) {%>
              <% out.print(taskBean.getItems().get(i)); }%>
              
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
              </div>
            </div>
          </div>
        </div>

      </div>      
      <div class="col-md-7">
      ${task_list}
      </div>
    </div>

  <%@ include file="partials/include_bootstrap_javascript.jsp"%>
</body>
</html>