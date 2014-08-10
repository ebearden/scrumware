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
import com.scrumware.login.SystemUnavailableException;
import com.scrumware.role.Role;
import com.scrumware.role.RoleDB;
import com.scrumware.user.*;

/**
 * Servlet implementation class ResetUserPass
 * 
 * @author emily kubic
 */
@WebServlet(name = "ResetUserPass", urlPatterns = {"/ResetUserPass", "/user/resetuserpass"})
public class ResetUserPass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetUserPass() {
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
        	
        	int user_role = SessionHelper.getSessionUserRole(request);
        	int user_id = SessionHelper.getSessionUserId(request);
        	int edit_id = 0;
        	String errmsg = null;
        	
        	if (request.getParameter("id")==null || request.getParameter("id").equals("")) {
                edit_id = user_id;
                //System.out.println("changing own pass.");
                String old_pass = "";
                try {
	        		old_pass = 
	        		com.scrumware.login.PasswordService.getInstance().encrypt(request.getParameter("old_password"));
	        	} catch(SystemUnavailableException e) {
	        		e.printStackTrace();
	        	}
                
                if (!UserDB.checkPassword(edit_id, old_pass)) {
                	errmsg="Please try again.";
                }
        	} else if (user_role==1) {
        		edit_id = Integer.parseInt(request.getParameter("id"));
                //System.out.println("changing other's pass.");
        	} else {
        		response.sendRedirect(request.getContextPath() + "/home.jsp");
    			return;
        	}
        	
        	
       
            if (!request.getParameter("new_password").equals(request.getParameter("confirm_new_pass"))) {
        		errmsg = errmsg + "Password and confirmation must match.\n";
        	}
            
            if (errmsg != null) {
            	
            	request.setAttribute("errmsg", errmsg);
            	
            	if (user_id == edit_id) {
            		getServletContext().getRequestDispatcher("/user/reset_pass.jsp").forward(request, response);
            	} else {
                	getServletContext().getRequestDispatcher("/user/reset_pass.jsp?id="+edit_id).forward(request, response);
            	}
            	
            }else {
            	
            	String new_pass = "";
                try {
	        		new_pass = 
	        		com.scrumware.login.PasswordService.getInstance().encrypt(request.getParameter("new_password"));
	        	} catch(SystemUnavailableException e) {
	        		e.printStackTrace();
	        	}
            	
            	UserDB.resetPassword(edit_id, new_pass);
            	
            	if (edit_id == user_id) {
            		request.setAttribute("msg", "Your password was successfully reset.");
            		getServletContext().getRequestDispatcher("/user/viewuser").forward(request, response);
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
