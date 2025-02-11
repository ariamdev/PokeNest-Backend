package v._1.PokeNest.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import v._1.PokeNest.auth.dto.response.JwtAuthenticationResponse;
import v._1.PokeNest.auth.dto.request.LoginRequest;
import v._1.PokeNest.auth.dto.request.RegisterRequest;
import v._1.PokeNest.pets.exception.custom.UserNotFoundException;
import v._1.PokeNest.pets.model.enums.Role;
import v._1.PokeNest.pets.model.User;
import v._1.PokeNest.pets.exception.custom.ExistingEmailException;
import v._1.PokeNest.pets.exception.custom.ExistingUsernameException;
import v._1.PokeNest.auth.repository.UserRepository;
import v._1.PokeNest.auth.service.AuthService;

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
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        String token= jwtServiceImpl.getToken(user);

        return JwtAuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public JwtAuthenticationResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ExistingUsernameException("The user " + request.getUsername() + " already exists. " +
                    "Please, select another username.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ExistingEmailException("The email" + request.getEmail() + " is already restered in the system.");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.USER)
                .pets(new ArrayList<>())
                .build();

        userRepository.save(user);

        return JwtAuthenticationResponse.builder()
                .token(jwtServiceImpl.getToken(user))
                .build();
    }

    @Override
    public User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
    }

    @Override
    public boolean isAdmin(User user) {
        return user.getRole() == Role.ADMIN;
    }
}
