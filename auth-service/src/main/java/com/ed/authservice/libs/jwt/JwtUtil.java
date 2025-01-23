package com.ed.authservice.libs.jwt;

import com.ed.authservice.auth.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtUtil {

  private final JwtProperties jwtProperties;

  public String generateToken(User user) {
    Date now = new Date();
    return makeToken(user, new Date(now.getTime() + jwtProperties.getDuration().toMillis()));
  }

  public String makeToken(User user, Date expirationDate) {
    return Jwts.builder()
        .header().add("typ", "JWT")
        .and()
        .issuer(jwtProperties.getIssuer())
        .issuedAt(new Date())
        .expiration(expirationDate)
        .claim("userId", user.getPublicId())
        .claim("userRole", user.getUserRole())
        .signWith(getSigningKey())
        .compact();
  }

  public Key getSigningKey() {
    
    return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtProperties.getSecretKey()));
  }
}