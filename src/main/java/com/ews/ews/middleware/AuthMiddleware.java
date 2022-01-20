package com.ews.ews.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ews.ews.exception.UnauthorizedException;
import com.ews.ews.payload.ApiResponse;

public class AuthMiddleware extends HandlerInterceptorAdapter {

	private String secretAPIKey;
	
	public AuthMiddleware(String secretAPIKey) {
		this.secretAPIKey = secretAPIKey;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO: Add Authorization key to constants.
		String authHeader = request.getHeader("Authorization");

		// TODO: Raise proper exception instead of returning false 
	    if (authHeader == null || authHeader.length() == 0){
	    	throw new UnauthorizedException(new ApiResponse(Boolean.FALSE, "Authorization header missing."));
	    }
	    
	    String[] authHeaderSplit = authHeader.split(" ");
	    
	    if (authHeaderSplit.length != 2 || !authHeaderSplit[0].equals("Bearer") || authHeaderSplit[1].length() == 0) {
	    	throw new UnauthorizedException(new ApiResponse(Boolean.FALSE, "Invalid authorization token "));
	    }

	    if (!secretAPIKey.equals(authHeaderSplit[1])) {
	    	throw new UnauthorizedException(new ApiResponse(Boolean.FALSE, "Invalid authorization token "));
	    }
	    
	    return true;
	}

}
