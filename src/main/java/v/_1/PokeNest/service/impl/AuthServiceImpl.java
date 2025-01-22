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
import v._1.PokeNest.exception.custom.UserNotFoundException;
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
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        String token= jwtServiceImpl.getToken(user);

        String role = "";
        if (user instanceof User) {
            User appUser = (User) user;
            role = appUser.getRole().name();
        }

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
}
