package com.example.itransitionlasttask.filter;

import com.example.itransitionlasttask.model.entity.UserEntity;
import com.example.itransitionlasttask.repository.UserRepository;
import com.example.itransitionlasttask.security.JWTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JWTokenFilter extends OncePerRequestFilter {

  private final JWTokenProvider jwtTokenProvider;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      String token = getTokenFromHeader(request);

      if (token != null) {
        Jws<Claims> claimsJws = jwtTokenProvider.getClaims(token);
        if (claimsJws != null){
          String subject = claimsJws.getBody().getSubject();
          Optional<UserEntity> user = userRepository.findByEmail(subject);

          if (user.isPresent()){
            UserEntity userEntity = user.get();
            UsernamePasswordAuthenticationToken authentication = new  UsernamePasswordAuthenticationToken(userEntity, null,
                    userEntity.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }

        }
      }
    }
    catch(JwtValidationException | ExpiredJwtException ex){
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
    filterChain.doFilter(request, response);
    resetAuthenticationAfterRequest();
  }

  private void resetAuthenticationAfterRequest() {
    SecurityContextHolder.getContext().setAuthentication(null);
  }

  private String getTokenFromHeader(HttpServletRequest request) {
    String header = request.getHeader(AUTHORIZATION);
    if (header != null && header.startsWith("Bearer ")) {
      return header.substring("Bearer ".length());
    }
    return null;
  }
}





