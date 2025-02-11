package v._1.PokeNest.auth.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import v._1.PokeNest.pets.model.User;
import v._1.PokeNest.auth.service.JwtService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;


@Service
@Component
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret.file}")
    private String secretFilePath;

    @Value("${jwt.token.access.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.threshold}")
    private long refreshThreshold;

    private String SECRET_KEY;

    @PostConstruct
    private void loadSecretKey() {
        try {
            SECRET_KEY = new String(Files.readAllBytes(Paths.get(secretFilePath))).trim();
        } catch (IOException e) {
            throw new IllegalStateException("We couldn't find the file in the path: " + secretFilePath, e);
        }
    }

    @Override
    public String getToken(UserDetails user) {
        return generateToken(new HashMap<>(), user, accessTokenExpiration);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails user, long validity) {
        if (user instanceof User) {
            User appUser = (User) user;
            extraClaims.put("role", appUser.getRole().name());
        }
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String extractUserName(String token) {
        return getAllClaims(token).getSubject();
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public boolean isTokenCloseToExpiry(String token) {
        Date expiration = getExpiration(token);
        long timeToExpiry = expiration.getTime() - System.currentTimeMillis();
        return timeToExpiry <= refreshThreshold;
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}


