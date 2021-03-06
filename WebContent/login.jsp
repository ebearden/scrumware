<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="partials/include_bootstrap_partial.jsp" %>
<link rel="stylesheet" href="css/lavish-theme.css">

<title>SCRUMware | Login</title>
</head>
<body role="document">
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    

    <div class="container theme-showcase" role="main">

        <!-- Main jumbotron for a primary marketing message or call to action -->
        <div class="jumbotron">
        	<h2>Welcome to SCRUMware</h2>
        	<br/>
        	<c:if test="${errmsg != null}">
      			<p class="alert alert-danger">${errmsg}</p>
    		</c:if>
            <p>
            <form name="login" action="Login" method="post">
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