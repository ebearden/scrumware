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
	     url: 'TaskServlet',
	    data: $('form[name="new_task"]').serialize(),
	    complete: window.location.reload()
	   });
	  $('#newTaskModal').modal('hide');
	  return false;
}
</script>
<title>Tasks</title>
<%@ include file="partials/include_bootstrap_partial.jsp"%>
</head>
<body role="document">
  <%@ include file="partials/navigation_bar_partial.jsp"%>

  <div class="container theme-showcase" role="main">
    <br /> <br />
    <div class="alert alert-success hidden">${msg}</div>
    <div class="span12"><hr /></div>
    <button class="btn btn-default" data-toggle="modal" data-target="#newTaskModal">New task</button>
    <div class="span12">${task_list}</div>
  </div>

  <%@ include file="partials/new_task_modal.jsp" %>
  <%@ include file="partials/include_bootstrap_javascript.jsp"%>
</body>
</html>