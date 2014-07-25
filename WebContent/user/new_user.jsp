<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>New User</title>
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
</head>

<body role="document">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../partials/navigation_bar_partial.jsp"%>
  
  <div id="main" class="container theme-showcase" role="main">
  
	<c:if test="${errmsg != null}">
      	<p class="alert alert-danger">${errmsg}</p>
	</c:if>
  
    <form role="form" id="userForm" name="new_user" method="POST" action="adduser" class="form-horizontal">
        <!-- User Name -->
        <div class="form-group">
          <label for="user_name">User Name</label>
          <input type="text" class="form-control" name="user_name" value="${username}"></input>
        </div>
        
        <!-- First Name -->
        <div class="form-group">
          <label for="first_name">First Name</label>
          <input type="text" class="form-control" name="first_name" value="${firstname}"></input>
        </div>
        
        <!-- Last Name -->
        <div class="form-group">
          <label for="last_name">Last Name</label>
          <input type="text" class="form-control" rows="3" name="last_name" value="${lastname}"></input>
        </div>
        
        <!-- Email -->
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" class="form-control" rows="3" name="email" value="${email}"></input>
        </div>
        
        <!-- Password -->
        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" class="form-control" rows="3" name="password"></input>
        </div>
        
        <!-- Confirm Password -->
        <div class="form-group">
          <label for="confirm_pass">Confirm Password</label>
          <input type="password" class="form-control" rows="3" name="confirm_pass"></input>
        </div>

        <!-- Role -->
        <div class="form-group">
          <label for="role">Role</label> 
          <select class="form-control" name="role">
            <c:forEach var="r" items="${roles}">
                <option value="${r.id}">${r.rolename}</option>            
            </c:forEach>
          </select>
        </div>
        
        <!-- Active -->
        <div class="form-group">
          <label for="active">Status</label> 
          <select class="form-control" name="active">
                <option value="0">Active</option>     
                <option value="1">Inactive</option>  
          </select>
        </div>
        
        
        <!-- Submit -->
        <button type="submit" class="btn btn-primary">Add User</button>
    </form>
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