package pl.lodz.p.edu.flanki.config.authentication;


import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JWTProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpiration}")
    private int jwtExpiration;

    boolean validateJwtToken(final String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (final SignatureException e) {
            log.error("Invalid JWT signature -> Message: {} ", e);
        } catch (final MalformedJwtException e) {
            log.error("Invalid JWT token -> Message: {}", e);
        } catch (final ExpiredJwtException e) {
            log.error("Expired JWT token -> Message: {}", e);
        } catch (final UnsupportedJwtException e) {
            log.error("Unsupported JWT token -> Message: {}", e);
        } catch (final IllegalArgumentException e) {
            log.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }

    String getUserNameFromJwtToken(final String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public String generateJwtToken() {
        final UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Jwts.builder()
                .setSubject(userPrinciple.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
