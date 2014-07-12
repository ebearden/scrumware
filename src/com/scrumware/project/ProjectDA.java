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

public class ProjectDA {
	public Project getProject(int projectId) {
		return getProjectListForIdType(Constants.PROJECT_ID, projectId).get(0);
	}
	
	public List<Project> getAllProjects() {
		return getProjectListForIdType(null, null);
	}
	
	public List<Project> getAllProjectsForUserId(int userId) {
		return getProjectListForIdType(Constants.ASSIGNED_TO, userId);
	}
	
	public boolean saveProject(Project project) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		String projectSQL;
		boolean success = false, isUpdate = false;
		
		if ((Integer)(project.getProjectID()) != null) {
			isUpdate = true;
			projectSQL = "UPDATE Project SET project_name=?, description=?, project_manager=?, "
					+ "planned_start_date=?, planned_end_date=?, status_id=?, "
					+ "updated=NOW(), updated_by=? "
					+ "WHERE project_id=?;";
					
		}
		else {
			projectSQL = "INSERT INTO Project(project_name, description, project_manager, planned_start_date, "
					+ "planned_end_date, status_id, created_by, updated_by) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";	
		}
		
		try {
			PreparedStatement projectStatement = connection.prepareStatement(projectSQL, Statement.RETURN_GENERATED_KEYS);
			
			projectStatement.setString(1, project.getName());
			projectStatement.setString(2, project.getDescription());
			projectStatement.setInt(3, project.getPM());
			projectStatement.setDate(4, project.getStartDate());
			projectStatement.setDate(5, project.getEndDate());
			projectStatement.setInt(6, project.getStatus());
			if (isUpdate) {
				projectStatement.setInt(8, project.getUpdatedBy());
			} else {
				projectStatement.setInt(8, project.getCreatedBy());
				projectStatement.setInt(9, project.getUpdatedBy());
			}

			
			int result = projectStatement.executeUpdate();
			if (result == 1) {
				ResultSet generatedKey = projectStatement.getGeneratedKeys();
				if (generatedKey.next()) {
					project.setProjectID(generatedKey.getInt(1));
					System.out.println(generatedKey.getInt(1));
					System.out.println(generatedKey.getLong(1));
				}
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
		}
		return success;
	}
	
	public boolean saveProject(List<Project> projectList) {
		boolean success = false;
		for (Project p : projectList) {
			 success = saveProject(p);
		}
		return success;
	}
	
	private List<Project> getProjectListForIdType(String type, Integer id) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		List<Project> projectList = new ArrayList<Project>();

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
			
			PreparedStatement dependencyStatement = connection.prepareStatement(
					"SELECT dependency_id, depends_on, active FROM Task_Dependencies WHERE task_id=?;"
					);
			
			if (id != null) {
				projectStatement.setInt(1, id);
			}
			ResultSet projectResultSet = projectStatement.executeQuery();
			
			while (projectResultSet.next()) {
				project = new Project();
				project.setProjectID(projectResultSet.getInt(1));
				project.setCreated(projectResultSet.getDate(2));
				project.setCreatedBy(projectResultSet.getInt(3));
				project.setUpdated(projectResultSet.getDate(4));
				project.setUpdatedBy(projectResultSet.getInt(5));
				project.setName(projectResultSet.getString(6));
				project.setDescription(projectResultSet.getString(7));
				project.setPM(projectResultSet.getInt(8));
				project.setStartDate(projectResultSet.getDate(9));
				project.setEndDate(projectResultSet.getDate(10));
				project.setStatus(projectResultSet.getInt(11));
				projectList.add(project);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
		}

		return projectList;
	}
	
	public boolean deleteProject(Project project) {
		Connection connection = ConnectionPool.getInstance().getConnection();
		PreparedStatement statement = null;
		String sql = "DELETE FROM Project WHERE project_id=?";
		boolean success = false;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, project.getProjectID());
			if (statement.executeUpdate() == 1) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.getInstance().freeConnection(connection);
		}
		return success;
	}
}
