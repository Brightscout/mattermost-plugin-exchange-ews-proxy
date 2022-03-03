package com.ews.ews.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ews.ews.exception.UnauthorizedException;
import com.ews.ews.payload.ApiResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	private String secretAuthKey;

	@Autowired
	public void setValues(@Value("${app.secretAuthKey}") String secretAuthKey) {
		this.secretAuthKey = secretAuthKey;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = getTokenFromRequest(request);
			if (StringUtils.hasText(token) && secretAuthKey != null && secretAuthKey.equals(token)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(token,
						token, null);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (Exception ex) {
			throw new UnauthorizedException(new ApiResponse(Boolean.FALSE,
					"Could not set user authentication in security context. Error: " + ex.getMessage()));
		}

		filterChain.doFilter(request, response);
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}
