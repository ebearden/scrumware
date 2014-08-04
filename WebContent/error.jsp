<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="partials/include_bootstrap_partial.jsp" %>
<link rel="stylesheet" href="css/lavish-theme.css">

<title>SCRUMware | Error</title>
</head>
<body role="document">

    <%@ include file="partials/navigation_bar_partial.jsp" %>

    <div class="container theme-showcase" role="main">

        <!-- Main jumbotron for a primary marketing message or call to action -->
        <div class="jumbotron">
             <p class="lead center">Oops... Something went wrong.</p>
             <a href="/SCRUMware/task/assigned_tasks"><button class="btn btn-primary">Home</button></a>
        </div>
     </div>
     <%@ include file="partials/include_bootstrap_javascript.jsp" %>
  </body>
</html>