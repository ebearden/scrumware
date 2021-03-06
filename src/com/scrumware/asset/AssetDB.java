package com.scrumware.asset;

import java.io.*;
import java.util.*;
import java.sql.*;

import com.scrumware.jdbc.*;

/**
 * @author emily kubic
 */

public class AssetDB {
	
private static ArrayList<Asset> assets = null;
    
    /**
     * 
     * This method connects to the db, uses a SQL statement to generate
     * a result set of all product data, then creates Products with this data
     * and adds them to the ArrayList products
     *
     * @return products an ArrayList of Product objects
     */
    public static ArrayList<Asset> getAssets(int project_id)
    {
        
         
/*
 * new ArrayList user instantiated
 */
     
        assets = new ArrayList<Asset>();
  
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
    
        
        String query = "SELECT * FROM Asset WHERE project_id = ?";
  
/*
 * try the query, if it works process data and return products, otherwise
 * catch the db exception
 */    
        
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, project_id);
            rs = ps.executeQuery();
            while (rs.next())
            {
                Asset a = new Asset();
                a.setAssetID(rs.getInt("asset_id"));
                a.setCreated(rs.getDate("created"));
                a.setCreatedBy(rs.getInt("created_by"));
                a.setUpdated(rs.getDate("updated"));
                a.setUpdatedBy(rs.getInt("updated_by"));
                a.setName(rs.getString("asset_name"));
                a.setDescription(rs.getString("description"));
                a.setLocation(rs.getString("location"));
                a.setUpdatedBy(rs.getInt("updated_by"));
                a.setProjectID(rs.getInt("project_id"));
                assets.add(a);
                //System.out.println("user added");
            }
            return assets;
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

    
    public static Asset getAsset(String name, int project_id)
    {
        
         
/*
 * new ArrayList user instantiated
 */
     
        Asset a = new Asset();
  
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
    
        
        String query = "SELECT * FROM Asset WHERE asset_name = ? AND project_id = ?";
  
/*
 * try the query, if it works process data and return products, otherwise
 * catch the db exception
 */    
        
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, project_id);
            rs = ps.executeQuery();
            while (rs.next())
            {
                
                a.setAssetID(rs.getInt("asset_id"));
                a.setCreated(rs.getDate("created"));
                a.setCreatedBy(rs.getInt("created_by"));
                a.setUpdated(rs.getDate("updated"));
                a.setUpdatedBy(rs.getInt("updated_by"));
                a.setName(rs.getString("asset_name"));
                a.setDescription(rs.getString("description"));
                a.setLocation(rs.getString("location"));
                a.setProjectID(rs.getInt("project_id"));
            }
            return a;
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
    

    public static Asset getAsset(int id)
    {
    	/*
    	 * new ArrayList user instantiated
    	 */
    	     
    	        Asset a = new Asset();
    	  
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
    	    
    	        
    	        String query = "SELECT * FROM Asset WHERE asset_id = ?";
    	  
    	/*
    	 * try the query, if it works process data and return products, otherwise
    	 * catch the db exception
    	 */    
    	        
    	        try
    	        {
    	            ps = connection.prepareStatement(query);
    	            ps.setInt(1, id);
    	            rs = ps.executeQuery();
    	            while (rs.next())
    	            {
    	                
    	                a.setAssetID(rs.getInt("asset_id"));
    	                a.setCreated(rs.getDate("created"));
    	                a.setCreatedBy(rs.getInt("created_by"));
    	                a.setUpdated(rs.getDate("updated"));
    	                a.setUpdatedBy(rs.getInt("updated_by"));
    	                a.setName(rs.getString("asset_name"));
    	                a.setDescription(rs.getString("description"));
    	                a.setLocation(rs.getString("location"));
    	                a.setProjectID(rs.getInt("project_id"));
    	            }
    	            return a;
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
     * This method checks to see if an Asset is present in ArrayList assets
     *
     * @param id int from servlet
     * @return true or false
     */
    

    public static boolean exists(String name, int project_id)
    {
    	 assets = getAssets(project_id);       
         for (Asset a : assets)
         {            
             if (name.equals(a.getName()))
             {
                 return true;
             }
         }             
        return false;
    }
    
  
    
    /**
     *This method inserts the data from an Asset object into the db that
     * holds asset data
     * 
     * @param a Asset object from servlet
     * @return 1 for success or 0 for failure
     */
    public static int insert(Asset a)
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
                "INSERT INTO Asset (created, created_by, updated, updated_by,"+
                "asset_name, description, location, project_id) " +
                "VALUES (now(), ?, now(), ?, ?, ?, ?, ?)";
        
/*
 * try the query, if it works process data, otherwise catch the db exception
 */        
        
        try
        {        
            ps = connection.prepareStatement(query);
            ps.setInt(1, a.getCreatedBy());
            ps.setInt(2, a.getUpdatedBy());
            ps.setString(3, a.getName());
            ps.setString(4, a.getDescription());
            ps.setString(5, a.getLocation());
            ps.setInt(6, a.getProject());
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
 *This method updates an entry in the db using data from an Asset object
 * 
 * @param a Asset object from servlet
 * @return 1 for success or 0 for failure
 */
    
    public static int update(Asset a)
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
        
        String query = "UPDATE Asset SET updated = now(),"+
        		" updated_by = ?, "+
        		" description = ?"+
                " WHERE asset_id = ?";

 /*
 * try the query, if it works process data, otherwise catch the db exception
 */
        
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, a.getUpdatedBy());
            ps.setString(2, a.getDescription());
            ps.setInt(3, a.getAssetID());
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
     *This method deletes an entry in the db using data from an Asset object
     * 
     * @param id an Asset object id from servlet
     * @return 1 for success or 0 for failure
     */ 
    
    public static int delete(int id)
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
        
        
        String query = "DELETE FROM Asset " +
                "WHERE asset_id = ?";
        
/*
 * try the query, if it works process data, otherwise catch the db exception
 */
        
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);

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

}
