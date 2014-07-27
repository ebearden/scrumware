package com.scrumware.user;

import java.io.*;
import java.util.*;
import java.sql.*;

import com.scrumware.jdbc.*;

public class UserDB {
	
private static ArrayList<User> users = null;
    
    /**
     * 
     * This method connects to the db, uses a SQL statement to generate
     * a result set of all product data, then creates Products with this data
     * and adds them to the ArrayList products
     *
     * @return products an ArrayList of Product objects
     */
    public static ArrayList<User> getUsers()
    {
        
         
/*
 * new ArrayList user instantiated
 */
     
        users = new ArrayList<User>();
  
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
    
        
        String query = "SELECT user_id, username, first_name, last_name, email_address,"
        		+"user_role, active FROM Sys_User";
  
/*
 * try the query, if it works process data and return products, otherwise
 * catch the db exception
 */    
        
        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next())
            {
                User u = new User();
                u.setId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setFirstname(rs.getString("first_name"));
                u.setLastname(rs.getString("last_name"));
                u.setEmail(rs.getString("email_address"));
                u.setRole(rs.getInt("user_role"));
                u.setActive(rs.getInt("active"));
                users.add(u);
                //System.out.println("user added");
            }
            return users;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }    
        
/*
 * close out the result set, the statement and free the pool connection
 */    
        
        finally
        {
            DButil.closeResultSet(rs);
            DButil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
    }

    /**
     *
     * This method returns a specific user from the ArrayList
     * users
     * 
     * @param productCode string passed from servlet
     * @return single instance of User u
     */
    public static User getUser(int id)
    {
        users = getUsers();       
        for (User u : users)
        {            
            if (id != 0 && 
                id == u.getId())
            {
                return u;
            }
        }        
        return null;
    }

    /**
     * This method checks to see if a product is present in ArrayList products
     *
     * @param productCode String input from servlet
     * @return true or false
     */
    public static boolean exists(int id)
    {
    	 users = getUsers();       
         for (User u : users)
         {            
             if (id != 0 && 
                 id == u.getId())
             {
                 return true;
             }
         }             
        return false;
    }
    
    /**
     * This method checks to see if a product is present in ArrayList products
     *
     * @param productCode String input from servlet
     * @return true or false
     */
    public static boolean usernameExists(String username)
    {
    	 users = getUsers();       
         for (User u : users)
         {            
             if (u.getUsername().equals(username))
             {
                 return true;
             }
         }             
        return false;
    }
    
    /**
     * This method checks to see if a product is present in ArrayList products
     *
     * @param productCode String input from servlet
     * @return true or false
     */
    public static boolean emailExists(String email)
    {
    	 users = getUsers();       
         for (User u : users)
         {            
             if (u.getEmail() == email)
             {
                 return true;
             }
         }             
        return false;
    }
    
    /**
     *This method inserts the data from a Product object into the db that
     * holds product data
     * 
     * @param product Product object from servlet
     * @return 1 for success or 0 for failure
     */
    public static int insert(User u, String password)
    {
        
/*
 * get an instance of a pool connection, then instantiate PreparedStatement ps
 */        
        
    	ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
/*
 * set the statement to be executed in String query
 */        
        
        String query = 
                "INSERT INTO Sys_User (created, updated, username, password, first_name,"+
        		"last_name, email_address, user_role, active) " +
                "VALUES (now(), now(), ?, ?, ?, ?, ?, ?, ?)";
        
/*
 * try the query, if it works process data, otherwise catch the db exception
 */        
        
        try
        {        
            ps = connection.prepareStatement(query);
            ps.setString(1, u.getUsername());
            ps.setString(2, password);
            ps.setString(3, u.getFirstname());
            ps.setString(4, u.getLastname());
            ps.setString(5, u.getEmail());
            ps.setInt(6, u.getRole());
            ps.setInt(7, u.getActive());
            return ps.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
        
/*
 * close out the statement and free the pool connection
 */
        
        finally
        {
            DButil.closePreparedStatement(ps);
            pool.freeConnection(connection);
            
        }
    }

/**
 *This method updates an entry in the db using data from a Product object
 * 
 * @param product Product object from servlet
 * @return 1 for success or 0 for failure
 */
    
    public static int update(User u)
    {
        
/*
 * get an instance of a pool connection, then instantiate PreparedStatement ps
 */        
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
/*
 * set the statement to be executed in String query
 */  
        
        String query = "UPDATE Sys_User SET updated = now(),"+
        		" username = ?,"+
                " first_name = ?," +
                " last_name = ?," +
                " email_address = ?," +
                " user_role = ?," +
                " active = ?" +
                " WHERE user_id = ?";

 /*
 * try the query, if it works process data, otherwise catch the db exception
 */
        
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getFirstname());
            ps.setString(3, u.getLastname());
            ps.setString(4, u.getEmail());
            ps.setInt(5, u.getRole());
            ps.setInt(6, u.getActive());
            ps.setInt(7, u.getId());

            return ps.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
        
 /*
 * close out the statement and free the pool connection
 */       
        
        finally
        {
            DButil.closePreparedStatement(ps);
            //JDBCHelper.freeConnection(connection);
            pool.freeConnection(connection);
        }
    }

    public static boolean checkPassword(int user_id, String pass) {
    	
    	/*
    	 * get an instance of a pool connection, then instantiate PreparedStatement ps
    	 */        
    	        
    	        ConnectionPool pool = ConnectionPool.getInstance();
    	        Connection connection = pool.getConnection();
    	        PreparedStatement ps = null;
    	        ResultSet rs = null;
    	        String confirmpass = "";
    	        
    	/*
    	 * set the statement to be executed in String query
    	 */  
    	        
    	        String query = "SELECT password FROM Sys_User"+
    	                " WHERE user_id = ?";

    	 /*
    	 * try the query, if it works process data, otherwise catch the db exception
    	 */
    	        
    	        try
    	        {        
    	            ps = connection.prepareStatement(query);
    	            ps.setInt(1, user_id);
    	            rs = ps.executeQuery();
    	            while(rs.next()) {
    	            	confirmpass = rs.getString("password");
    	            } 
    	            
    	        }
    	        catch(SQLException e)
    	        {
    	            e.printStackTrace();
    	        }
    	        
    	 /*
    	 * close out the statement and free the pool connection
    	 */       
    	        
    	        finally
    	        {
    	        	DButil.closeResultSet(rs);
    	            DButil.closePreparedStatement(ps);
    	            pool.freeConnection(connection);
    	        }
    	        
    	        if (pass.matches(confirmpass)) {
            		return true;
            	} else {
            		return false;
            	}
    	
    }
    
    public static int resetPassword(int id, String newpass)
    {
        
/*
 * get an instance of a pool connection, then instantiate PreparedStatement ps
 */        
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
/*
 * set the statement to be executed in String query
 */  
        
        String query = "UPDATE Sys_User SET updated = now(), password = ?"+
                " WHERE user_id = ?";

 /*
 * try the query, if it works process data, otherwise catch the db exception
 */
        
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, newpass);
            ps.setInt(2, id);

            return ps.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
        
 /*
 * close out the statement and free the pool connection
 */       
        
        finally
        {
            DButil.closePreparedStatement(ps);
            //JDBCHelper.freeConnection(connection);
            pool.freeConnection(connection);
        }
    }
    
    /**
     * This method deletes an entry from the db using data from a Product object
     *
     * @param product Product object from servlet
     * @return 1 for success or 0 for failure
     */
    
    public static int delete(User u)
    {
/*
 * get an instance of a pool connection, then instantiate PreparedStatement ps
 */        
        
    	ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
/*
 * set the statement to be executed in String query
 */        
        
        
        String query = "DELETE FROM Sys_User " +
                "WHERE user_id = ?";
        
/*
 * try the query, if it works process data, otherwise catch the db exception
 */
        
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, u.getId());

            return ps.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
        
       
 /*
 * close out the statement and free the pool connection
 */         
        
        finally
        {
            DButil.closePreparedStatement(ps);
            //JDBCHelper.freeConnection(connection);
            pool.freeConnection(connection);
        }
    }

}
