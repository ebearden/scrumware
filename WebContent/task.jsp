<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Tasks</title>
<%@ include file="partials/include_bootstrap_partial.jsp" %>
</head>
<body role="document">
<%@ include file="partials/navigation_bar_partial.jsp" %>

<div class="container theme-showcase" role="main">
<div class="jumbotron">
<br/><br/>

<form role="form" method="post" action="TaskServlet">
  <div class="form-group">
    <label for="name">Name</label>
    <input type="text" class="form-control" name="name" placeholder="Task Name">
  </div>
   <div class="form-group">
    <label for="name">Description</label>
    <textarea class="form-control" rows="3" name="description"></textarea>
  </div>
    
  <div class="form-group">
    <label for="name">Assign To</label>
    <jsp:useBean id="obj" class="com.scrumware.javabeans.UserBean" scope="page"/>
	<select class="form-control" name="assigned_to">
	<%@ page import="com.scrumware.javabeans.UserBean" %>
    <%UserBean userBean = new UserBean();
    userBean.getItems();%>
    <%for (String s : userBean.getItems()){%>
        <option><%out.print(s);%></option>
    <%}%>
	</select>

  </div>
  <div class="form-group">
    <label for="name">Story</label>
    <select class="form-control" name="story_id">
        <%@ page import="com.scrumware.javabeans.StoryBean" %>
    <%StoryBean storyBean = new StoryBean();
    storyBean.getItems();%>
    <%for (String s : storyBean.getItems()){%>
        <option><%out.print(s);%></option>
    <%}%>
    </select>
  </div>
  <button type="submit" class="btn btn-default">Add Task</button>
</form>
</div>
</div>
<%@ include file="partials/include_bootstrap_javascript.jsp" %>
</body>
</html>