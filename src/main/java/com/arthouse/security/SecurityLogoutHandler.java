package com.arthouse.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

public class SecurityLogoutHandler extends SecurityContextLogoutHandler  {
	
	public SecurityLogoutHandler() {
		  super();
		 }
	
	public void logout(HttpServletRequest request, HttpServletResponse reponse,
			   Authentication authentication) {
			  try {
			   HttpSession session = request.getSession(false);
			   if (session != null) {
	                session.invalidate();
	                /*session.removeAttribute("cookie");*/
	               
	            }
			   
			  } catch (Exception e) {
			   // TODO Auto-generated catch block
			   
			   e.printStackTrace();
			  }
			  SecurityContextHolder.clearContext();
			  super.logout(request, reponse, authentication);
			 
			
			
			 }


}
