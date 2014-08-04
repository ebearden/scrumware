package com.scrumware.asset;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.scrumware.config.Constants;
import com.scrumware.login.SessionHelper;

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
        	
        	if (!SessionHelper.validateSession(request, response)) {
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
}
