package com.espe.loanservice.config;

import com.espe.loanservice.exception.JwtTokenExpiredException;
import com.espe.loanservice.exception.JwtTokenMalformedException;
import com.espe.loanservice.exception.JwtTokenSignatureException;
import com.espe.loanservice.exception.JwtTokenUnsupportedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    private Key getKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getKey())
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(getKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", authToken, e);
            throw new JwtTokenExpiredException("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", authToken, e);
            throw new JwtTokenUnsupportedException("JWT token is unsupported");
        } catch (MalformedJwtException e) {
            logger.error("JWT token is malformed: {}", authToken, e);
            throw new JwtTokenMalformedException("JWT token is malformed");
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", authToken, e);
            throw new JwtTokenSignatureException("Invalid JWT signature");
        } catch (IllegalArgumentException e) {
            logger.error("JWT token is empty or has an illegal argument: {}", authToken, e);
            throw new JwtTokenMalformedException("JWT token is empty or has an illegal argument");
        }
    }
}