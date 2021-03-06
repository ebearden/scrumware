<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
<title>Users</title>
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
  			<div class="row">
	            <div class="col-md-10">
	            	<h3>User List</h3>
	            </div>
	            <c:if test="${sessionScope.role==1}">
	            	<div class="col-md-2">
		            	<h3>
		            		<a href="../user/adduser">New User</a>
		            	</h3>
	            	</div>
	            </c:if>
            </div>
            <div id="user-table" class="span12">
            <table id="user-table" class="table table-condensed table-hover">
            <thead>
                <tr>
	                <th>User Name</th><th>First Name</th><th>Last Name</th>
	                <th>User Email</th><th>Role</th><th>Status</th>
	                <c:if test="${sessionScope.role==1}">
	                	<th>Password</th><th>Functions</th>
	                </c:if>
                </tr>
            </thead>
                <c:forEach var="u" items="${users}">
                    <tr>
                        <td>${u.username}</td>
                        <td>${u.firstname}</td>
                        <td>${u.lastname}</td>
                        <td>${u.email}</td>
                        <c:forEach var="r" items="${roles}">
                        	<c:if test="${u.role == r.id}">
                        		<td>${r.rolename}</td>
                        	</c:if>
                        </c:forEach>	
                        <c:choose>
                        	<c:when test="${u.active == 0}"><td>Inactive</td></c:when>
                        	<c:otherwise><td>Active</td></c:otherwise>
                        </c:choose>
                        <c:if test="${sessionScope.role==1}">
	                        <td>
								<a href="<c:url value='../user/reset_pass.jsp?id=${u.id}'/>">
	                            	<button class="btn btn-primary">Reset</button>
								</a>
	                        </td>
	                        <td>
	                            <a href="<c:url value='../user/edituser?id=${u.id}'/>">
	                            	<button class="btn btn-primary">Edit Profile</button>
								</a>
	                        </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
		</div>
</div>
<%@ include file="../partials/include_bootstrap_javascript.jsp"%>
</body>
</html>