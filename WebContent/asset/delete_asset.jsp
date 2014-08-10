<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
<title>Delete Asset</title>
</head>
<body>
<%-- 
    Document   : Delete Asset
    Created on : Aug 10, 2014
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
	            	<h3 class="text-center">Asset Information</h3>
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
                <tr><th>File Name</th><td>${asset.name}</td></tr>
	            <tr><th>File Description</th><td>${asset.description}</td></tr>
	            <tr><th>Created On</th><td>${asset.created}</td></tr>
                <tr><th>Created By</th>
                	<c:forEach var="u" items="${users}">
                       	<c:if test="${asset.createdBy == u.id}">
                       		<td>${u.username}</td>
                       	</c:if>
                    </c:forEach>
                </tr>
	            <tr><th>Updated On</th><td>${asset.created}</td></tr>
                <tr><th>Updated By</th>
                	<c:forEach var="u" items="${users}">
                       	<c:if test="${asset.updatedBy == u.id}">
                       		<td>${u.username}</td>
                       	</c:if>
                    </c:forEach>
                </tr>
            </table>
		</div>
		<div class="col-md-2 col-md-offset-5">
			<a href="../asset/delete?id=${asset.assetID}&confirm=true">
            	<button class="btn btn-success">Delete</button>
            </a>
            <a href="../asset/assets?project_id=${asset.project}">
            	<button class="btn btn-danger">Cancel</button>
            </a>
		</div>
</div>
<%@ include file="../partials/include_bootstrap_javascript.jsp"%>
</body>
</html>