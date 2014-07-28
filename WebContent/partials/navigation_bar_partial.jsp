<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-static-top navbar-inverse " role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/SCRUMware/home.jsp">SCRUMware</a>
    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="/SCRUMware/project/projects">Projects</a>
        <li><a href="/SCRUMware/sprint/sprints">Sprints</a>
        <li><a href="/SCRUMware/story/stories">Stories</a>
        <li><a href="/SCRUMware/task/tasks">Tasks</a>
        <c:if test="${sessionScope.role<3}">
        	<li><a href="/SCRUMware/user/users">Users</a>
        </c:if>
        
      </ul>
      
      <ul class="nav navbar-nav navbar-right">
      	<li id="fat-menu" class="dropdown">
		  <a href="#" id="drop3" role="button" class="dropdown-toggle" data-toggle="dropdown">${sessionScope.user_name}<b class="caret"></b></a>
		  <ul class="dropdown-menu" role="menu" aria-labelledby="drop3">
		  	<li><a href="/SCRUMware/user/viewuser">View Profile</a></li>
		    <li><a href="/SCRUMware/user/reset_pass.jsp">Reset Password</a></li>
		    <li role="presentation" class="divider"></li>
		    <li><a href="/SCRUMware/Logout">Logout</a></li>
		  </ul>
		</li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>