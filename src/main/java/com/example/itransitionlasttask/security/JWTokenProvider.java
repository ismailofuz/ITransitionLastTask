package com.example.itransitionlasttask.security;

import com.example.itransitionlasttask.model.entity.UserEntity;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTokenProvider {

  @Value("${jwt.secret.key.access}")
  private  String jwtAccessSecret;

  @Value("${jwt.secret.expiration.access}")
  private int accessTokenExpiration;


  public String generateAccessToken(UserEntity user) {
    String subj = user.getEmail();
    return "Bearer " +  Jwts.builder().setSubject(subj).claim("role", user.getRole()).setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + accessTokenExpiration)).signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
            .compact();
  }


  public Jws<Claims> getClaims(String token) {
    return Jwts.parser().setSigningKey(jwtAccessSecret).parseClaimsJws(token);
  }
}
