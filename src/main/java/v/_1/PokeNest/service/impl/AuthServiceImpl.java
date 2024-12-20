package v._1.PokeNest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import v._1.PokeNest.dto.response.JwtAuthenticationResponse;
import v._1.PokeNest.dto.request.LoginRequest;
import v._1.PokeNest.dto.request.RegisterRequest;
import v._1.PokeNest.model.enums.Role;
import v._1.PokeNest.model.User;
import v._1.PokeNest.exception.custom.ExistingEmailException;
import v._1.PokeNest.exception.custom.ExistingUsernameException;
import v._1.PokeNest.repository.UserRepository;
import v._1.PokeNest.service.AuthService;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtServiceImpl jwtServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token= jwtServiceImpl.getToken(user);
        return JwtAuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public JwtAuthenticationResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ExistingUsernameException("El user " + request.getUsername() + " ya existe. " +
                    "Por favor, escoja otro nombre de usuario.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ExistingEmailException("El email '" + request.getEmail() + " ya está registrado en el sistema.");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.USER)
                .pets(new ArrayList<>()) //Inicializa lista de pets
                .build();

        userRepository.save(user);

        return JwtAuthenticationResponse.builder()
                .token(jwtServiceImpl.getToken(user))
                .build();
    }
}
