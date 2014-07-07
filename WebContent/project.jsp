<%@ page language="java" contentType="text/html; charset=US-ASCII"
  pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script type="text/javascript">
function save() {
	  $.ajax({
	    type: 'POST',
	   cache: false,
	     url: 'ProjectServlet',
	    data: $('form[name="new_project"]').serialize(),
	    success: showSuccess()
	   });
	  $('#newProjectModal').modal('hide');
	  return false;
}

function get() {
	$.ajax({
        type: 'GET',
       cache: false,
         url: 'ProjectServlet',
        data: $('form[name="edit_project"]').serialize(),
        success: function () {
        	$('#newProjectModal').modal('show');
        	
        }
       });
	return false;
}


function showSuccess() {
	location.reload();
}

</script>
<title>Tasks</title>
<%@ include file="partials/include_bootstrap_partial.jsp"%>
</head>
<body role="document">
  <%@ include file="partials/navigation_bar_partial.jsp"%>

  <div id="main" class="container theme-showcase" role="main">
    <br /> <br />
    <div id="success"></div>
    <p>
    <br /> <br />
    <button class="btn btn-primary" data-toggle="modal" data-target="#newTaskModal">New Task</button><br />
    </p>
    <div class="span12"><hr /></div>
    <div id="task-table" class="span12">${task_list}</div>
  </div>

  <%@ include file="partials/new_task_modal.jsp" %>
  <%@ include file="partials/confirm_delete_modal.jsp" %>
  <%@ include file="partials/include_bootstrap_javascript.jsp"%>
</body>
</html>