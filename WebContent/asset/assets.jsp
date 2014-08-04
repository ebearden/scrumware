<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
<title>Project Assets</title>
</head>
<body>
<%-- 
    Document   : ViewAssets
    Created on : Jun 28, 2014
    Author     : eakubic

    This page uses custom tags with the prefix c
    It displays Product data pulled from the db and
    provides links to CRUD operations


--%>


<%@ include file="../partials/navigation_bar_partial.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

  <div id="main" class="container theme-showcase" role="main">
  
            <div class="pull-left"><h3>Project ${project_id} Assets</h3></div>
            <div class="pull-right"><h3><a href="../asset/asset_upload.jsp?project_id=${project_id}">Add an Asset</a></h3></div>
            <div id="asset-table" class="span12">
            <table id="asset-table" class="table table-condensed table-hover">
            <thead>
                <tr>
	                <th>File Name</th><th>File Description</th><th>Created On</th>
	                <th>Created By</th><th>Updated On</th><th>Updated By</th>
                </tr>
            </thead>
                <c:forEach var="a" items="${assets}">
                    <tr>
                        <td><a href="../asset/download?name=${a.name}&id=${project_id}">
                        ${a.name}</a></td>
                        <td>${a.description}</td>
                        <td>${a.created}</td>
                        <c:forEach var="u" items="${users}">
                        	<c:if test="${a.createdBy == u.id}">
                        		<td>${u.username}</td>
                        	</c:if>
                        </c:forEach>
                        <td>${a.updated}</td>
                        <c:forEach var="u" items="${users}">
                        	<c:if test="${a.updatedBy == u.id}">
                        		<td>${u.username}</td>
                        	</c:if>
                        </c:forEach>	
                    </tr>
                </c:forEach>
            </table>
		</div>
</div>
<%@ include file="../partials/include_bootstrap_javascript.jsp"%>
</body>
</html>