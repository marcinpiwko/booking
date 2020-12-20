package com.piwko.booking.api.security;

import com.piwko.booking.persistence.model.User;
import com.piwko.booking.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.internal.Function;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    private final Set<String> blackList = new HashSet<>();

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getRoleFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return "ROLE_" + claims.get("role", String.class);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user, boolean rememberMe) {
        return Jwts.builder().setClaims(new HashMap<>())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(getTokenExpirationDate(rememberMe))
                .claim("role", user.getRole().getName())
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey()).compact();
    }

    private Date getTokenExpirationDate(boolean rememberMe) {
        return rememberMe ? java.sql.Date.valueOf(LocalDate.now().plusDays(jwtProperties.getRememberMe()))
                : new Date(System.currentTimeMillis() + jwtProperties.getValidity());
    }

    public void invalidateToken(String token) {
        if (!StringUtil.isEmpty(token)) {
            this.blackList.add(token.replace(jwtProperties.getTokenPrefix(), ""));
        }
    }

    public void validateToken(String token) {
        if (blackList.contains(token) || isTokenExpired(token)) {
            throw new JwtException("invalidated or expired");
        }
    }

    public String getHeaderName() {
        return jwtProperties.getHeaderName();
    }

    public String getTokenPrefix() {
        return jwtProperties.getTokenPrefix();
    }

    @Scheduled(fixedDelayString = "${booking.security.jwtValidity}")
    private void refreshBlackList() {
        blackList.removeIf(this::isTokenExpired);
    }
}