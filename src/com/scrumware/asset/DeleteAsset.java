package com.scrumware.asset;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scrumware.login.SessionHelper;
import com.scrumware.user.UserDB;

/**
 * Servlet implementation class DeleteAsset
 */
@WebServlet(name="/DeleteAsset", urlPatterns={"/DeleteAsset", "/asset/delete"})
public class DeleteAsset extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAsset() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
        	
        	if (!SessionHelper.validateSession(request, response)) {
    			return;
    		}
        	
        	int asset_id = 0;
        	int project_id = 0;
        	String msg = null;
        	
        	if (request.getParameter("id") != null) {
        		asset_id = Integer.parseInt(request.getParameter("id"));
        	} else {
        		getServletContext().getRequestDispatcher("/project/projects").forward(request, response);
        	}
        	
        	Asset a = AssetDB.getAsset(asset_id);
        	
        	if (request.getParameter("confirm") != null) {
        		project_id = a.getProject();
        		if (AssetDB.delete(asset_id) == 0) {
        			msg = "The file was not deleted.";
        			request.setAttribute("msg", msg);
        			getServletContext().getRequestDispatcher("/asset/delete_asset.jsp?id="+asset_id).forward(request, response);
        		} else {
        			response.sendRedirect(request.getContextPath() + "/asset/assets?project_id="+project_id);
        		}
        	} else {
        		request.setAttribute("users", UserDB.getUsers());
        		request.setAttribute("asset", a);
        		getServletContext().getRequestDispatcher("/asset/delete_asset.jsp").forward(request, response);
        	}
        	
        } finally {            
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

}
