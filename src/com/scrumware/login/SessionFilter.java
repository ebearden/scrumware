package com.scrumware.login;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author emily kubic
 */
 
public class SessionFilter implements Filter {
 
    private ArrayList<String> urlList;
     
    public void destroy() {
    }
 
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {

    	HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res; 
        
        boolean authorized = false;
    	boolean ok_url = false;

        
        
        HttpSession sess = request.getSession(false);
        if (sess != null) {
	        if (sess.getAttribute("role") != null &&
	        		sess.getAttribute("id") != null &&
	        		sess.getAttribute("user_name") != null) {
	        	//System.out.println("valid session");
	        	authorized = true;
	        }
        } 
        
        if (!authorized) {
        	String url = request.getServletPath();
            //System.out.println(url);
            if (urlList.contains(url)) {
            	//System.out.println("url ok");
            	ok_url = true;
            }
        }
    	
                
        if (ok_url || authorized) {
            chain.doFilter(request, response);
            return;
        } else {
        	//response.sendRedirect(request.getContextPath() + "/login.jsp");
            request.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        
        //throw new ServletException ("Try again sucker.");
        
        
    }
 
    public void init(FilterConfig config) throws ServletException {
        String urls = config.getInitParameter("avoid-urls");
        StringTokenizer token = new StringTokenizer(urls, ",");
 
        urlList = new ArrayList<String>();
 
        while (token.hasMoreTokens()) {
            urlList.add(token.nextToken());
            //System.out.println("url added");
        }
    }
    
}