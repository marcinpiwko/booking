package com.piwko.booking.api.security;

import com.piwko.booking.util.StringUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {
        try {
            String tokenHeader = request.getHeader(jwtUtil.getHeaderName());
            if (StringUtil.isEmpty(tokenHeader) || !tokenHeader.startsWith(jwtUtil.getTokenPrefix())) {
                return;
            }
            String token = tokenHeader.replace(jwtUtil.getTokenPrefix(), "");
            jwtUtil.validateToken(token);
            String username = jwtUtil.getUsernameFromToken(token);
            String role = jwtUtil.getRoleFromToken(token);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(new SimpleGrantedAuthority(role))));
        } catch (JwtException e) {
            throw new IllegalStateException("Token can not be trusted: ");
        } finally {
            chain.doFilter(request, response);
        }
    }
}
