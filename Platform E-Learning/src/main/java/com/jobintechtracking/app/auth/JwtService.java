package com.jobintechtracking.app.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "4Ih3wrP+Hq2epTvgtOAGMBKVLMrgon5WoQsJbTS7hiGDfyFQ4mV9fOtV5LqbgZMNJ1RhXG2jIopmCcXyT4AUcz/QYaoW+GLhfiNkSClDYHW6CrS2C6MA0FoL64q+rvfLPi7eJ9w6NpTCwwNwyFG6HvmOb894fiv2XnZVTctPINWyFcu3jY+UbTKjaam7qeZSjky5GTi1hr9QRPbKNmH+njzSHW6AUE0IExv9KWaa6bLKf4csRn6rBpzGxz33xnME1V2RXWoQX+DmdEKiSjD+pGhE22ZdIQ6LG0+vjMxMOLyKVBL1HYY0vGbBBJ67fe3v1+2rkjtDJOmKuIYQQW/GH8G8XPlx6FsVWQ9PCGRvV/8=";

    public String extractUsername(String token){

        return  extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
