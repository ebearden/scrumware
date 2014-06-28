package com.scrumware.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JDBCHelper {
	/**
	 * Get a connection to the database
	 * @return the connection.
	 */
	public static Connection getConnection() {
		Connection con = null;
		Context initCtx;
		Context envCtx;
		DataSource ds;
		
		try {
			initCtx = new InitialContext();
			envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/scrumwarestaging");
			con = ds.getConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return con;
	}
	
	public static void freeConnection(Connection c)
    {
        try
        {
            c.close();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            //return false;
        }
        //return true;
    }
}
