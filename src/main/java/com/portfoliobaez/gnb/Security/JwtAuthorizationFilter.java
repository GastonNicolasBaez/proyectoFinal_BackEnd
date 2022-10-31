package com.portfoliobaez.gnb.Security;

import static com.portfoliobaez.gnb.Utils.CONSTANTS.SECRET_KEY_TOKEN;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;


@CrossOrigin(origins = "https://frontendgnb.web.app")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            if (!existeJwtToken(request)) {
                SecurityContextHolder.clearContext();
            } else {
                Claims claims = validateToken(request);
                if (claims.get("authorities") == null) {
                    SecurityContextHolder.clearContext();
                } else {
                    setUpSpringAuthentication(claims);
                }
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }

    }

    private void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List) claims.get("authorities");

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
        SecurityContextHolder.getContext()
                .setAuthentication(auth);

    }
    
    private Claims validateToken ( HttpServletRequest request ) {
        String jwtToken = request.getHeader(HEADER)
          .replace(PREFIX, "");
        return Jwts.parser()
          .setSigningKey(SECRET_KEY_TOKEN.getBytes())
          .parseClaimsJws(jwtToken)
          .getBody();
    }

    private boolean existeJwtToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }
}
