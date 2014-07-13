
<!-- Fixed navbar -->
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">SCRUMware</a>
          <ul class="nav navbar-nav">
          <li class="active"><a href="/SCRUMware/login.jsp">Login</a></li>
          <li class="active"><a href="/SCRUMware/Logout">Logout</a></li>
          </ul>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="/SCRUMware/home.jsp">Home</a></li>
 
            <li class="dropdown">
              <a href="/SCRUMware/project.jsp" class="dropdown-toggle" data-toggle="dropdown">Project <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="/SCRUMware/project.jsp">Add Project</a></li>
                <li><a href="#">Update Project</a></li>
              </ul>
            </li>
            <li class="dropdown">
              <a href="/SCRUMware/sprint.jsp" class="dropdown-toggle" data-toggle="dropdown">Sprint <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">Add Sprint</a></li>
                <li><a href="#">Update Sprint</a></li>
              </ul>
            </li>
            <li class="dropdown">
              <a href="/SCRUMware/story.jsp" class="dropdown-toggle" data-toggle="dropdown">Story <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="/SCRUMware/story.jsp">Add Story</a></li>
                <li><a href="/SCRUMware/story.jsp">Update Story</a></li>
              </ul>
            </li>
            <li class="dropdown">
              <a href="/SCRUMware/task.jsp" class="dropdown-toggle" data-toggle="dropdown">Task <b class="caret"></b></a>
              <ul class="dropdown-menu">
              <li><a href="/SCRUMware/task/all">View Tasks</a></li>
                <li><a href="/SCRUMware/task/new">New Task</a></li>
              </ul>
            </li>
            <li class="dropdown">
              <a href="/SCRUMware/ViewUsers" class="dropdown-toggle" data-toggle="dropdown">User <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="/SCRUMware/ViewUsers">Add User</a></li>
                <li><a href="#">Update Task</a></li>
              </ul>
            </li>
            
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>