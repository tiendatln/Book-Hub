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

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // 1. Không có Bearer Token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        try {
            // 2. Lấy thông tin JWT
            String username = jwtService.extractUsername(jwt);
            String tokenType = jwtService.extractTokenType(jwt);

            // 3. Kiểm tra token
            if (username == null
                    || !"access".equals(tokenType)
                    || !jwtService.isTokenValid(jwt, username, "access")) {

                sendError(response, 401, "Invalid or expired JWT token");
                return;
            }

            // 4. Lấy UserDetails
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            // 5. Kiểm tra tài khoản
            if (!userDetails.isEnabled()) {
                sendError(response, 401, "Account is disabled");
                return;
            }

            // 6. Debug Role
            System.out.println("Username: " + userDetails.getUsername());
            System.out.println("User Roles: " + userDetails.getAuthorities());

            // 7. Tạo Authentication
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            // 8. Lưu Authentication vào SecurityContext
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        } catch (Exception e) {

            // Chỉ bắt lỗi trong quá trình xác thực
            SecurityContextHolder.clearContext();

            logger.error("JWT authentication failed", e);

            sendError(response, 401, "Invalid or expired JWT token");
            return;
        }

        // 9. Tiếp tục xử lý request
        // Đặt ngoài try-catch để không bắt lỗi Controller/Service
        filterChain.doFilter(request, response);
    }

    private void sendError(
            HttpServletResponse response,
            int status,
            String message
    ) throws IOException {

        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");

        response.getWriter().write("""
                {
                    "code": %d,
                    "success": false,
                    "message": "%s"
                }
                """.formatted(status, message));
    }
}