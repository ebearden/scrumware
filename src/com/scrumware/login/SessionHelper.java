package com.scrumware.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.taglibs.standard.lang.jstl.test.beans.PublicInterface2;

import com.scrumware.config.Constants;

/**
 * @author Elvin Bearden
 * 
 */
public class SessionHelper {

	public static boolean validateSession(HttpServletRequest request, HttpServletResponse response) {
		boolean valid = false;
		if (request.getParameter("key") != null && request.getParameter("key").equals(Constants.LOGIN_KEY)) {
			valid = true;
		} else {
			HttpSession session = request.getSession(false);
			if (session != null) {
				if (session.getAttribute("id") == null || session.getAttribute("id").equals("")) {
					valid = false;
				} else if (session.getAttribute("user_name") == null || session.getAttribute("user_name").equals("")) {
					valid = false;
				} else if (session.getAttribute("role") == null || session.getAttribute("role").equals("")) {
					valid = false;
				} else {
					valid = true;
				}				
			} else {
				valid = false;
			}
		}
		
		
		
		if (!valid) {
			try {
				response.sendRedirect(request.getContextPath() + "/login.jsp");	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return valid;
	}
	
	/**
	 * Get the current users userId.
	 * @param request - the current HttpServletRequest.
	 * @return - userId as an Integer.
	 */
	public static Integer getSessionUserId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("id") != null) {
			System.out.println(session.getAttribute("id"));
			return Integer.parseInt(session.getAttribute("id").toString());
		}
		return null;
	}
	
	/**
	 * Get the current users role.
	 * @param request - the current HttpServletRequest.
	 * @return - user role as an Integer.
	 */
	public static Integer getSessionUserRole(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("role") != null) {
			System.out.println(session.getAttribute("role"));
			return Integer.parseInt(session.getAttribute("role").toString());
		}
		return null;
	}
	
	/**
	 * Get the current users username.
	 * @param request - the current HttpServletRequest.
	 * @return - username as a String.
	 */
	public static String getSessionUserName(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("user_name") != null) {
			System.out.println(session.getAttribute("user_name"));
			return session.getAttribute("user_name").toString();
		}
		return null;
	}
	
}
