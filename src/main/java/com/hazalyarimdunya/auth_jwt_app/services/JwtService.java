package com.hazalyarimdunya.auth_jwt_app.services;
import com.hazalyarimdunya.auth_jwt_app.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
public class JwtService {
        private final Key key;
        private final long jwtExpiration;

        public JwtService(@Value("${jwt.secret}") String secret,
                          @Value("${jwt.expiration}") long jwtExpiration) {
            this.key = Keys.hmacShaKeyFor(secret.getBytes());
            this.jwtExpiration = jwtExpiration;
        }

        // Token oluşturma
        public String generateToken(UserEntity userEntity) {
            return Jwts.builder()
                    .setSubject(userEntity.getUsername())
                    .claim("role", userEntity.getRole().name())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        }

        // Username alma
        public String extractUsername(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }

        // Token geçerli mi?
        public boolean validateToken(String token, UserEntity userEntity) {
            String username = extractUsername(token);
            return username.equals(userEntity.getUsername()) && !isTokenExpired(token);
        }

        private boolean isTokenExpired(String token) {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        }
    }
