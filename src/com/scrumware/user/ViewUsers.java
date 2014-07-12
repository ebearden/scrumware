package com.scrumware.user;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


//import Data.ProductDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet controller displays all Users in the db
 * and has view: ViewUsers.jsp.  It retrieves the data from the
 * db, then puts the returned ArrayList on the request.
 * 
 * @see User#getUsers() 
 * 
 * @author eakubic
 */
@WebServlet(name = "ViewUsers", urlPatterns = {"/ViewUsers"})
public class ViewUsers extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            
/** 
 * The title to be displayed on the jsp is set, then passed to the request.
*/            
            
            String title="View Users";
            request.setAttribute("title", title);
            
            String msg="test";
            request.setAttribute("msg", msg);
            
/** 
 * A new arraylist is instantiated with the data returned from the db.  It is
 * then set on the request.
*/            
            
            ArrayList<User> users = UserDB.getUsers();
            //System.out.println("got users");
            request.setAttribute("users",users);
            
/** 
 * The data is forwarded to ViewProducts.jsp
*/            
            
            getServletContext().getRequestDispatcher("/ViewUsers.jsp").forward(request, response);
            
 /** 
* Processing is done, so exit script.
*/           
            
            
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
