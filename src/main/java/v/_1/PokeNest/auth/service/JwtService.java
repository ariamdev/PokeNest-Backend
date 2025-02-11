package v._1.PokeNest.auth.service;

import org.springframework.security.core.userdetails.UserDetails;


public interface JwtService {
    String getUsernameFromToken(String token);
    String getToken(UserDetails user);
    boolean isTokenValid(String token, UserDetails userDetails);
    String extractUserName(String token);

}
