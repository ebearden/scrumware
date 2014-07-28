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
import com.scrumware.role.*;


/**
 * Servlet implementation class ViewUser
 */
@WebServlet(name = "ViewUser", urlPatterns = {"/ViewUser", "/user/viewuser"})
public class ViewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewUser() {
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
        	int id = 0;
        	Object ob = sess.getAttribute("role");
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
		// TODO Auto-generated method stub
		
		processRequest(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
