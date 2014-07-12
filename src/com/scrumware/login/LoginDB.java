package com.scrumware.login;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.scrumware.jdbc.*;

public class LoginDB {
	
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
            
            String query = "SELECT password FROM Sys_User WHERE email = ?";
            
    /*
     * try the query, if it works process data, otherwise catch the db exception
     */        
            
            try
            {        
                ps = connection.prepareStatement(query);
                ps.setString(1, product.getCode());
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            
	        
}
