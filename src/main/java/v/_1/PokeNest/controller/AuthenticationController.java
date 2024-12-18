package v._1.PokeNest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import v._1.PokeNest.service.impl.AuthServiceImpl;
import v._1.PokeNest.dao.response.JwtAuthenticationResponse;
import v._1.PokeNest.dao.request.LoginRequest;
import v._1.PokeNest.dao.request.RegisterRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authServiceImpl.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authServiceImpl.register(request));
    }
}
