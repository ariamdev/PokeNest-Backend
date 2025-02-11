package v._1.PokeNest.auth.service;

import v._1.PokeNest.auth.dto.request.LoginRequest;
import v._1.PokeNest.auth.dto.request.RegisterRequest;
import v._1.PokeNest.auth.dto.response.JwtAuthenticationResponse;
import v._1.PokeNest.pets.model.User;

public interface AuthService {
    JwtAuthenticationResponse login(LoginRequest request);
    JwtAuthenticationResponse register(RegisterRequest request);
    User getAuthenticatedUser();
    boolean isAdmin(User user);
}
