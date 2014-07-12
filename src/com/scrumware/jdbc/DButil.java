package com.scrumware.jdbc;

import java.sql.*;

/**
 *This class manages SQL query statements and result sets
 * 
 * @see Data.ProductDB
 * 
 * @author eakubic
 */
public class DButil {
    /**
     *
     * 
     * @param s a SQL statment
     */
    public static void closeStatement(Statement s)
    {
        try
        {
            if (s != null) {
                s.close();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     *
     * @param ps a prepared SQL statement
     */
    public static void closePreparedStatement(Statement ps)
    {
        try
        {
            if (ps != null) {
                ps.close();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param rs a SQL result set
     */
    public static void closeResultSet(ResultSet rs)
    {
        try
        {
            if (rs != null) {
                rs.close();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

}
