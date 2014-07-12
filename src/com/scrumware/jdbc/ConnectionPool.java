package com.scrumware.jdbc;

import java.sql.*;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * This class creates a pool of 100 connections available for connecting to
 * the database holding Product data by creating a context and setting the 
 * location of the database
 * 
 * @see Data.DButil
 *
 * @author eakubic
 */
public class ConnectionPool

/**
 * pool and datasource are instantiated
 * 
 */

{
    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;
 
    private ConnectionPool()
    {
        try
        {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/scrumwarestaging");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This methods checks to see if there is already a connection to the
     *  db and creates one if there isn't one present.
     * 
     * @return instance of ConnectionPool
     */
    public static ConnectionPool getInstance()
    {
        if (pool == null)
        {
            pool = new ConnectionPool();
        }
        return pool;
    }

    /**
     * This returns a new instance of a connection to the db
     * 
     * @return instance of db connection
     */
    public Connection getConnection()
    {
        try
        {
            return dataSource.getConnection();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            return null;
        }
    }
    
    /**
     * Thie method frees the connection to the db.
     *
     * @param c a connection from connection pool
     */
    public void freeConnection(Connection c)
    {
        try
        {
            c.close();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }
}