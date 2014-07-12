<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Story</title>
<%@ include file="partials/include_bootstrap_partial.jsp"%>
</head>
<body role="document">
  <%@ include file="partials/navigation_bar_partial.jsp"%>

  <div id="main" class="container theme-showcase" role="main">
    <br /> <br />
    <div id="success"></div>
    <p>
    <br /> <br />
    <button class="btn btn-primary" data-toggle="modal" data-target="#newTaskModal">New Story</button><br />
    </p>
    <div class="span12"><hr /></div>
    <div id="task-table" class="span12">${task_list}</div>
  </div>

</body>
</html>