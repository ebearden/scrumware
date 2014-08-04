package com.scrumware.asset;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scrumware.config.Constants;

/**
 * Servlet implementation class AssetDownload
 */
@WebServlet(name="/AssetDownload", urlPatterns={"/AssetDownload", "/asset/download"})
public class AssetDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssetDownload() {
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
        	

        	String name = request.getParameter("name");
        	int id = Integer.parseInt(request.getParameter("id"));
        	
        	AssetDB db = new AssetDB();
        	Asset a = db.getAsset(name,id);
        	
        	String path = getServletContext().getRealPath(File.separator);
        	response.setContentType("application/octet-stream");
        	response.setHeader("content-disposition","attachment; filename=" + name);
        	
        	FileInputStream in = new FileInputStream(a.getLocation());
        	
        	int i = in.read();
        	while (i != -1){
        		out.write(i);
        		i = in.read();
        	}
        	in.close();
        	
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
