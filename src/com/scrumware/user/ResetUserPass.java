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

import com.scrumware.role.Role;
import com.scrumware.role.RoleDB;
import com.scrumware.user.*;

/**
 * Servlet implementation class ResetUserPass
 */
@WebServlet(name = "ResetUserPass", urlPatterns = {"/ResetUserPass", "/user/resetuserpass"})
public class ResetUserPass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetUserPass() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
        	
        	HttpSession sess = request.getSession(false);
        	UserDB db = new UserDB();
        	int id = 0;
        	String errmsg = null;
            
        	if (request.getParameter("id")==null) {
        		
                Object ob = sess.getAttribute("id");
                if (ob instanceof Integer) {
                	id = (Integer) ob;
                } else {
                	System.out.println("WTF this should be an int.");
                }

                System.out.println(id);
                System.out.println(request.getParameter("old_password"));
                
                if (!db.checkPassword(id, request.getParameter("old_password"))) {
                	errmsg="Please try again.";
                }
                
                
            } else {

	        	id = Integer.parseInt(request.getParameter("id"));
        	
            }
       
            if (!request.getParameter("new_password").equals(request.getParameter("confirm_new_pass"))) {
        		errmsg = errmsg + "Password and confirmation must match.\n";
        	}
            
            if (errmsg !=null) {
            	
            	request.setAttribute("errmsg", errmsg);
            	getServletContext().getRequestDispatcher("/user/reset_pass.jsp").forward(request, response);
            	
            } else {
            	
            	db.resetPassword(id, request.getParameter("new_password"));
            	
            	if (id == (Integer) sess.getAttribute("id")) {
            		request.setAttribute("user_name",(String) sess.getAttribute("user_name"));
            		getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
            	} else {
            		response.sendRedirect("../user/users");
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

}
