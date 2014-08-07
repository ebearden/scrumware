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
import com.scrumware.role.*;


/**
 * Servlet implementation class ViewUser
 * 
 * @author emily kubic
 */
@WebServlet(name = "ViewUser", urlPatterns = {"/ViewUser", "/user/viewuser"})
public class ViewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewUser() {
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
        	int id = 0;
        	Object ob = sess.getAttribute("id");
            if (ob instanceof Integer) {
            	id = (Integer) ob;
            } else {
            	System.out.println("WTF this should be an int.");
            }
            
            RoleDB roledb = new RoleDB();
            ArrayList<Role> roles = roledb.getRoles();
            
        	UserDB userdb = new UserDB();
        	User user = userdb.getUser(id);
        	//System.out.println(user.getUsername());
        	
        	request.setAttribute("roles", roles);
        	request.setAttribute("user", user);
        	getServletContext().getRequestDispatcher("/user/view_user.jsp").forward(request, response);
        	
        }
        
        finally {            
        	out.close();
        }
        
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		processRequest(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		processRequest(request, response);
		
	}
}
