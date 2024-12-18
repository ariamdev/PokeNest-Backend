package v._1.PokeNest.service;

import v._1.PokeNest.dao.request.LoginRequest;
import v._1.PokeNest.dao.request.RegisterRequest;
import v._1.PokeNest.dao.response.JwtAuthenticationResponse;

public interface AuthService {
    JwtAuthenticationResponse login(LoginRequest request);
    JwtAuthenticationResponse register(RegisterRequest request);
}
