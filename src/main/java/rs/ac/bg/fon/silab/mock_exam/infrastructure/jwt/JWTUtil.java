package rs.ac.bg.fon.silab.mock_exam.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.time.Instant;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;
@Service
public class JWTUtil {

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    public String issueToken(String subject){
        return issueToken(subject, Map.of());
    }

    public String issueToken(String subject, String ...scopes){
        return issueToken(subject, Map.of("scopes", scopes));
    }

    public String issueToken(String subject, Map<String, Object> claims){

        String token = Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("https://probni-prijemni.fon.bg.ac.rs")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(
                        Date.from(
                                Instant.now().plus(1, DAYS)
                        )
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public String getSubject(String token){
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean isTokenValid(String jwt, String username) {
        String subject = getSubject(jwt);
        return subject.equals(username) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        Date today = Date.from(Instant.now());

        return getClaims(jwt).getExpiration().before(today);
    }
}
