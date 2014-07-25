package com.scrumware.login;


import java.io.*;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private LoginDB a_login = null;
	private String errmsg = null;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
        	
        	errmsg = null;
        	HttpSession sess = request.getSession(false);
        	//System.out.println(sess.getMaxInactiveInterval());
        	
        	/*check to see if there is an active session */
        	
        	
        	if(sess.getAttribute("role") != null) {
  
        		/*if there is an active session, redirect to home page*/
        		
        		getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
        		
        	} else {
        	
        		
        		/*if there is no active session, do login logic*/
        		
	        	if (request.getParameter("user_name").isEmpty() || request.getParameter("password").isEmpty()) {
	                
	        		/*both username and password must be present,
	        		 * otherwise set error message
	        		 */
	        		
	        		errmsg = "You must enter both user name and password."; 
	            
	            } else {
	            	
	            	/*validate user credentials against DB*/
	            	
	            	a_login = new LoginDB(request.getParameter("user_name"),request.getParameter("password"));
	            	
	            	/*if valid, create session
	            	 * set max inactive interval to 30 mins
	            	 * set role, id, and username as session attributes
	            	 */
	            	
	            	if (a_login.isValid()) {
	            		
	            		sess = request.getSession(false);
	            		//System.out.println(sess.getMaxInactiveInterval());
	            		sess.setAttribute("id", a_login.getId());
	            		sess.setAttribute("role", a_login.getRole());
	            		sess.setAttribute("user_name", request.getParameter("user_name"));
	            		request.setAttribute("user_name",request.getParameter("user_name"));
	            		request.setAttribute("role", a_login.getRole());
	            		
	            	} else {
	            		
	            		/*if not valid, set error message*/
	            		
	            		errmsg = "Sorry.  Something went wrong.  Please try again.";
	            		
	            	}
	            	
	            }
	        	
	        	if (errmsg == null || errmsg.equalsIgnoreCase("") || errmsg.isEmpty()) {
	        		
	        		/*if there are no error messages,
	        		 * session has been successfully created.
	        		 * send user to home page
	        		 */
	        		
	        		//request.setAttribute("user_name",session.getAttribute("user_name"));
	                getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
	        		
	        	} else {
	        		
	        		/*if there are error messages,
	        		 * login has failed.
	        		 * send user back to login page to try again
	        		 */
	        		
	        		request.setAttribute("errmsg", errmsg);
	        		//request.setAttribute("user_name", request.getParameter("user_name"));
	                getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
	                
	        	}
        	}
        	
        	/*exit servlet*/
        	
        } finally {            
            out.close();
        }
	
	}
	
	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
