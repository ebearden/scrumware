package com.scrumware.story;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.scrumware.config.Constants;
import com.scrumware.jdbc.ConnectionPool;
import com.scrumware.jdbc.DButil;
import com.scrumware.project.Project;
import com.scrumware.task.Task;



public class StoryDB {
	/**
	 * Get a story by Id.
	 * @param storyId - The tasks Id.
	 * @return a new Story object.
	 */
	public static Story getStory(int storyId) {
		return getStoryListForIdType(Constants.STORY_ID, storyId).get(0);
	}
	
	/**
	 * Get all stories. Potentially way too many.
	 * @return List of all stories.
	 */
	public static ArrayList<Story> getAllStories() {
		return getStoryListForIdType(null, null);
	}

	/**
	 * Get all stories related to a project.
	 * @param projectId - The project Id.
	 * @return a list of new story objects for the project.
	 */
	public static ArrayList<Story> getAllStoriesForProject(int projectId) {
		return getStoryListForIdType(Constants.STORY_ID, projectId);
	}
	
	/**
	 * Get all stories related to a sprint.
	 * @param sprintId - The sprint Id.
	 * @return a list of new story objects for the sprint.
	 */
	public static ArrayList<Story> getAllStoriesForSprint(int sprintId) {
		return getStoryListForIdType(Constants.STORY_ID, sprintId);
	}
	
	public static Story saveStory(Story story) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		String storySQL;
		boolean isUpdate = false;
		
		if (story.getStoryID() != null) {
			isUpdate = true;
			storySQL = "UPDATE Story SET story_name=?, description=?, acceptance_criteria=?, "
					+ "status_id=?, sprint_id=?, task_count=?, "
					+ "updated=NOW(), updated_by=? "
					+ "WHERE story_id=?;";
					
		}
		else {
			storySQL = "INSERT INTO Story(story_name, description, acceptance_criteria, status_id, "
					+ "sprint_id, task_count, created_by, updated_by, created, updated) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW());";	
		}
		
		try {
			PreparedStatement storyStatement = connection.prepareStatement(storySQL, Statement.RETURN_GENERATED_KEYS);
			
			storyStatement.setString(1, story.getStoryName());
			storyStatement.setString(2, story.getDescription());
			storyStatement.setString(3, story.getAcceptanceCriteria());
			storyStatement.setInt(4, story.getStatusID());
			storyStatement.setInt(5, story.getSprintID());
			storyStatement.setInt(6, story.getTaskCount());
			if (isUpdate) {
				storyStatement.setInt(7, story.getUpdatedBy());
				storyStatement.setInt(8, story.getStoryID());
			} else {
				storyStatement.setInt(7, story.getCreatedBy());
				storyStatement.setInt(8, story.getUpdatedBy());
			}

			
			int result = storyStatement.executeUpdate();
			if (result == 1) {
				ResultSet generatedKey = storyStatement.getGeneratedKeys();
				if (generatedKey.next()) {
					story.setStoryID(generatedKey.getInt(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
		}
		return getStory(story.getStoryID());
	}
	
	/**
	 * Save a list of stories.
	 * @param storyList - the list of story.
	 */
	public static void saveStory(ArrayList<Story> storyList) {
		for (Story s : storyList) {
			 saveStory(s);
		}
	}
	
	private static ArrayList<Story> getStoryListForIdType(String type, Integer id) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		ArrayList<Story> storyList = new ArrayList<Story>();

		Story story = null;
		String sql;
		if (id == null) {
			sql = "SELECT story_id, created, created_by, updated, updated_by, story_name, description, "
					+ "acceptance_criteria, status_id, project_id, sprint_id, task_count "
					+ "FROM Story;";
		}
		else {
			sql = "SELECT story_id, created, created_by, updated, updated_by, story_name, description, "
					+ "acceptance_criteria, status_id, project_id, sprint_id, task_count "
					+ "FROM Story WHERE " + type + "=?;";
		}
		
		try {
			PreparedStatement storyStatement = connection.prepareStatement(sql);

			if (id != null) {
				storyStatement.setInt(1, id);
			}
			ResultSet storyResultSet = storyStatement.executeQuery();
			
			while (storyResultSet.next()) {
				story = new Story();
				story.setStoryID(storyResultSet.getInt(1));
				story.setCreated(storyResultSet.getTimestamp(2));
				story.setCreatedBy(storyResultSet.getInt(3));
				story.setUpdated(storyResultSet.getTimestamp(4));
				story.setUpdatedBy(storyResultSet.getInt(5));
				story.setStoryName(storyResultSet.getString(6));
				story.setDescription(storyResultSet.getString(7));
				story.setAcceptanceCriteria(storyResultSet.getString(8));
				story.setStatusId(storyResultSet.getInt(9));
				story.setProjectID(storyResultSet.getInt(10));
				story.setSprintID(storyResultSet.getInt(11));
				story.setTaskCount(storyResultSet.getInt(12));
				storyList.add(story);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
		}

		return storyList;
	}
	
	/**
	 * Delete a Story.
	 * @param story - The story to delete.
	 * @return True if successful, else false.
	 */
	public static boolean deleteStory(Story story) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement statement = null;
		String[] sql = new String[4];
		//Delete Dependent On Task Dependencies in Story
		sql[0] = "DELETE Task_Dependencies FROM Task_Dependencies RIGHT JOIN Task ON Task.task_id=Task_Dependencies.depends_on RIGHT JOIN Story ON Story.story_id=Task.story_id WHERE Story.story_id=?;";
		//Delete Dependent of Task Dependencies in Story
		sql[1] = "DELETE Task_Dependencies FROM Task_Dependencies RIGHT JOIN Task ON Task.task_id=Task_Dependencies.task_id RIGHT JOIN Story ON Story.story_id=Task.story_id WHERE Story.story_id=?;";
		//Delete Tasks in Story
		sql[2] = "DELETE Task FROM Task RIGHT JOIN Story ON Story.story_id=Task.story_id WHERE Story.story_id=?;";
		//Delete Story
		sql[3] = "DELETE Story FROM Story WHERE Story.story_id=?;";

		boolean success = false;

		try {
			for(int i = 0; i < sql.length; i++) {
				statement = connection.prepareStatement(sql[i]);
				statement.setInt(1, story.getStoryID());
				if (statement.executeUpdate() == 1) {
					success = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
		}
		return success;
	}
	
	public static boolean hasOpenTasks(int storyId) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		String sqlString = "SELECT COUNT(*) FROM Task WHERE story_id=? AND status_id <> 4;";
		
		PreparedStatement statement = null;
		int count = 0;
		try {
			statement = connection.prepareStatement(sqlString);
			statement.setInt(1, storyId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DButil.closePreparedStatement(statement);
			ConnectionPool.getInstance().freeConnection(connection);
		}
		
		return count > 0;
	}
}
