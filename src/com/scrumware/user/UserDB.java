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
 * new ArrayList products instantiated
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
    
        
        String query = "SELECT user_id, username, email_address FROM Sys_User";
  
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
                u.setName(rs.getString("username"));
                u.setEmail(rs.getString("email_address"));
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
     * This method returns a specific product from the ArrayList
     * products
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
     *This method inserts the data from a Product object into the db that
     * holds product data
     * 
     * @param product Product object from servlet
     * @return 1 for success or 0 for failure
     */
    public static int insert(User u)
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
                "INSERT INTO Sys_User (username, email_address) " +
                "VALUES (?, ?)";
        
/*
 * try the query, if it works process data, otherwise catch the db exception
 */        
        
        try
        {        
            ps = connection.prepareStatement(query);
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
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
            //JDBCHelper.freeConnection(connection);
            /*
            try  {
            	connection.close();
            } catch (Exception e) {
            	e.printStackTrace();
            }
            */
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
        
        String query = "UPDATE User SET username = ?,"+
                " email_address = ?" +
                " WHERE user_id = ?";

 /*
 * try the query, if it works process data, otherwise catch the db exception
 */
        
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setInt(3, u.getId());

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
