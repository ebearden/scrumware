<%@ page language="java" contentType="text/html; charset=US-ASCII"
  pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Tasks</title>
<%@ include file="../partials/include_bootstrap_partial.jsp"%>
 
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
  <link rel="stylesheet" href="../css/sortStyling.css">
  
   <script type="text/javascript">
   jQuery(function($) {
       var panelList = $('#draggablePanelList1');

       panelList.sortable({
    	   
           // Only make the .panel-heading child elements support dragging.
           // Omit this to make the entire <li>...</li> draggable.
           handle: '.panel-heading', 
           receive: function(event, ui){
        	   var taskItem = ui.item.context.id;
        	   var newStatus = ui.item.parent().attr("id")
        	           	   
        	   $.get('UpdateTaskStatus',{taskID: taskItem,statusID: newStatus});
           }
           
       }).disableSelection();
       
       panelList.sortable ("option", "connectWith", ".list-unstyled"),
       panelList.sortable( "option", "dropOnEmpty", true ),
       
     panelList =  $('#draggablePanelList2');
       panelList.sortable({
    	   
           // Only make the .panel-heading child elements support dragging.
           // Omit this to make the entire <li>...</li> draggable.
           handle: '.panel-heading', 
           
           receive: function(event, ui){
        	   var taskItem = ui.item.context.id;
        	   var newStatus = ui.item.parent().attr("id")
        	           	   
        	   $.get('UpdateTaskStatus',{taskID: taskItem,statusID: newStatus});
           }
       
       }).disableSelection(); 
       
 	   	panelList.sortable ("option", "connectWith", ".list-unstyled"),
		panelList.sortable( "option", "dropOnEmpty", true ),
       
       	panelList =  $('#draggablePanelList3');
		panelList.sortable({
    	   
           // Only make the .panel-heading child elements support dragging.
           // Omit this to make the entire <li>...</li> draggable.
           handle: '.panel-heading', 
           receive: function(event, ui){
        	   var taskItem = ui.item.context.id;
        	   var newStatus = ui.item.parent().attr("id")
        	           	   
        	   $.get('UpdateTaskStatus',{taskID: taskItem,statusID: newStatus});
           }
           
       
       });
       
		panelList.sortable ("option", "connectWith", ".list-unstyled"),
 		panelList.sortable( "option", "dropOnEmpty", true ),
       
       panelList =  $('#draggablePanelList4');
       panelList.sortable({
    	   
           // Only make the .panel-heading child elements support dragging.
           // Omit this to make the entire <li>...</li> draggable.
           handle: '.panel-heading',
           receive: function(event, ui){
        	   var taskItem = ui.item.context.id;
        	   var newStatus = ui.item.parent().attr("id")
        	           	   
        	   $.get('UpdateTaskStatus',{taskID: taskItem,statusID: newStatus},function(response){
        		   
        		   if (response === "fail"){
        			   panelList.sortable('cancel');
        		       $(ui.sender).sortable('cancel');
        		       alert(response);	
        		   }
        	   });
           }
           
       
       }).disableSelection();
       
       panelList.sortable ("option", "connectWith", ".list-unstyled"),
       panelList.sortable( "option", "dropOnEmpty", true )
       
   	});
   
	</script>


</head>
<body role="document">
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ include file="../partials/navigation_bar_partial.jsp"%>

  <div id="main" class="container theme-showcase" role="main">
    
   <div class="page-header">
        <h2>Your Tasks:</h2>
   </div>
    
    
    
    <div class="row">
    	<label class="col-xs-3">To Do</label>
    	<label class="col-xs-3">In Process</label>
    	<label class="col-xs-3">To Verify</label>
    	<label class="col-xs-3">Done</label>
    </div>
    
   
   <div class="row">
	<div id="todo" class="col-xs-3 well" >
    	<ul id="draggablePanelList1" class="list-unstyled emptylist">
    		<c:forEach var="t" items="${task_list}">
				<c:if test ="${t.statusId == 1}">
    				<li id="${t.taskId}" class="panel panel-primary">
    		    		<div class="panel-heading">${t.name} </div>
        				<div class="panel-body">${t.description}</div>
    				</li>
    			</c:if>
   			</c:forEach>
   			
		</ul>
	</div>
	
	
	<div id="inprocess" class="col-xs-3 well">
		<ul id="draggablePanelList2" class="list-unstyled emptylist">
    		<c:forEach var="t" items="${task_list}">
				<c:if test ="${t.statusId == 2}">
    				<li id= "${t.taskId}" class="panel panel-primary">
    		    		<div class="panel-default panel-heading">${t.name} </div>
        				<div class="panel-default panel-body">${t.description}</div>
    				</li>
    			</c:if>
   			</c:forEach>
   				
		</ul>
	</div>
	
	
	<div id="toverify" class="col-xs-3 well">
		<ul id="draggablePanelList3" class="list-unstyled emptylist">
    		<c:forEach var="t" items="${task_list}">
				<c:if test ="${t.statusId == 3}">
    				<li id= "${t.taskId}" class="panel panel-primary">
    		    		<div class="panel-heading">${t.name} </div>
        				<div class="panel-body">${t.description}</div>
    				</li>
    			</c:if>
   			</c:forEach>
		</ul>
	</div>
	
	
		<div id="done" class="col-xs-3 well">
		<ul id="draggablePanelList4" class="list-unstyled emptylist">
			<c:forEach var="t" items="${task_list}">
				<c:if test ="${t.statusId == 4}">
    				<li id= "${t.taskId}" class="panel panel-primary">
    		    		<div class="panel-heading">${t.name} </div>
        				<div class="panel-body">${t.description}</div>
    				</li>
    			</c:if>
   			</c:forEach>
		</ul>
	</div>
	</div>
	
	</div>
	
	
	
	
	
	
    
 	

  <%@ include file="../partials/include_bootstrap_javascript.jsp"%>
</body>
</html>