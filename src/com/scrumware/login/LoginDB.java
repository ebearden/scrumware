package com.scrumware.login;

import java.io.*;
import java.util.*;
import java.sql.*;
//import com.scrumware.user.*;

import com.scrumware.jdbc.*;

public class LoginDB {
	
	private String user_name;
	private int id;
	private int role;
	//private User user;
	
	/*no arg constructor
	 *this does nothing and should not be used
	 */
	
	public LoginDB() {
		
		this.user_name = "";
		this.id = 0;
		this.role = 0;
		
	}
	
	/*constructor with DB login*/
	
	public LoginDB(String name, String password) {
		
		this.user_name = name;
		this.id = 0;
		this.role = 0;
		//this.user = new User();
		
	
	
	
	/*
	 * get an instance of a pool connection, then instantiate PreparedStatement ps
	 * and ResultSet rs
	 */
	        
	        ConnectionPool pool = ConnectionPool.getInstance();
	        Connection connection = pool.getConnection();
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        

    /*
     * set the statement to be executed in String query
     */        
            
            String query = "SELECT user_id, password, user_role FROM Sys_User WHERE username = ?";
            
    /*
     * try the query, if it works process data, otherwise catch the db exception
     */        
            
        try
        {        
            ps = connection.prepareStatement(query);
            ps.setString(1, user_name);
            rs = ps.executeQuery();
            while(rs.next()) {
            	if (password.matches(rs.getString("password"))) {
            		this.id = rs.getInt("user_id");
            		//System.out.println("id: "+this.id);
            		this.role = rs.getInt("user_role");
            		//System.out.println("role: "+this.id);
            	}
            } 
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        /*close the result set
         * close the prepared statement
         * free the pool connection
         */
        
        
        finally
        {
        	DButil.closeResultSet(rs);
            DButil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
	}
	
	/*check to see if credentials matched DB*/
	
	public boolean isValid() {
		if (this.role!=0 && this.id!=0) {
			return true;
		} else {
			return false;
		}
	}
	
	/*return user id for session variables*/
	
	public int getId() {
		
		return this.id;
	
	}
	
	/*return user role id for session variables*/
	
	public int getRole() {
		
		return this.role;
		
	}
	
}
