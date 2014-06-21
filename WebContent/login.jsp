<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="partials/include_bootstrap_partial.jsp" %>

<title>SCRUMware | Login</title>
</head>
<body role="document">

    <%@ include file="partials/navigation_bar_partial.jsp" %>

    <div class="container theme-showcase" role="main">

        <!-- Main jumbotron for a primary marketing message or call to action -->
        <div class="jumbotron">
            <p>
            <form name="login" action=Login method="POST">
                <div class="input-group">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span> 
                    <input type="text" name="user_name" class="form-control" placeholder="Username"/>
                </div>
                <div class="input-group">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-eye-close"></span></span> 
                    <input type="password" name="password" class="form-control" placeholder="Password"/>
                </div>
                <br />
                <input class="btn btn-success" role="button" type="submit" value="Login" />
            </form>
            </p>
        </div>
        <%@ include file="partials/include_bootstrap_javascript.jsp" %>
  </body>
</html>