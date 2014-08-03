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
		}
		
		HttpSession session = request.getSession(false);
		if (session.getAttribute("id") == null || session.getAttribute("id").equals("")) {
			valid = false;
		} else if (session.getAttribute("user_name") == null || session.getAttribute("user_name").equals("")) {
			valid = false;
		} else if (session.getAttribute("role") == null || session.getAttribute("role").equals("")) {
			valid = false;
		} else {
			valid = true;
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
}
