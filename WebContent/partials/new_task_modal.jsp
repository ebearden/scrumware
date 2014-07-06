<div class="modal fade" id="newTaskModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">
          <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title" id="myModalLabel">New Task</h4>
      </div>
      <div class="modal-body">
        <form role="form" name="new_task" method="POST" action="">
          <div class="form-group">
            <label for="task_name">Name</label> <input type="text"
              class="form-control" name="task_name">
          </div>
          <div class="form-group">
            <label for="description">Description</label>
            <textarea class="form-control" rows="3" name="description"></textarea>
          </div>

          <div class="form-group">
            <label for="assigned_to">Assign To</label> 
            <select
              class="form-control" name="assigned_to">
              <%@ page import="com.scrumware.javabeans.UserBean"%>
              <%
              	UserBean userBean = new UserBean();
              %>
              <%for (int i = 0; i < userBean.getItems().size(); i++) {%>
              <option value="<%=i+2%>">
                <% out.print(userBean.getItems().get(i)); %>
              </option>
              <%}%>
            </select>

          </div>
          <div class="form-group">
            <label for="story_name">Story</label> <select
              class="form-control" name="story_id">
              <%@ page import="com.scrumware.javabeans.StoryBean"%>
              <%StoryBean storyBean = new StoryBean();%>
              <%for (Integer i : storyBean.getItems().keySet()) {%>
              <option value="<%=i%>">
                <% out.print(storyBean.getItems().get(i)); }%>
              </option>
            </select>
          </div>
            <div class="modal-footer">
              <button type="submit" onclick="return save();" class="btn btn-primary">Add Task</button>
            </div>
        </form>
    </div>
  </div>
</div>
