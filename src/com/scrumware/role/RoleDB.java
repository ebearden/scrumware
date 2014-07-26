package com.scrumware.role;

import java.io.*;
import java.util.*;
import java.sql.*;

import com.scrumware.jdbc.*;


public class RoleDB {
	
	private static ArrayList<Role> roles = null;
	
	public static ArrayList<Role> getRoles() {
		
		/*
		 * new ArrayList roles instantiated
		 */
		
		roles = new ArrayList<Role>();
		
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
            
                
        String query = "SELECT role_id, active, role_name, description FROM User_Role where active = 1";
          
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
                Role r = new Role();
                r.setId(rs.getInt("role_id"));
                r.setActive(rs.getInt("active"));
                r.setRolename(rs.getString("role_name"));
                r.setDescription(rs.getString("description"));
                roles.add(r);
                //System.out.println("user added");
            }
            return roles;
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
    * This method returns a specific role from the ArrayList
    * roles
    * 
    * @param roleId string passed from servlet
    * @return single instance of Role r
    */
	   public static Role getRole(int id)
	   {
	       roles = getRoles();       
	       for (Role r : roles)
	       {            
	           if (id != 0 && 
	               id == r.getId())
	           {
	               return r;
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
	    	 roles = getRoles();       
	         for (Role r: roles)
	         {            
	             if (id != 0 && 
	                 id == r.getId())
	             {
	                 return true;
	             }
	         }             
	        return false;
	    }


}
