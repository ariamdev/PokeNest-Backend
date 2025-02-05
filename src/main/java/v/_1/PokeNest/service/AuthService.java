package v._1.PokeNest.service;

import v._1.PokeNest.dto.request.LoginRequest;
import v._1.PokeNest.dto.request.RegisterRequest;
import v._1.PokeNest.dto.response.JwtAuthenticationResponse;
import v._1.PokeNest.model.User;

public interface AuthService {
    JwtAuthenticationResponse login(LoginRequest request);
    JwtAuthenticationResponse register(RegisterRequest request);
    User getAuthenticatedUser();
    boolean isAdmin(User user);
}
