<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
<title>Upload a File</title>
</head>
<body>
<%@ include file="../partials/navigation_bar_partial.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	  <div id="main" class="container theme-showcase" role="main">
	  
	  <h1>Add an Asset</h1>
		<c:if test="${errmsg != null}">
			<p class="alert alert-danger">${errmsg}</p>
		</c:if>
	  
		
		<c:choose>
	        <c:when test="${exists != null}">
	        	<form method="POST" action="upload?update=true&project_id=${project_id}" enctype="multipart/form-data" >
	        </c:when>
	        <c:otherwise>
	        	<form method="POST" action="upload?project_id=${param.project_id}" enctype="multipart/form-data" >
	        </c:otherwise>
        </c:choose>
		
		<!-- User Name -->
        <div class="form-group">
          <label for="file">File</label>
           <input type="file" name="file" id="file" "/>
        </div>
        <div class="form-group">
          <label for="description">
           Description
          </label>
           <input type="text" name="description"class="form-control" rows="3" value="${a_desc}"/>
        </div>
        <!-- Submit -->
        <c:choose>
	        <c:when test="${exists != null}">
	        	<button type="submit" class="btn btn-success" name="confirm" id="confirm">Confirm</button>
	        	<a href="../asset/assets?project_id=${project_id}">
	        		<button class="btn btn-danger" name="cancel">Cancel</button>
	        	</a>
	        </c:when>
	        <c:otherwise>
	        <button type="submit" class="btn btn-primary" name="upload" id="upload">Upload</button>
	        </c:otherwise>
        </c:choose>
       </form>
	</div>
<%@ include file="../partials/include_bootstrap_javascript.jsp"%>
</body>
</html>