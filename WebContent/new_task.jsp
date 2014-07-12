<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>New Task</title>
<%@ include file="partials/include_bootstrap_partial.jsp"%>
</head>
<body role="document">
  <%@ include file="partials/navigation_bar_partial.jsp"%>

  <div id="main" class="container theme-showcase" role="main">
  <br /><br /> <br /><br />
  <form role="form" name="new_task" method="POST" action="TaskServlet">
        <div class="form-group">
          <label for="task_name">Name</label> <input type="text"
            class="form-control" name="task_name">
        </div>
        <div class="form-group">
          <label for="description">Description</label>
          <textarea class="form-control" rows="3" name="description"></textarea>
        </div>

        <div class="form-group">
          <label for="assigned_to">Assign To</label> 
          <select
            class="form-control" name="assigned_to">
            <%@ page import="com.scrumware.user.UserBean"%>
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
            <%@ page import="com.scrumware.story.StoryBean"%>
            <%StoryBean storyBean = new StoryBean();%>
            <%for (Integer i : storyBean.getItems().keySet()) {%>
            <option value="<%=i%>">
              <% out.print(storyBean.getItems().get(i)); }%>
            </option>
          </select>
        </div>
        <button type="submit" class="btn btn-primary">Add Task</button>
    </form>
</div>
</body>
</html>