package com.scrumware.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scrumware.config.Constants;
import com.scrumware.login.SessionHelper;
import com.scrumware.role.Role;
import com.scrumware.role.RoleDB;

/**
 * Servlet implementation class EditUser
 */
@WebServlet(name = "EditUser", urlPatterns = {"/EditUser", "/user/edituser"})
public class EditUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUser() {
        super();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
        	
        	if (!SessionHelper.validateSession(request, response)) {
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
            
        	if (request.getParameter("id")==null) {
                
                //getServletContext().getRequestDispatcher("../user/users").forward(request, response);
                response.sendRedirect("../user/users");
            
            } else {

	        	int id = -1;
            	String username = null;
	        	String firstname = null;
	        	String lastname = null;
	        	String email = null;
	        	int role = -1;
	        	int active = -1;
            	String errmsg = null;
            	boolean edited = false;
            	
            	User user = UserDB.getUser(Integer.parseInt(request.getParameter("id")));
            	
            	if (request.getParameter("user_name") == null) {
            	
            		id = user.getId();
            		username = user.getUsername();
		        	firstname = user.getFirstname();
		        	lastname = user.getLastname();
		        	email = user.getEmail();
		        	role = user.getRole();
		        	active = user.getActive();
		        	
		        	//System.out.println("Got data from user object.");
		        	
		        	//getServletContext().getRequestDispatcher("/user/edit_user.jsp").forward(request, response);
        	
            	} else {
            	
            		id = Integer.parseInt(request.getParameter("id"));
		        	username = request.getParameter("user_name");
		        	firstname = request.getParameter("first_name");
		        	lastname = request.getParameter("last_name");
		        	email = request.getParameter("email");
		        	role = Integer.parseInt(request.getParameter("role"));
		        	active = Integer.parseInt(request.getParameter("active"));
		        	edited = true;
		        	
            	}
            	
            	if (username==null || firstname==null || lastname==null ||
            			email==null || role==-1 || active==-1) {
            		errmsg = "You may not leave any field blank.\n";
            	}
            	
	        	//ArrayList<User> users = UserDB.getUsers();
	        	UserDB users = new UserDB();
	        	
	        	if (users.usernameExists(username)) {
	        		if (!user.getUsername().equals(username)) {
	        			errmsg = errmsg+"An account already exists with this username.\n";
	        		}
	        	}
	        	
	        	//System.out.println(users.usernameExists(username));
	        	
	        	//System.out.println(email);
	        	
	        	if (users.emailExists(email)) {
	        		if (user.getEmail() != email) {
	        			errmsg = errmsg+"An account already exists with this email address.\n";
	        		}
	        	}
	        	
	        	//System.out.println(users.emailExists(email));
	        	
	        	if (errmsg != null || !edited) {
	        		
	        		//System.out.println(edited);
	        		
	        		request.setAttribute("id", id);
	        		request.setAttribute("errmsg", errmsg);
	        		request.setAttribute("username", username);
	        		request.setAttribute("firstname", firstname);
	        		request.setAttribute("lastname", lastname);
	        		request.setAttribute("email", email);
	        		request.setAttribute("role", role);
	        		request.setAttribute("active", active);            	
	        		
	        		getServletContext().getRequestDispatcher("/user/edit_user.jsp").forward(request, response);

	        		
	        	} else {
	            	
	        		//System.out.println(user.getId());

	        		user.setUsername(username);
	        		user.setFirstname(firstname);
	        		user.setLastname(lastname);
	        		user.setEmail(email);
	        		user.setRole(role);
	        		user.setActive(active);
	        		
	        		users.update(user);
	        		response.sendRedirect("../user/users");
	        		//getServletContext().getRequestDispatcher("/user/users").forward(request, response);
	        		
	        		
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
    	
    	processRequest(request, response);
    	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	
    	processRequest(request, response);
    	
	}
}
