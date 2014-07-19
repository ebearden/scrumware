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
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private int id;
    private int role;
    private int active;
    
    /**
     * This is the no arg constructor
     */
    public User()
    {
        username = "";
        firstname = "";
        lastname = "";
        email = "";
        id = 0;
        role = 0;
        active = 0;
    }
    
    /**
     *
     * This constructor takes String args
     * 
     * @param name string input from user
     * @param email string input from user
     * @param id string from user
     * @param role string input from user
     */
    public User(String uname, String fname, String lname, String email, String id, int role, int active)
    {
        this.username = uname;
        this.firstname = fname;
        this.lastname = lname;
        this.email = email;
        if (isNumeric(id)) {
            this.id = Integer.parseInt(id);
        } else {
            this.id = 0;
        }
        this.role = role;
        this.active = active;
    }

    /**
     * This method retrieves the User Username for a User Object
     *
     * @return name String stored in User object
     */
    public String getUsername()
    {
        return username; 
    }
    
    /**
     * This method sets User.Username to the passed value
     *
     * @param name string input from user
     */
    public void setUsername(String uname)
    {
        this.username = uname;
    }
    
    
    /**
     * This method retrieves the User Firstname for a User Object
     *
     * @return name String stored in User object
     */
    public String getFirstname()
    {
        return firstname; 
    }
    
    /**
     * This method sets User.Firstname to the passed value
     *
     * @param name string input from user
     */
    public void setFirstname(String fname)
    {
        this.firstname = fname;
    }
    
    /**
     * This method retrieves the User Firstname for a User Object
     *
     * @return name String stored in User object
     */
    public String getLastname()
    {
        return lastname; 
    }
    
    /**
     * This method sets User.Firstname to the passed value
     *
     * @param name string input from user
     */
    public void setLastname(String lname)
    {
        this.lastname = lname;
    }
    
    /**
     * This method sets User.Email to the passed value
     *
     * @param email string input from user
     */
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    
    /**
     * This method retrieves the User.Email for a User Object
     *
     * @return email String stored in User object
     */
    public String getEmail()
    {
        return email; 
    }
    
    /**
     * This method sets Product.Price to the passed value
     *
     * @param id int input from user
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * This method retrieves the User.Id for a User Object
     *
     * @return id int stored in User object
     */
    public int getId()
    {
        return id; 
    }
    
    
    /**
     * This method sets User.Role to the passed value
     *
     * @param role string input from user
     */
    public void setRole(int role)
    {
        this.role = role;
    }

    /**
     * This method retrieves the User.Role for a User Object
     *
     * @return price double stored in User object
     */
    public int getRole()
    {
        return role; 
    }
    
    /**
     * This method sets User.Role to the passed value
     *
     * @param role string input from user
     */
    public void setActive(int active)
    {
        this.active = active;
    }

    /**
     * This method retrieves the User.Role for a User Object
     *
     * @return price double stored in User object
     */
    public int getActive()
    {
        return active; 
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