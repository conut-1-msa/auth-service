package io.github.conut.msa.auth.common.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HeaderAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String userUuid = request.getHeader("X-User-UUID");
        String userRoles = request.getHeader("X-User-Roles");
        if (userUuid != null && userRoles != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (var userRole: userRoles.split(",")) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole);
                authorities.add(authority);
            }
            UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userUuid, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
