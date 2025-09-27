package com.hazalyarimdunya.auth_jwt_app.config;
//
//import com.hazalyarimdunya.auth_jwt_app.entity.User;
//import com.hazalyarimdunya.auth_jwt_app.services.JwtService;
//import com.hazalyarimdunya.auth_jwt_app.services.UserService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final UserService userService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        final String authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        final String token = authHeader.substring(7); // "Bearer          " kısmını çıkar
//        final String username = jwtService.extractUsername(token);
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            User user = userService.getByUsername(username)
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//
//            if (jwtService.validateToken(token, user)) {
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(
//                                user.getUsername(),
//                                null,
//                                List.of(new SimpleGrantedAuthority(user.getRole().name()))//admin or user
//                        );
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//}



import com.hazalyarimdunya.auth_jwt_app.entity.UserEntity;
import com.hazalyarimdunya.auth_jwt_app.services.JwtService;
import com.hazalyarimdunya.auth_jwt_app.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        final String username = jwtService.extractUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserEntity userEntity = userService.getByUsername(username)
                    .orElse(null);

            if (userEntity != null && jwtService.validateToken(token, userEntity)) {
                // DİKKAT: burada authority = "USER" veya "ADMIN"
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userEntity.getUsername(),
                                null,
                                List.of(new SimpleGrantedAuthority(userEntity.getRole().name()))
                        );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}

