package com.marktech.task_management_system.config.filter;

import com.marktech.task_management_system.config.service.JwtService;
import com.marktech.task_management_system.config.service.MyUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserDetailService detailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // extracting token from header
        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(header != null && header.startsWith("Bearer ")) {

            token = header.substring(7);
            username = jwtService.extractUserName(token);


        }

        if(username != null  && SecurityContextHolder.getContext().getAuthentication() == null) {
            // checking the user present in DB or not
            UserDetails details = detailService.loadUserByUsername(username);

            if(jwtService.validateToken(token , details)) {

                UsernamePasswordAuthenticationToken authToken  =  new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }
}
