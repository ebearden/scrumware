package com.scrumware.user;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Without these imports, this class will not work.
 */

import java.io.Serializable;



/**
 *  This model class creates a Product Object and provides getters and setters
 * for this object.
 * 
 * @see Data.ProductDB
 * 
 * @author eakubic
 */
//public class User implements Serializable

public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
    private String name;
    private String email;
    private int id;
    
    /**
     * This is the no arg constructor
     */
    public User()
    {
        name = "";
        email = "";
        id = 0;
    }
    
    /**
     *
     * This constructor takes String args
     * 
     * @param code string input from user
     * @param desc string input from user
     * @param sprice string input from user
     */
    public User(String name, String email, String id)
    {
        this.name = name;
        this.email = email;
        if (isNumeric(id)) {
            this.id = Integer.parseInt(id);
        } else {
            this.id = 0;
        }
    }
    
    /**
     * This method sets Product.Code to the passed value
     *
     * @param code string input from user
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * This method retrieves the User Name for a User Object
     *
     * @return user_name String stored in Product object
     */
    public String getName()
    {
        return name; 
    }
    
    /**
     * This method sets Product.Description to the passed value
     *
     * @param description string input from user
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * This method retrieves the Product.Description for an Product Object
     *
     * @return description String stored in Product object
     */
    public String getEmail()
    {
        return email; 
    }
    
    /**
     * This method sets Product.Price to the passed value
     *
     * @param price string input from user
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * This method retrieves the Product.Price for an Product Object
     *
     * @return price double stored in Product object
     */
    public int getId()
    {
        return id; 
    }
    
    
    /**
     * This method checks whether the passed value is numeric.
     *
     * @param str string input
     * @return true or false
     */
    public static boolean isNumeric(String str) {  
        try {  
            int i = Integer.parseInt(str);  
        } catch(NumberFormatException nfe) {  
            return false;  
        }  
        return true;  
    }
}