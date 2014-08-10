<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Reset Password</title>
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
</head>

<body role="document">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../partials/navigation_bar_partial.jsp"%>
  
  <div id="main" class="container theme-showcase" role="main">
  
	<c:if test="${errmsg != null}">
      	<p class="alert alert-danger">${errmsg}</p>
	</c:if>
  	<div class="col-md-8 col-md-offset-2">
    <form role="form" id="userForm" name="reser_user_pass" method="POST" action="resetuserpass?id=${param.id}" class="form-horizontal">
        
        
        
       <!-- Old Password -->
		<c:if test="${empty param.id}">
		        <div class="form-group">
		          <label for="old_password">Old Password</label>
		          <input type="password" class="form-control" rows="3" name="old_password"></input>
		        </div>
		</c:if>
        
        <!-- New Password -->
        <div class="form-group">
          <label for="new_password"> New Password</label>
          <input type="password" class="form-control" rows="3" name="new_password"></input>
        </div>
        
        <!-- Confirm New Password -->
        <div class="form-group">
          <label for="confirm_new_pass">Confirm New Password</label>
          <input type="password" class="form-control" rows="3" name="confirm_new_pass"></input>
        </div>

        
        
        
        <!-- Submit -->
        <button type="submit" class="btn btn-primary">Change Password</button>
    </form>
    </div>
</div>
<%@ include file="../partials/include_bootstrap_javascript.jsp"%>
<script type="text/javascript" src="../js/formValidation.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    userFormValidation();
});
</script>
</body>
</html>