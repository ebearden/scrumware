package com.scrumware.asset;

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
import com.scrumware.role.Role;
import com.scrumware.role.RoleDB;
import com.scrumware.user.User;
import com.scrumware.user.UserDB;

/**
 * Servlet implementation class AssetServlet
 */
@WebServlet(name = "/AssetServlet", urlPatterns = {"/AssetServlet", "/asset/assets"})
public class AssetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssetServlet() {
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
        	
        	if (request.getParameter("project_id") == null) {
        		
        		response.sendRedirect(request.getContextPath() + "/project/projects");
    			return;
        		
        	}
        	
            /* TODO output your page here. You may use following sample code. */
            
/** 
 * The title to be displayed on the jsp is set, then passed to the request.
*/            
            
            String title="View Project Assets";
            request.setAttribute("title", title);
            
            
/** 
 * A new arraylist is instantiated with the data returned from the db.  It is
 * then set on the request.
*/            
            
            ArrayList<User> users = UserDB.getUsers();
            //System.out.println("got users");
            request.setAttribute("users",users);
            
            int project_id = Integer.parseInt(request.getParameter("project_id"));
            ArrayList<Asset> assets = AssetDB.getAssets(project_id);
            //System.out.println("got users");
            request.setAttribute("assets",assets);
            
            request.setAttribute("project_id", project_id);
            
/** 
 * The data is forwarded to ViewUsers.jsp
*/            
            
            getServletContext().getRequestDispatcher("/asset/assets.jsp").forward(request, response);
            
 /** 
* Processing is done, so exit script.
*/           
            
            
        } finally {            
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
