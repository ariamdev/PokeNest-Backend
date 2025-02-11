package v._1.PokeNest.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import v._1.PokeNest.auth.service.impl.AuthServiceImpl;
import v._1.PokeNest.auth.dto.response.JwtAuthenticationResponse;
import v._1.PokeNest.auth.dto.request.LoginRequest;
import v._1.PokeNest.auth.dto.request.RegisterRequest;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody @Valid LoginRequest request){
        log.info("Received signup request for user: {}", request.getUsername());
        return ResponseEntity.ok(authServiceImpl.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> register(@RequestBody @Valid RegisterRequest request){
        log.info("Received signin request for email: {}", request.getEmail());
        return ResponseEntity.ok(authServiceImpl.register(request));
    }
}
