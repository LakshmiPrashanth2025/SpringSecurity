package com.example.SpringSecurity.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.SpringSecurity.service.JWTAuthorizationService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTAuthorizationService jwtAuthService;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader!=null && authHeader.startsWith("Bearer")) {
			String jwtToken = authHeader.substring(7);
			System.out.println("Auth Header: jwtToken" + jwtToken);
			
			try {
				final String userEmail = jwtAuthService.extractUsername(jwtToken);

				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				if (userEmail != null && authentication == null) {
					UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

					if (jwtAuthService.isTokenValid(jwtToken, userDetails)) {
						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());

						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authToken);
					}
				}

				filterChain.doFilter(request, response);
			} catch (Exception exception) {
				System.out.println("Exception in JwtFilter: " + exception.getMessage());
				throw exception;
				//handlerExceptionResolver.resolveException(request, response, null, exception);
			}
			
			
		}
		else {
			filterChain.doFilter(request, response);	
			return;
		}
		
		
	}
	
	

}
