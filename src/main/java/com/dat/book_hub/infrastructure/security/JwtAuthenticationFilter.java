package com.dat.book_hub.infrastructure.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        String authHeadeString = request.getHeader("Authorization");

        if (authHeadeString == null || !authHeadeString.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeadeString.substring(7);

        try {
            String username = jwtService.extractUsername(jwt);
            String tokenType = jwtService.extractTokenType(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (tokenType.equals("access") && username != null
                    && userDetails != null) {
                // Set authentication in the context

                if (userDetails.isEnabled() == false) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");

                    response.getWriter().write("""
                    {
                        "code": 401,
                        "success": false,
                        "message": "account is disabled"
                    }
                    """);
                }
                if (jwtService.isTokenValid(jwt, username, tokenType)) {
                    // Set authentication in the context
                    // You can use SecurityContextHolder to set the authentication
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    // Set the authentication in the SecurityContext
                    // SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Proceed with the filter chain
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            response.getWriter().write("""
                    {
                        "code": 401,
                        "success": false,
                        "message": "Invalid or expired JWT token"
                    }
                    """);
        }
    }
}