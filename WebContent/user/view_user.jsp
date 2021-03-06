<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
<title>Your Profile</title>
</head>
<body>
<%-- 
    Document   : ViewUsers
    Created on : Jun 28, 2014
    Author     : eakubic

    This page uses custom tags with the prefix c
    It displays Product data pulled from the db and
    provides links to CRUD operations


--%>


<%@ include file="../partials/navigation_bar_partial.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

  <div id="main" class="container theme-showcase" role="main">
  
  			
  			<div class = "row">
	            <div class="col-md-4 col-md-offset-4">
	            	<h3 class="text-center">User Profile</h3>
	            </div>
            </div>
            <div class = "row">
            <br/>
            <c:if test="${msg != null}">
      			<h3 class="alert alert-success text-center">${msg}</h3>
    		</c:if>
            </div>
            <div id="user-profile-table" class="col-md-4 col-md-offset-4">
            <table id="user-profile-table" class="table">       
                <tr><th>User Name</th><td>${requestScope.user.username}</td></tr>
	            <tr><th>First Name</th><td>${requestScope.user.firstname}</td></tr>
	            <tr><th>Last Name</th><td>${requestScope.user.lastname}</td></tr>
	            <tr><th>User Email</th><td>${requestScope.user.email}</td></tr>
                <tr><th>User Role</th>
                	<c:forEach var="r" items="${roles}">
                       	<c:if test="${requestScope.user.role == r.id}">
                       		<td>${r.rolename}</td>
                       	</c:if>
                    </c:forEach>
                </tr>
            </table>
		</div>
</div>
<%@ include file="../partials/include_bootstrap_javascript.jsp"%>
</body>
</html>