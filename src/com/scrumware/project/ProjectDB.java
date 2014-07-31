package com.scrumware.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.scrumware.config.Constants;
import com.scrumware.jdbc.ConnectionPool;

public class ProjectDB {
	public static Project getProject(int projectId) {
		return getProjectListForIdType(Constants.PROJECT_ID, projectId).get(0);
	}
	
	public static ArrayList<Project> getAllProjects() {
		return getProjectListForIdType(null, null);
	}
	
	public static ArrayList<Project> getAllProjectsForUserId(int userId) {
		return getProjectListForIdType(Constants.PROJECT_MANAGER, userId);
	}
	
	public static Project saveProject(Project project) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		String projectSQL;
		boolean isUpdate = false;
		
		if ((Integer)(project.getProjectId()) != null) {
			isUpdate = true;
			projectSQL = "UPDATE Project SET project_name=?, description=?, project_manager=?, "
					+ "planned_start_date=?, planned_end_date=?, status_id=?, "
					+ "updated=NOW(), updated_by=? "
					+ "WHERE project_id=?;";
					
		}
		else {
			projectSQL = "INSERT INTO Project(project_name, description, project_manager, planned_start_date, "
					+ "planned_end_date, status_id, created_by, updated_by) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";	
		}
		
		try {
			PreparedStatement projectStatement = connection.prepareStatement(projectSQL, Statement.RETURN_GENERATED_KEYS);
			projectStatement.setString(1, project.getName());
			projectStatement.setString(2, project.getDescription());
			projectStatement.setInt(3, project.getProjectManagerId());
			projectStatement.setDate(4, project.getStartDate());
			projectStatement.setDate(5, project.getEndDate());
			projectStatement.setInt(6, project.getStatusId());
			if (isUpdate) {
				projectStatement.setInt(7, project.getUpdatedBy());
				projectStatement.setInt(8, project.getProjectId());
			} else {
				projectStatement.setInt(7, project.getCreatedBy());
				projectStatement.setInt(8, project.getUpdatedBy());
			}
			System.out.println(projectStatement);

			
			int result = projectStatement.executeUpdate();
			if (result == 1) {
				ResultSet generatedKey = projectStatement.getGeneratedKeys();
				if (generatedKey.next()) {
					project.setProjectId(generatedKey.getInt(1));
					System.out.println(generatedKey.getInt(1));
					System.out.println(generatedKey.getLong(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
		}
		return getProject(project.getProjectId());
	}
	
	public static boolean saveProject(List<Project> projectList) {
		boolean success = false;
		for (Project p : projectList) {
			 saveProject(p);
		}
		return success;
	}
	
	private static ArrayList<Project> getProjectListForIdType(String type, Integer id) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		ArrayList<Project> projectList = new ArrayList<Project>();

		Project project = null;
		String sql;
		if (id == null) {
			sql = "SELECT project_id, created, created_by, updated, updated_by, project_name,"
					+ "description, project_manager, planned_start_date, planned_end_date, status_id "
					+ "FROM Project";
		}
		else {
			sql = "SELECT project_id, created, created_by, updated, updated_by, project_name,"
					+ "description, project_manager, planned_start_date, planned_end_date, status_id "
					+ "FROM Project WHERE " + type + "=?;";
		}
		
		try {
			PreparedStatement projectStatement = connection.prepareStatement(sql);
			System.out.println(projectStatement);
			
			if (id != null) {
				projectStatement.setInt(1, id);
			}
			ResultSet projectResultSet = projectStatement.executeQuery();
			
			while (projectResultSet.next()) {
				project = new Project();
				project.setProjectId(projectResultSet.getInt(1));
				project.setCreated(projectResultSet.getDate(2));
				project.setCreatedBy(projectResultSet.getInt(3));
				project.setUpdated(projectResultSet.getDate(4));
				project.setUpdatedBy(projectResultSet.getInt(5));
				project.setName(projectResultSet.getString(6));
				project.setDescription(projectResultSet.getString(7));
				project.setProjectManagerId(projectResultSet.getInt(8));
				project.setStartDate(projectResultSet.getDate(9));
				project.setEndDate(projectResultSet.getDate(10));
				project.setStatusId(projectResultSet.getInt(11));
				projectList.add(project);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
		}

		return projectList;
	}
	
	public static boolean deleteProject(int projectId) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement statement = null;
		
		String[] sql = new String[7];
		sql[0] = "DELETE Task_Dependencies AS td FROM Task_Dependencies td RIGHT JOIN Task t ON t.task_id=td.depends_on RIGHT JOIN Story s ON s.story_id=t.story_id RIGHT JOIN Project p ON p.project_id=s.project_id WHERE p.project_id=?;";
		
		sql[1] = "DELETE Task_Dependencies AS td FROM Task_Dependencies td RIGHT JOIN Task t ON t.task_id=td.task_id RIGHT JOIN Story s ON s.story_id=t.story_id RIGHT JOIN Project p ON p.project_id=s.project_id WHERE p.project_id=?;";
		
		sql[2] = "DELETE Task AS t FROM Task t RIGHT JOIN Story s ON s.story_id=t.story_id RIGHT JOIN Project p ON p.project_id=s.project_id WHERE p.project_id=?;";
		
		sql[3] = "DELETE Story AS s FROM Story s RIGHT JOIN Project p ON p.project_id=s.project_id WHERE p.project_id=?;";
		
		sql[4] = "DELETE Sprint AS s FROM Sprint s RIGHT JOIN Project p ON p.project_id=s.project_id WHERE p.project_id=?;";
		
		sql[5] = "DELETE Project_Users AS pu FROM Project_Users pu RIGHT JOIN Project p ON p.project_id=pu.project_id WHERE p.project_id=?;";
		
		sql[6] = "DELETE Project AS p FROM Project p WHERE p.project_id=?;";

		boolean success = false;
		
		try {
			for(int i = 0; i < sql.length; i++) {
				statement = connection.prepareStatement(sql[i]);
				statement.setInt(1, projectId);
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
}
