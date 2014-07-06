<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Edit</title>

<%@ include file="partials/include_bootstrap_partial.jsp"%>
</head>
<body role="document">
  <%@ include file="partials/navigation_bar_partial.jsp"%>

  <div id="main" class="container theme-showcase" role="main">
    <br /><br /> <br /><br />
    <h2>#${task_id} - ${task_name}</h2>
    <p><h3><small>Created on:test Created By:test Last Updated: test by test</small></h3></p>
    <div class="span12"><hr /></div>
    <div class="well"><p>${description}</p></div>
    <p>Assigned to: </p>
    <p>Story: </p>
    <p>Work Notes:</p>
</div>
  <%@ include file="partials/include_bootstrap_javascript.jsp"%>
</body>
</html>