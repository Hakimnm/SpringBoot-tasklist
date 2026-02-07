package com.programmingtechie.tasklist.web.security;

import com.programmingtechie.tasklist.domain.exception.ResourceNotFoundException;
import com.sun.net.httpserver.HttpServer;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvedir jwtTokenProvedir;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String bearerToken=((HttpServletRequest) servletRequest).getHeader("Authorization");
        if(bearerToken!=null && bearerToken.startsWith("Bearer ")){
            bearerToken=bearerToken.substring(7);
        }
        if(bearerToken!=null && jwtTokenProvedir.validateToken(bearerToken)){
            try{
                Authentication authentication=jwtTokenProvedir.getAuthentication(bearerToken);
                if(authentication!=null){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch(ResourceNotFoundException ignored){}


        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
