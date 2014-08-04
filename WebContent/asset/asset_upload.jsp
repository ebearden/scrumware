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
	  
		<form method="POST" action="upload?project_id=${param.project_id}" enctype="multipart/form-data" >
		<!-- User Name -->
        <div class="form-group">
          <label for="file">File</label>
           <input type="file" name="file" id="file" />
        </div>
        <div class="form-group">
          <label for="description">
           Description
          </label>
           <input type="text" name="description"class="form-control" rows="3" />
        </div>
        <!-- Submit -->
        <button type="submit" class="btn btn-primary" name="upload" id="upload">Upload</button>
       </form>
	</div>
<%@ include file="../partials/include_bootstrap_javascript.jsp"%>
</body>
</html>