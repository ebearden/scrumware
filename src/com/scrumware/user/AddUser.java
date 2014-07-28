package com.scrumware.user;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scrumware.role.*;
import com.scrumware.config.Constants;
import com.scrumware.login.*;

/**
 * Servlet implementation class AddUser
 */
@WebServlet(name = "AddUser", urlPatterns = {"/AddUser", "/user/adduser"})
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUser() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
        	
        	if (!isValidSession(request)) {
    			response.sendRedirect(request.getContextPath() + "/login.jsp");
    			return;
    		}
        	
        	HttpSession sess = request.getSession(false);
        	int user_role = 0;
        	Object ob = sess.getAttribute("role");
            if (ob instanceof Integer) {
            	user_role = (Integer) ob;
            } else {
            	System.out.println("WTF this should be an int.");
            }
            
            if (user_role != 1) {
            	response.sendRedirect(request.getContextPath() + "/home.jsp");
    			return;
            }
        	
        	ArrayList<Role> roles = RoleDB.getRoles();
            //System.out.println("got users");
            request.setAttribute("roles",roles);
            
        	if (request.getParameter("user_name")==null) {
                
                getServletContext().getRequestDispatcher("/user/new_user.jsp").forward(request, response);
                
            
            } else {
        	
	        	String username = request.getParameter("user_name");
	        	String firstname = request.getParameter("first_name");
	        	String lastname = request.getParameter("last_name");
	        	String email = request.getParameter("email");
	        	String password = "";
	        	try {
	        		password = com.scrumware.login.PasswordService.getInstance().encrypt(request.getParameter("password"));
	        	} catch(SystemUnavailableException e) {
	        		e.printStackTrace();
	        	}
	        	int role = Integer.parseInt(request.getParameter("role"));
	        	int active = Integer.parseInt(request.getParameter("active"));
	        	String errmsg ="";
	        	
	        	if (username==null || firstname==null || lastname==null ||
	        		password==null || email==null || role==-1 || active==-1) {
            		errmsg = "You may not leave any field blank.\n";
            	}
	        	
	        	if (!request.getParameter("password").equals(request.getParameter("confirm_pass"))) {
	        		errmsg = errmsg + "Password and confirmation must match.\n";
	        	}
	        	
	        	//ArrayList<User> users = UserDB.getUsers();
	        	UserDB users = new UserDB();
	        	
	        	if (users.usernameExists(username)) {
	        		errmsg = errmsg+"An account already exists with this username.\n";
	        	}
	        	
	        	//System.out.println(users.usernameExists(username));
	        	
	        	//System.out.println(email);
	        	
	        	if (users.emailExists(email)) {
	        		errmsg = errmsg+"An account already exists with this email address.\n";
	        	}
	        	
	        	//System.out.println(users.emailExists(email));
	        	
	        	if (errmsg.equals("") || errmsg == null) {
	        		
	        		User u = new User();
	        		u.setUsername(username);
	        		u.setFirstname(firstname);
	        		u.setLastname(lastname);
	        		u.setEmail(email);
	        		u.setRole(role);
	        		u.setActive(active);
	        		
	        		users.insert(u, password);
	        		getServletContext().getRequestDispatcher("/user/users").forward(request, response);
	        		
	        		
	        	} else {
	        		
	        		request.setAttribute("errmsg", errmsg);
	        		request.setAttribute("username", username);
	        		request.setAttribute("firstname", firstname);
	        		request.setAttribute("lastname", lastname);
	        		request.setAttribute("email", email);
	        		request.setAttribute("role", role);
	        		request.setAttribute("active", active);
	        		getServletContext().getRequestDispatcher("/user/new_user.jsp").forward(request, response);
	        		
	        	}
        	
            }
        	
        }
        
        finally {            
        	out.close();
        }
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

        processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

        processRequest(request, response);
	}
    
    private boolean isValidSession(HttpServletRequest request) {
		if (request.getParameter("key") != null && request.getParameter("key").equals(Constants.LOGIN_KEY)) {
			return true;
		}
		
		HttpSession session = request.getSession(false);
		if (session.getAttribute("id") == null || session.getAttribute("id").equals("")) {
			return false;
		} else if (session.getAttribute("user_name") == null || session.getAttribute("user_name").equals("")) {
			return false;
		} else if (session.getAttribute("role") == null || session.getAttribute("role").equals("")) {
			return false;
		} else {
			return true;
		}
	}

}
